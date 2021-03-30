package qsi.dev_house.offers_service.service

import org.springframework.stereotype.Service
import qsi.dev_house.offers_service.model.Company
import reactor.core.publisher.Flux

@Service
interface CompanyService {
    fun getCompanies() : Flux<Company>
}