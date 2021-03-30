package qsi.dev_house.offers_service.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import qsi.dev_house.offers_service.persistence.OfferRepository
import qsi.dev_house.offers_service.service.*


@Configuration
@EnableWebFlux
@EnableR2dbcRepositories(basePackages = arrayOf("qsi.dev_house.offers_service.persistence"))
class WebConfig : WebFluxConfigurer {

    @Bean
    fun cityService(): CityService? {
        return CityServiceImpl()
    }

    @Bean
    fun companyService(): CompanyService? {
        return CompanyServiceImpl()
    }

    @Bean
    fun offerService(): OfferService? {
        return OfferServiceImpl()
    }

}