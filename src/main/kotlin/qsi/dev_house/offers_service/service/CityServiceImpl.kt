package qsi.dev_house.offers_service.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Configurable
import org.springframework.stereotype.Service
import qsi.dev_house.offers_service.model.Offer
import qsi.dev_house.offers_service.persistence.OfferRepository
import reactor.core.publisher.Flux
import java.util.stream.Collectors
import java.util.stream.StreamSupport

@Service
class CityServiceImpl : CityService{

    @Autowired
    lateinit var offerRepository : OfferRepository

    override fun getCities(): Flux<String> {
        return offerRepository!!.findCities()
    }
}