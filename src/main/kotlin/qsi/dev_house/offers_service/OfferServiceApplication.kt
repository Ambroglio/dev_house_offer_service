package qsi.dev_house.offers_service

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.CrossOrigin

@SpringBootApplication
class OfferServiceApplication

fun main(args: Array<String>) {
	runApplication<OfferServiceApplication>(*args)
	//	SpringApplication.run(OfferServiceApplication::class.java, *args)
}
