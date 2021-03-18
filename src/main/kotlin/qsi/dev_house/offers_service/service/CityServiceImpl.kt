package qsi.dev_house.offers_service.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import qsi.dev_house.offers_service.model.Offer
import qsi.dev_house.offers_service.persistence.OfferRepository
import java.util.stream.Collectors
import java.util.stream.StreamSupport

@Service
class CityServiceImpl : CityService{
    @Autowired
    val offerRepository : OfferRepository? = null

    override fun getCities(): List<String> {
        return offerRepository!!.findCities()
    }
}