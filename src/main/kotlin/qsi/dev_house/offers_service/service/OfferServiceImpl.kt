package qsi.dev_house.offers_service.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Configurable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import qsi.dev_house.offers_service.misc.OfferRanker
import qsi.dev_house.offers_service.model.Company
import qsi.dev_house.offers_service.model.Offer
import qsi.dev_house.offers_service.persistence.CompanyRepository
import qsi.dev_house.offers_service.persistence.OfferRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import java.util.stream.Collectors
import java.util.stream.StreamSupport

@Service
class OfferServiceImpl : OfferService {

    @Autowired
    lateinit var offerRepository: OfferRepository

    @Autowired
    lateinit var companyRepository: CompanyRepository

    @Autowired
    lateinit var offerRanker: OfferRanker

    var logger: Logger = LoggerFactory.getLogger(OfferServiceImpl::class.java)

    fun setCompany(offer: Offer) {
        var company = Mono.just(Company())

        //checking and creating company for offer
        if (companyRepository!!.existsById(offer.companyName).equals(true)) {
            //company already exists.
            logger.debug("Company already exists.")

            company = companyRepository!!.findById(offer.companyName)
            company.map{c -> {
                c.offers.add(offer)
                offer.company = c
                companyRepository!!.save(c)
            }}

        } else {
            //company must be created.
            logger.debug("Company must be created.")

            company.map { c ->
                {
                    c.description = offer.companyDescription!!
                    c.name = offer.companyName
                    c.offers = mutableSetOf(offer)
                    offer.company = c
                    companyRepository!!.save(c)
                }
            }


        }
    }

    @Transactional
    override fun createOffer(offer: Offer): Mono<Offer> {
        logger.debug("Creating offer...")

        setCompany(offer)

        return offerRepository!!.save(offer)
    }

    override fun findById(id: UUID): Mono<Offer> {
        return offerRepository!!.findById(id).switchIfEmpty(Mono.error(Exception("This offer does not exist.")))
        }

    override fun updateOffer(id: UUID, offer: Offer): Mono<Offer> {
        setCompany(offer)

        return offerRepository!!.save(offer)
    }

    override fun deleteOffer(id: UUID) {
        val offer = offerRepository!!.findById(id).switchIfEmpty(Mono.error(Exception("This offer does not exist.")))
        offer.map { o -> o.valid = false }

        offerRepository!!.save(offer.block()!!)
    }

    override fun findOffers(): Flux<Offer> {
        val offers: List<Offer> =
            StreamSupport.stream(offerRepository!!.findByValidTrueAndValidityEndDateAfter().spliterator(), false)
                .collect(Collectors.toList())

        return Flux.fromIterable(offerRanker!!.sortByRanking(offers))
    }

    override fun findByMemberId(id: UUID) : Flux<Offer> {
        return Flux.fromIterable(StreamSupport.stream(
            offerRepository!!.findByMemberIdAndValidTrueAndValidityEndDateAfter(id).spliterator(), false)
            .collect(Collectors.toList()))
    }

    override fun findByCityNameStartingWith(cityNamePart: String) : Flux<Offer> {
        val cityNamePartToUpperCase = cityNamePart.toUpperCase()

        val offers: List<Offer> =
            StreamSupport.stream(offerRepository!!.findByCityNameStartsWithAndValidTrueAndValidityEndDateAfter(cityNamePartToUpperCase).spliterator(), false)
                .collect(Collectors.toList())

        return Flux.fromIterable(offerRanker!!.sortByRanking(offers))
    }


    //WARNING ONLY FOR TESTS
    override fun deleteAll() {
        offerRepository!!.deleteAll()
        companyRepository!!.deleteAll()
    }
}