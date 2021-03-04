package qsi.dev_house.offers_service.integration_tests

import org.assertj.core.api.Assertions
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import qsi.dev_house.offers_service.model.Offer
import qsi.dev_house.offers_service.service.OfferService
import java.time.Duration
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
class OfferServiceIntegrationTest {
    @Autowired
    private val offerService: OfferService? = null

    var offer = Offer(
            cityName = "Ronchin",
            companyName = "Adeo",
            title = "Architecte d'entreprise",
            validityEndDate = Date.from(
                    LocalDate.of(2018, 6, 25)
                            .atStartOfDay().atZone(ZoneId.systemDefault())
                            .toInstant()
            ),
            contractPeriodValue = "P0Y6M0D",
            contractTypeValue = "STAGE"
    )

    @After
    fun deleteData() {
        offerService!!.deleteAll()
    }

    @Test
    fun whenCompanyNameIsGiven_AndCompanyDoesNotExist_ThenCreateCompany() {
        //given
        offer.companyDescription = "Adeo est un des leaders européens du DIY et de l'amélioration de l'habitat."

        offer = offerService!!.createOffer(offer)

        //when
        val foundOffer: Offer = offer.id!!.let { offerService.findById(it) }

        //then
        Assertions.assertThat(foundOffer.company.offers.size)
                .isEqualTo(1)
    }

    @Test
    fun whenCompanyNameIsGiven_AndCompanyDoesExist_ThenUpdateCompany() {
        //given
        offer.companyDescription = "Adeo est le leader européen du DIY et de l'amélioration de l'habitat."

        offerService!!.createOffer(offer)

        var otherOffer = offer
        otherOffer.id = null
        otherOffer.title = "DevOPS"

        //we change the description in order to see if it has changed for the company
        otherOffer.companyDescription = "Adeo est le troisième leader mondial du DIY et de l'amélioration de l'habitat."

        otherOffer = offerService.createOffer(otherOffer)

        //when
        val foundOtherOffer = otherOffer.id!!.let { offerService.findById(it) }

        Assertions.assertThat(foundOtherOffer.title)
                .isEqualTo("DevOPS")

        //then
        Assertions.assertThat(foundOtherOffer.company.offers.size)
                .isEqualTo(2)

        println(foundOtherOffer)

        Assertions.assertThat(foundOtherOffer.company.description)
                .isEqualTo("Adeo est le leader européen du DIY et de l'amélioration de l'habitat.")
    }
}