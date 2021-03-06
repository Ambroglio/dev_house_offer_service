package qsi.dev_house.offers_service.service

import org.springframework.stereotype.Service
import qsi.dev_house.offers_service.model.Offer
import java.util.*

@Service
interface OfferService {
    fun createOffer(offer : Offer) : Offer
    fun findById(id : UUID) : Offer
    fun updateOffer(id: UUID, offer: Offer) : Offer
    fun deleteOffer(id: UUID)
    fun findByMemberId(id: UUID) : List<Offer>
    fun findByCityNameStartingWith(cityNamePart: String) : List<Offer>

    //find all valid offers globally - to be sorted then
    fun findOffers() : List<Offer>

    //WARNING ONLY FOR TESTS
    fun deleteAll()
}