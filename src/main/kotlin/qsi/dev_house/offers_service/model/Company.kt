package qsi.dev_house.offers_service.model

import com.fasterxml.jackson.annotation.JsonView
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import qsi.dev_house.offers_service.views.Views

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("company")
class Company() {
        @Id
        @JsonView(Views.Offer::class, Views.Company::class)
        lateinit var name : String
        @JsonView(Views.Offer::class, Views.Company::class)
        lateinit var description : String
        //var websiteLink : String

        //in order to keep all created offers for a company
        //@JsonView(Views.Company::class)
        lateinit var offers : MutableSet<Offer>
}