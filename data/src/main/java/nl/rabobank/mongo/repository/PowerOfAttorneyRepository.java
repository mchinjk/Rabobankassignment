package nl.rabobank.mongo.repository;

import nl.rabobank.authorizations.Authorization;
import nl.rabobank.authorizations.PowerOfAttorney;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PowerOfAttorneyRepository extends MongoRepository<PowerOfAttorney, String> {

    List<PowerOfAttorney> findByGranteeNameAndAuthorization(String granteeName, Authorization authorization);
    List<PowerOfAttorney> findByGranteeNameAndAccountNumberAndAuthorization(String granteeName, String accountNumber, Authorization authorization);

}
