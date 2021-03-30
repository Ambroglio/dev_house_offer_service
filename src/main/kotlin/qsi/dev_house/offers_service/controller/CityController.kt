package qsi.dev_house.offers_service.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import qsi.dev_house.offers_service.service.CityService
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/cities")
@CrossOrigin
class CityController {

    @Autowired
    lateinit var cityService: CityService

    @GetMapping("/")
    fun getCities() : Flux<String> {
        return cityService!!.getCities()
    }
}