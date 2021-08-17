package nl.rabobank;

import nl.rabobank.account.Account;
import nl.rabobank.account.AccountType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class AccountControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // test data
    private static final String ACCOUNT_NUMBER = "123-1";
    private static final String ACCOUNT_NAME = "accountName";
    private static final AccountType ACCOUNT_TYPE = AccountType.PAYMENT;
    private static final Double BALANCE = 100d;

    private Account account;

    private String urlCreate;
    private String urlGetByAccountNumber;

    @Before
    public void setup() {
        account = new Account(ACCOUNT_NUMBER, ACCOUNT_NAME, ACCOUNT_TYPE, BALANCE);

        String hostPort = "http://localhost:" + port;
        urlCreate = hostPort + "/account/create";
        urlGetByAccountNumber = hostPort + "/account/" + ACCOUNT_NUMBER;
    }

    @Test
    public void testCreateAccount() throws Exception {
        ResponseEntity<Account> responseEntity = restTemplate.postForEntity(urlCreate, account, Account.class);

        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void testGetByAccountNumber() throws Exception {
        ResponseEntity<Account> responseEntityPost = restTemplate.postForEntity(urlCreate, account, Account.class);

        ResponseEntity<Account> responseEntityGet = restTemplate.getForEntity(urlGetByAccountNumber, Account.class);

        assertThat(responseEntityGet.getStatusCode(), equalTo(HttpStatus.OK));
    }
}