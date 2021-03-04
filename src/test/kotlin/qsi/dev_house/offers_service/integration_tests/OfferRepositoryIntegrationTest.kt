package qsi.dev_house.offers_service.integration_tests

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit4.SpringRunner
import qsi.dev_house.offers_service.model.Offer
import qsi.dev_house.offers_service.persistence.OfferRepository
import qsi.dev_house.offers_service.service.OfferService
import java.time.Duration
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import java.time.Instant




@RunWith(SpringRunner::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OfferRepositoryIntegrationTest {
    @Autowired
    private val entityManager: TestEntityManager? = null

    @Autowired
    private val offerRepository: OfferRepository? = null

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
    fun empty() {
        offerRepository!!.deleteAll()
    }

    @Test
    fun whenFindById_ThenReturnOffer() {
        // given

        offer = entityManager!!.persist(offer)
        entityManager.flush()

        // when
        val foundOffer: Offer? = offer.id!!.let { offerRepository!!.findById(it).orElse(null) }

        // then
        assertThat(foundOffer).isNotNull

        if (foundOffer != null) {
            assertThat(foundOffer.cityName)
                    .isEqualTo(offer.cityName)
        }
    }
}