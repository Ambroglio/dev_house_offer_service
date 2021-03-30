package qsi.dev_house.offers_service.persistence

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import qsi.dev_house.offers_service.model.Offer
import reactor.core.publisher.Flux
import java.util.*

@Repository
interface OfferRepository : ReactiveCrudRepository<Offer, UUID> {
    fun findByValidTrueAndValidityEndDateAfter(date: Date = Date()) : Iterable<Offer>
    fun countByValidTrueAndValidityEndDateAfterAndCityNameEquals(date: Date = Date(), cityName : String) : Int
    fun countByValidTrueAndValidityEndDateAfterAndCompany_NameEquals(date: Date = Date(), companyName : String) : Int
    fun findByMemberIdAndValidTrueAndValidityEndDateAfter(id : UUID, date: Date = Date()) : Iterable<Offer>
    fun findByCityNameStartsWithAndValidTrueAndValidityEndDateAfter(cityNamePart: String, date : Date = Date()) : Iterable<Offer>
    @Query("select distinct cityName from Offer where valid = true and validityEndDate > current_date")
    fun findCities() : Flux<String>
}