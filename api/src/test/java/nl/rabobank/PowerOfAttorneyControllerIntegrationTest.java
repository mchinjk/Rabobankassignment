package nl.rabobank;

import nl.rabobank.account.Account;
import nl.rabobank.account.AccountType;
import nl.rabobank.authorizations.Authorization;
import nl.rabobank.authorizations.PowerOfAttorney;
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

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class PowerOfAttorneyControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // test data
    private static final String ACCOUNT_NUMBER = "123-1";
    private static final String ACCOUNT_NAME = "accountName";
    private static final AccountType ACCOUNT_TYPE = AccountType.PAYMENT;
    private static final Double BALANCE = 100d;
    private static final String GRANTEE_NAME = "grantee";
    private static final Authorization AUTHORIZATION = Authorization.READ;

    private Account account;
    private PowerOfAttorney powerOfAttorney;

    private String urlCreateAccount;
    private String urlCreatePowerOfAttorney;
    private String urlGetAccountsByNameAndAuthority;

    @Before
    public void setup() {
        account = new Account(ACCOUNT_NUMBER, ACCOUNT_NAME, ACCOUNT_TYPE, BALANCE);

        powerOfAttorney = new PowerOfAttorney("1", ACCOUNT_NUMBER, GRANTEE_NAME, AUTHORIZATION);

        String hostPort = "http://localhost:" + port;
        urlCreateAccount = hostPort + "/account/create";
        urlCreatePowerOfAttorney = hostPort + "/powerofattorney/create";
        urlGetAccountsByNameAndAuthority = hostPort + "/powerofattorney/" + GRANTEE_NAME + "/" + AUTHORIZATION.name();
    }

    @Test
    public void testCreatePowerOfAttorney() {
        ResponseEntity<Account> responseEntityAccount = restTemplate.postForEntity(urlCreateAccount, account, Account.class);

        ResponseEntity<PowerOfAttorney> responseEntityPowerOfAttorney =
                restTemplate.postForEntity(urlCreatePowerOfAttorney, powerOfAttorney, PowerOfAttorney.class);

        assertThat(responseEntityPowerOfAttorney.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void testGetAccountsByGranteeAndAuthorizationType() {
        ResponseEntity<Account> responseEntityAccount = restTemplate.postForEntity(urlCreateAccount, account, Account.class);

        ResponseEntity<List> responseEntityListOfAccounts = restTemplate.getForEntity(urlGetAccountsByNameAndAuthority, List.class);

        assertThat(responseEntityListOfAccounts.getStatusCode(), equalTo(HttpStatus.OK));

    }
}
