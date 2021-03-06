package qsi.dev_house.offers_service.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import qsi.dev_house.offers_service.misc.OfferRanker
import qsi.dev_house.offers_service.model.Company
import qsi.dev_house.offers_service.model.Offer
import qsi.dev_house.offers_service.persistence.CompanyRepository
import qsi.dev_house.offers_service.persistence.OfferRepository
import java.time.Duration
import java.time.Period
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.stream.Collectors
import java.util.stream.StreamSupport
import javax.transaction.Transactional

@Service
class OfferServiceImpl : OfferService {
    @Autowired
    val offerRepository: OfferRepository? = null

    @Autowired
    val companyRepository: CompanyRepository? = null

    @Autowired
    val offerRanker: OfferRanker? = null

    var logger: Logger = LoggerFactory.getLogger(OfferServiceImpl::class.java)

    fun setCompany(offer: Offer) {
        var company = Company()

        //checking and creating company for offer
        if (companyRepository!!.existsById(offer.companyName)) {
            //company already exists.
            logger.debug("Company already exists.")

            company = companyRepository!!.findById(offer.companyName).get()
            company.offers.add(offer)
            offer.company = company
        } else {
            //company must be created.
            logger.debug("Company must be created.")

            company.description = offer.companyDescription!! //can't be null
            company.name = offer.companyName
            company.offers = mutableSetOf(offer)
            offer.company = company
        }
    }

    @Transactional
    override fun createOffer(offer: Offer): Offer {
        logger.debug("Creating offer...")

        setCompany(offer)

        return offerRepository!!.save(offer)
    }

    override fun findById(id: UUID): Offer {
        return offerRepository!!.findById(id).orElseThrow {
            Exception("This offer does not exist.")
        }
    }

    override fun updateOffer(id: UUID, offer: Offer): Offer {
        setCompany(offer)

        return offerRepository!!.save(offer)
    }

    override fun deleteOffer(id: UUID) {
        val offer = offerRepository!!.findById(id).orElseThrow {
            Exception("This offer does not exist.")
        }

        offer.valid = false

        offerRepository!!.save(offer)
    }

    override fun findOffers(): List<Offer> {
        val offers: List<Offer> =
            StreamSupport.stream(offerRepository!!.findByValidTrueAndValidityEndDateAfter().spliterator(), false)
                .collect(Collectors.toList())

        return offerRanker!!.sortByRanking(offers)
    }

    override fun findByMemberId(id: UUID) : List<Offer> {
        return StreamSupport.stream(
            offerRepository!!.findByMemberIdAndValidTrueAndValidityEndDateAfter(id).spliterator(), false)
            .collect(Collectors.toList())
    }

    override fun findByCityNameStartingWith(cityNamePart: String) : List<Offer> {
        val cityNamePartToUpperCase = cityNamePart.toUpperCase()

        val offers: List<Offer> =
            StreamSupport.stream(offerRepository!!.findByCityNameStartsWithAndValidTrueAndValidityEndDateAfter(cityNamePartToUpperCase).spliterator(), false)
                .collect(Collectors.toList())

        return offerRanker!!.sortByRanking(offers)
    }


    //WARNING ONLY FOR TESTS
    override fun deleteAll() {
        offerRepository!!.deleteAll()
        companyRepository!!.deleteAll()
    }
}