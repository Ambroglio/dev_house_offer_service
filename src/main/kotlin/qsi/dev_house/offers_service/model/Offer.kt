package qsi.dev_house.offers_service.model

import com.fasterxml.jackson.annotation.JsonView
import qsi.dev_house.offers_service.views.Views
import java.time.Duration
import java.time.Period
import java.util.*
import javax.persistence.*
import kotlin.jvm.Transient

@Entity
@JsonView(Views.Normal::class)
//TODO validation ?
data class Offer(
        //city name is kept in order to find it easily through repositories
        var cityName: String,

        //for geolocation.
        var longitude: Float = 0.0f,
        var latitude: Float = 0.0f,

        //in order to find a company and set its description if it does not exist
        @Transient
        var companyName: String,
        @Transient
        var companyDescription: String? = null,

        var title: String,
        var description: String? = null,

        var valid: Boolean = true,
        var validityEndDate: Date,

        @Transient
        var contractPeriodValue: String? = null,

        //in order to get contract type
        @Transient
        var contractTypeValue: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null
    var creationDate: Date = Date()
        set(value) {
            field = Date()
        }

    //company is kept here
    @ManyToOne(cascade = [CascadeType.ALL, CascadeType.MERGE], fetch = FetchType.EAGER)
    @JoinColumn(name = "company_name", referencedColumnName = "name")
    lateinit var company: Company

    lateinit var memberId: UUID
    var contractType: ContractType = ContractType.valueOf(contractTypeValue)
    var contractPeriod: Period? = null

    init {
        if (contractPeriodValue != null)
            contractPeriod = Period.parse(contractPeriodValue)
    }
}