package qsi.dev_house.offers_service.service

import org.springframework.stereotype.Service

@Service
interface CityService {
    fun getCities(): List<String>
}