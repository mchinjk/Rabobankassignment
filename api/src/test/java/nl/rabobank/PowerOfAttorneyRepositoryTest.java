package nl.rabobank;

import nl.rabobank.authorizations.Authorization;
import nl.rabobank.authorizations.PowerOfAttorney;
import nl.rabobank.mongo.repository.PowerOfAttorneyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class PowerOfAttorneyRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PowerOfAttorneyRepository powerOfAttorneyRepository;

    @Test
    public void testDataWithMongoTemplate() {
        PowerOfAttorney p1 = new PowerOfAttorney("1", "123-0", "name1", Authorization.WRITE);
        PowerOfAttorney p2 = new PowerOfAttorney("2", "123-1", "name2", Authorization.READ);
        PowerOfAttorney p3 = new PowerOfAttorney("3", "123-2", "name1", Authorization.WRITE);

        mongoTemplate.insert(p1);
        mongoTemplate.insert(p2);
        mongoTemplate.insert(p3);

        assertEquals(1, powerOfAttorneyRepository.findByGranteeNameAndAccountNumberAndAuthorization("name2", "123-1", Authorization.READ).size());
        assertEquals(2, powerOfAttorneyRepository.findByGranteeNameAndAuthorization("name1", Authorization.WRITE).size());
    }
}
