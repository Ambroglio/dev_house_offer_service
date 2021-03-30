package qsi.dev_house.offers_service.controller

import com.fasterxml.jackson.annotation.JsonView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import qsi.dev_house.offers_service.model.Company
import qsi.dev_house.offers_service.service.CompanyService
import qsi.dev_house.offers_service.views.Views
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/companies")
@CrossOrigin
class CompanyController {

    @Autowired
    lateinit var companyService: CompanyService

    @GetMapping("/")
    @JsonView(Views.Company::class)
    fun getCompanies() : Flux<Company> {
        return companyService!!.getCompanies()
    }
}