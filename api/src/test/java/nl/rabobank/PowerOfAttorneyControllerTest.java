package nl.rabobank;

import nl.rabobank.account.Account;
import nl.rabobank.account.AccountType;
import nl.rabobank.authorizations.Authorization;
import nl.rabobank.authorizations.PowerOfAttorney;
import nl.rabobank.controller.PowerOfAttorneyController;
import nl.rabobank.exceptions.AccountNotFoundException;
import nl.rabobank.exceptions.GrantToAccountHolderNotAllowedException;
import nl.rabobank.exceptions.PowerOfAttorneyAlreadyExistsException;
import nl.rabobank.service.PowerOfAttorneyService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PowerOfAttorneyController.class)
@AutoConfigureJsonTesters
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class PowerOfAttorneyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<PowerOfAttorney> json;

    @MockBean
    private PowerOfAttorneyService powerOfAttorneyService;

    // test data
    private static final String ACCOUNT_NAME = "accountName";
    private static final String ACCOUNT_NUMBER = "123-1";
    private static final AccountType ACCOUNT_TYPE = AccountType.PAYMENT;
    private static final Double BALANCE = 100d;

    private static final String GRANTEE_NAME = "grantee";
    private static final Authorization AUTHORIZATION = Authorization.READ;
    private static final String INVALID_AUTHORIZATION = "DOWNLOAD";

    private static Account account;
    private static List<Account> accounts;

    private static PowerOfAttorney powerOfAttorney;

    private static final String URL_CREATE = "/powerofattorney/create";

    @BeforeClass
    public static void setup() {
        account = new Account(ACCOUNT_NUMBER, ACCOUNT_NAME, ACCOUNT_TYPE, BALANCE);
        accounts = Arrays.asList(account);
        powerOfAttorney = new PowerOfAttorney("1", ACCOUNT_NUMBER, GRANTEE_NAME, AUTHORIZATION);
    }

    @Test
    public void testCreatePowerOfAttorneySuccess() throws Exception {
        given(powerOfAttorneyService.create(any())).willReturn(powerOfAttorney);
        postPowerOfAttorney(json.write(powerOfAttorney).getJson(), status().isOk());
    }

    @Test
    public void testCreatePowerOfAttorneyAccountNotFound() throws Exception {
        given(powerOfAttorneyService.create(any())).willThrow(new AccountNotFoundException(ACCOUNT_NUMBER));
        postPowerOfAttorney(json.write(powerOfAttorney).getJson(), status().isNotFound());
    }

    @Test
    public void testCreatePowerOfAttorneyGrantToAccountHolderNotAllowed() throws Exception {
        given(powerOfAttorneyService.create(any())).willThrow(new GrantToAccountHolderNotAllowedException(ACCOUNT_NAME));
        postPowerOfAttorney(json.write(powerOfAttorney).getJson(), status().isBadRequest());
    }

    @Test
    public void testCreatePowerOfAttorneyAlreadyExists() throws Exception {
        given(powerOfAttorneyService.create(any())).willThrow(new PowerOfAttorneyAlreadyExistsException());
        postPowerOfAttorney(json.write(powerOfAttorney).getJson(), status().isBadRequest());
    }

    @Test
    public void testCreatePowerOfAttorneyMissingGranteeName() throws Exception {
        String bodyJson = format("{\"accountNumber\":\"%s\",\"authorization\":\"%s\"}",
                ACCOUNT_NUMBER, AUTHORIZATION);
        postPowerOfAttorney(bodyJson, status().isBadRequest());
    }

    @Test
    public void testCreatePowerOfAttorneyEmptyGranteeName() throws Exception {
        String bodyJson = format("{\"accountNumber\":\"%s\",\"granteeName\":\"%s\",\"authorization\":\"%s\"}",
                ACCOUNT_NUMBER, "", AUTHORIZATION);
        postPowerOfAttorney(bodyJson, status().isBadRequest());
    }

    @Test
    public void testCreatePowerOfAttorneyInvalidAuthorization() throws Exception {
        String bodyJson = format("{\"accountNumber\":\"%s\",\"granteeName\":\"%s\",\"authorization\":\"%s\"}",
                ACCOUNT_NUMBER, GRANTEE_NAME, INVALID_AUTHORIZATION);
        postPowerOfAttorney(bodyJson, status().isBadRequest());
    }

    /**
     * Performs a post request with given request body and expected status
     * @param requestBodyJson
     * @param statusMatcher
     * @throws Exception
     */
    private void postPowerOfAttorney(String requestBodyJson, ResultMatcher statusMatcher) throws Exception {
        mockMvc.perform(post(URL_CREATE)
                .content(requestBodyJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(statusMatcher)
                .andDo(print());
    }

    @Test
    public void testGetAccountsByGranteeAndAuthorizationTypeSuccess() throws Exception {
        given(powerOfAttorneyService.getAccountsByGranteeAndAuthorizationType(GRANTEE_NAME, AUTHORIZATION)).willReturn(accounts);

        mockMvc.perform( get("/powerofattorney/" + GRANTEE_NAME + "/" + AUTHORIZATION.name()))
                .andExpect(status().isOk())
                .andExpect(content().contentType((MediaType.APPLICATION_JSON)))
                .andExpect(jsonPath("$.[0].accountNumber").value(ACCOUNT_NUMBER))
                .andExpect(jsonPath("$.[0].accountHolderName").value(ACCOUNT_NAME))
                .andExpect(jsonPath("$.[0].accountType").value(ACCOUNT_TYPE.name()))
                .andExpect(jsonPath("$.[0].balance").value(BALANCE));
    }

    @Test
    public void testGetAccountsByGranteeAndAuthorizationTypeInvalid() throws Exception {
        mockMvc.perform( get("/powerofattorney/" + GRANTEE_NAME + "/" + "INVALID_AUTHORIZATION_TYPE"))
                .andExpect(status().isBadRequest());
    }
}
