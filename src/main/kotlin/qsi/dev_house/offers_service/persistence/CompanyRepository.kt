package qsi.dev_house.offers_service.persistence

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import qsi.dev_house.offers_service.model.Company

@Repository interface CompanyRepository : CrudRepository<Company, String> {
}