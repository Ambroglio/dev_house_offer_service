package qsi.dev_house.offers_service.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Configurable
import org.springframework.stereotype.Service
import qsi.dev_house.offers_service.model.Company
import qsi.dev_house.offers_service.model.Offer
import qsi.dev_house.offers_service.persistence.CompanyRepository
import reactor.core.publisher.Flux
import java.util.stream.Collectors
import java.util.stream.StreamSupport

@Service
class CompanyServiceImpl : CompanyService {

    @Autowired
    lateinit var companyRepository : CompanyRepository

    override fun getCompanies(): Flux<Company> {
        return companyRepository!!.findAll()
    }
}