package qsi.dev_house.offers_service.persistence

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import qsi.dev_house.offers_service.model.Offer
import java.util.*

@Repository interface OfferRepository : CrudRepository<Offer, UUID> {
    fun findByValidTrueAndValidityEndDateAfter(date: Date = Date()) : Iterable<Offer>
    fun countByValidTrueAndValidityEndDateAfterAndCityNameEquals(date: Date = Date(), cityName : String) : Int
    fun countByValidTrueAndValidityEndDateAfterAndCompany_NameEquals(date: Date = Date(), companyName : String) : Int
    fun findByMemberIdAndValidTrueAndValidityEndDateAfter(id : UUID, date: Date = Date()) : Iterable<Offer>
    fun findByCityNameStartsWithAndValidTrueAndValidityEndDateAfter(cityNamePart: String, date : Date = Date()) : Iterable<Offer>
    @Query("select distinct cityName from Offer where valid = true and validityEndDate > current_date")
    fun findCities() : List<String>
}