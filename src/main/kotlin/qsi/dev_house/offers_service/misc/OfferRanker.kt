package qsi.dev_house.offers_service.misc

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import qsi.dev_house.offers_service.model.Offer
import qsi.dev_house.offers_service.persistence.OfferRepository
import qsi.dev_house.offers_service.service.OfferServiceImpl
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*

@Component
class OfferRanker {
    @Autowired
    val offerRepository: OfferRepository? = null

    var logger: Logger = LoggerFactory.getLogger(OfferRanker::class.java)

    fun sortByRanking(offers: List<Offer>): List<Offer> {
        /*
    ranking = (noc - noe) / nod :
        noc = number of valid offers in a city, whatever the enterprise is
        noe = number of valid offers for this enterprise
        nod= number of days before the end of offers
     */
        for (offer in offers) {
            val noc =
                offerRepository!!.countByValidTrueAndValidityEndDateAfterAndCityNameEquals(cityName = offer.cityName)
            logger.debug("Number of valid offers for the city ${offer.cityName} : $noc")

            //todo
            val noe =
                offerRepository!!.countByValidTrueAndValidityEndDateAfterAndCompany_NameEquals(companyName = offer.company.name)
            logger.debug("Number of valid offers for the company ${offer.company.name} : $noe")

            val nod: Float = ChronoUnit.DAYS.between(
                Date().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate(),
                offer.validityEndDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
            ).toFloat()

            logger.debug("End of validity date : ${offer.validityEndDate}")
            logger.debug("Date now : ${Date()}")

            logger.debug("Number of days : $nod")

            offer.ranking = (noc - noe) / nod
            logger.debug("Offer ranking : ${offer.ranking}")
        }

        return offers.sortedBy { it.ranking }
    }
}