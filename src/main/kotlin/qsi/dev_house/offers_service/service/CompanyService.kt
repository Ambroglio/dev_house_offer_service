package qsi.dev_house.offers_service.service

import org.springframework.stereotype.Service
import qsi.dev_house.offers_service.model.Company

@Service
interface CompanyService {
    fun getCompanies() : List<Company>
}