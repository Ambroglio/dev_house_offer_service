package qsi.dev_house.offers_service.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import qsi.dev_house.offers_service.model.Company
import qsi.dev_house.offers_service.persistence.CompanyRepository
import java.util.stream.Collectors
import java.util.stream.StreamSupport

@Service
class CompanyServiceImpl : CompanyService {
    @Autowired
    val companyRepository : CompanyRepository? = null

    override fun getCompanies(): List<Company> {
        return StreamSupport.stream(companyRepository!!.findAll().spliterator(), false).collect(Collectors.toList())
    }
}