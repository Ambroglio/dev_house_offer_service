package qsi.dev_house.offers_service.controller

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import qsi.dev_house.offers_service.model.Company
import qsi.dev_house.offers_service.service.CompanyService
import qsi.dev_house.offers_service.views.Views

@RestController
@RequestMapping("/companies")
class CompanyController {
    @Autowired
    val companyService: CompanyService? = null

    @GetMapping("/")
    @JsonView(Views.Company::class)
    fun getCompanies() : List<Company> {
        return companyService!!.getCompanies()
    }
}