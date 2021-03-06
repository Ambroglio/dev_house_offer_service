package qsi.dev_house.offers_service.model

import com.fasterxml.jackson.annotation.JsonView
import qsi.dev_house.offers_service.views.Views
import javax.persistence.*

@Entity
class Company() {
        @Id
        @Column(name="name", unique = true)
        @JsonView(Views.Offer::class, Views.Company::class)
        lateinit var name : String
        @JsonView(Views.Offer::class, Views.Company::class)
        lateinit var description : String
        //var websiteLink : String

        //in order to keep all created offers for a company
        @OneToMany(mappedBy = "company", fetch = FetchType.EAGER, orphanRemoval=true)
        //@JsonView(Views.Company::class)
        lateinit var offers : MutableSet<Offer>
}