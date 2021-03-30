package qsi.dev_house.offers_service.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
interface CityService {
    fun getCities(): Flux<String>
}