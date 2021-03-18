package qsi.dev_house.offers_service.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import qsi.dev_house.offers_service.service.CityService

@RestController
@RequestMapping("/cities")
class CityController {
    @Autowired
    val cityService: CityService? = null

    @GetMapping("/")
    fun getCities() : List<String> {
        return cityService!!.getCities()
    }
}