package qsi.dev_house.offers_service.controller

import com.fasterxml.jackson.annotation.JsonView
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import qsi.dev_house.offers_service.model.Offer
import qsi.dev_house.offers_service.service.OfferService
import qsi.dev_house.offers_service.views.Views
import java.util.*

/**
 * REST Offer Controller, to create, update, delete (invalidate) offers.
 *
 * @author Ambroise Mullie
 * @author Lou Moreau
 */
@RestController
@RequestMapping("/offers")
class OfferController {
    data class VerifyTokenRequest(
            val jwt: String
    )

    data class VerifyTokenResponse(
            val user_id: UUID
    )

    var logger: Logger = LoggerFactory.getLogger(OfferController::class.java)

    val authServiceUrl = "http://localhost:3000"

    @Autowired
    val offerService: OfferService? = null

    /**
     * Takes a JWT token, inside its bearer, and returns the member's id
     *
     * @param bearer containing a JWT given by the authentication service
     * @return member's id
     */
    fun verifyToken(bearer: String) : UUID {
        val token = bearer.split(" ")[1]
        logger.debug(bearer)
        logger.debug(token)

        val restTemplate = RestTemplate()
        val responseEntity: ResponseEntity<VerifyTokenResponse> =
                restTemplate.postForEntity("$authServiceUrl/verify", HttpEntity<VerifyTokenRequest>(VerifyTokenRequest(token)), VerifyTokenResponse::class.java)

        if (responseEntity.statusCode != HttpStatus.OK) {
            throw Exception("JWT Token is not valid.")
        }

        return responseEntity.body!!.user_id
    }

    @GetMapping("/")
    @JsonView(Views.Offer::class)
    fun getOffers() : List<Offer> {
        return offerService!!.findOffers()
    }

    @GetMapping("/{id}")
    @JsonView(Views.Offer::class)
    fun getOffer(
        @PathVariable("id") id : UUID
    ) : Offer {
        return offerService!!.findById(id)
    }

    /**
     * Creates an offer, checking if the token is verified in order to create the post for the current member.
     *
     * @param offer to be created
     * @param bearer containing JWT
     * @return offer created
     */
    @PostMapping("/")
    @JsonView(Views.Offer::class)
    fun createOffer(@RequestBody offer: Offer,
                    @RequestHeader("Authorization") bearer: String): Offer {
        logger.debug(bearer)
        val memberId = verifyToken(bearer)
        offer.memberId = memberId

        return offerService!!.createOffer(offer)
    }

    /**
     * Updates an offer, checking if the token is verified and member's id is the same than current offer member's id.
     *
     * @param id of offer to be modified
     * @param offer offer's content
     * @param bearer containing jwt
     */
    @PutMapping("/{id}")
    @JsonView(Views.Offer::class)
    fun updateOffer(
            @PathVariable("id") id : UUID,
            @RequestBody offer : Offer,
            @RequestHeader("Authorization" )bearer: String) : Offer {
        val memberId = verifyToken(bearer)
        val savedOffer = offerService!!.findById(id)

        if (savedOffer.memberId != memberId) {
            throw Exception("You haven't created this offer, you cannot modify it.")
        }

        offer.id = id
        offer.memberId = memberId

        return offerService!!.updateOffer(id, offer)
    }

    @DeleteMapping("/{id}")
    @JsonView(Views.Offer::class)
    fun deleteOffer(
            @PathVariable("id") id : UUID,
            @RequestHeader("Authorization") bearer: String
    ) {
        val memberId = verifyToken(bearer)
        val savedOffer = offerService!!.findById(id)

        if (savedOffer.memberId != memberId) {
            throw Exception("You haven't created this offer, you cannot modify it.")
        }

        offerService!!.deleteOffer(id)
    }

    @GetMapping("/member/{id}")
    @JsonView(Views.Offer::class)
    fun findOffersByMemberId(
        @PathVariable("id") id : UUID,
    ) : List<Offer> {
        return offerService!!.findByMemberId(id)
    }

    @GetMapping("/city/{cityName}")
    @JsonView(Views.Offer::class)
    fun findOffersByCityName(
        @PathVariable("cityName") cityName : String
    ) : List<Offer> {
        return offerService!!.findByCityNameStartingWith(cityNamePart = cityName)
    }
}