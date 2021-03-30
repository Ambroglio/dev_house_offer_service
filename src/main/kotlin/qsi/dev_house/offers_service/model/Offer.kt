package qsi.dev_house.offers_service.model

import com.fasterxml.jackson.annotation.JsonView
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import qsi.dev_house.offers_service.views.Views
import java.time.Period
import java.util.*
import kotlin.jvm.Transient

//TODO validation ?
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("offer")
data class Offer(
        //city name is kept in order to find it easily through repositories
        @JsonView(Views.Offer::class, Views.Company::class)
        var cityName: String,

        //for geolocation.
        @JsonView(Views.Offer::class, Views.Company::class)
        var longitude: Float = 0.0f,
        @JsonView(Views.Offer::class, Views.Company::class)
        var latitude: Float = 0.0f,

        //in order to find a company and set its description if it does not exist
        @Transient
        var companyName: String,
        @Transient
        var companyDescription: String? = null,

        @JsonView(Views.Offer::class, Views.Company::class)
        var title: String,
        @JsonView(Views.Offer::class, Views.Company::class)
        var description: String? = null,

        @JsonView(Views.Offer::class, Views.Company::class)
        var valid: Boolean = true,
        @JsonView(Views.Offer::class, Views.Company::class)
        var validityEndDate: Date,

        @Transient
        var contractPeriodValue: String? = null,

        //in order to get contract type
        @Transient
        var contractTypeValue: String,
) {
    @Id
    @JsonView(Views.Offer::class, Views.Company::class)
    var id: UUID? = null
    @JsonView(Views.Offer::class, Views.Company::class)
    var creationDate: Date = Date()
        set(value) {
            field = Date()
        }

    //company is kept here
    @JsonView(Views.Offer::class)
    lateinit var company: Company

    @JsonView(Views.Offer::class, Views.Company::class)
    lateinit var memberId: UUID

    @JsonView(Views.Offer::class, Views.Company::class)
    var contractType: ContractType = ContractType.valueOf(contractTypeValue)

    @JsonView(Views.Offer::class, Views.Company::class)
    var contractPeriod: Period? = null

    @Transient
    var ranking: Float = 0.0f

    init {
        if (contractPeriodValue != null)
            contractPeriod = Period.parse(contractPeriodValue)

        cityName = cityName.toUpperCase()
    }
}