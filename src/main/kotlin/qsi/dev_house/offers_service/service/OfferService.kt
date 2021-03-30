package qsi.dev_house.offers_service.service

import org.springframework.stereotype.Service
import qsi.dev_house.offers_service.model.Offer
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
interface OfferService {
    fun createOffer(offer : Offer) : Mono<Offer>
    fun findById(id : UUID) : Mono<Offer>
    fun updateOffer(id: UUID, offer: Offer) : Mono<Offer>
    fun deleteOffer(id: UUID)
    fun findByMemberId(id: UUID) : Flux<Offer>
    fun findByCityNameStartingWith(cityNamePart: String) : Flux<Offer>

    //find all valid offers globally - to be sorted then
    fun findOffers() : Flux<Offer>

    //WARNING ONLY FOR TESTS
    fun deleteAll()
}