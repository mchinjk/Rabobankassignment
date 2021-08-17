package nl.rabobank;

import nl.rabobank.account.Account;
import nl.rabobank.account.AccountType;
import nl.rabobank.controller.AccountController;
import nl.rabobank.exceptions.AccountNotFoundException;
import nl.rabobank.service.AccountService;
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

import static java.lang.String.format;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
@AutoConfigureJsonTesters
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<Account> json;

    @MockBean
    private AccountService accountService;

    // test data
    private static final String ACCOUNT_NUMBER = "123-1";
    private static final String ACCOUNT_NAME = "accountName";
    private static final AccountType ACCOUNT_TYPE = AccountType.PAYMENT;
    private static final Double BALANCE = 100d;
    private static final String INVALID_ACCOUNT_TYPE = "DEBIT";

    private static final String URL_CREATE = "/account/create";
    private static final String URL_ACCOUNT_BY_NUMBER = "/account/" + ACCOUNT_NUMBER;

    private static Account account = new Account(ACCOUNT_NUMBER, ACCOUNT_NAME, ACCOUNT_TYPE, BALANCE);

    @Test
    public void testCreateAccountSuccess() throws Exception {
        given(accountService.create(any())).willReturn(account);
        performPost(json.write(account).getJson(), status().isOk());
    }

    @Test
    public void testCreateAccountMissingAccountHolderName() throws Exception {
        String accountJson = format("{\"accountNumber\":\"%s\",\"accountType\":\"%s\",\"balance\":%f}",
                ACCOUNT_NUMBER, ACCOUNT_TYPE, BALANCE);
        performPost(accountJson, status().isBadRequest());
    }

    @Test
    public void testCreateAccountEmptyAccountHolderName() throws Exception {
        String accountJson = format("{\"accountNumber\":\"%s\",\"accountHolderName\":\"%s\",\"accountType\":\"%s\",\"balance\":%f}",
                ACCOUNT_NUMBER, "", ACCOUNT_TYPE, BALANCE);
        performPost(accountJson, status().isBadRequest());
    }

    @Test
    public void testCreateAccountInvalidAccountType() throws Exception {
        String accountJson = format("{\"accountNumber\":\"%s\",\"accountHolderName\":\"%s\",\"accountType\":\"%s\",\"balance\":%f}",
                ACCOUNT_NUMBER, ACCOUNT_NAME, INVALID_ACCOUNT_TYPE, BALANCE);
        performPost(accountJson, status().isBadRequest());
    }

    /**
     * Performs a post request with given request body and expected status
     * @param requestBodyJson
     * @param statusMatcher
     * @throws Exception
     */
    private void performPost(String requestBodyJson, ResultMatcher statusMatcher) throws Exception {
        mockMvc.perform(post(URL_CREATE)
                .content(requestBodyJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(statusMatcher)
                .andDo(print());
    }

    @Test
    public void testGetByAccountNumberSuccess() throws Exception {
        given(accountService.findByAccountNumber(ACCOUNT_NUMBER)).willReturn(account);

        mockMvc.perform(get(URL_ACCOUNT_BY_NUMBER))
                .andExpect(status().isOk())
                .andExpect(content().contentType((MediaType.APPLICATION_JSON)))
                .andExpect(jsonPath("$.accountNumber").value(ACCOUNT_NUMBER))
                .andExpect(jsonPath("$.accountHolderName").value(ACCOUNT_NAME))
                .andExpect(jsonPath("$.accountType").value(ACCOUNT_TYPE.name()))
                .andExpect(jsonPath("$.balance").value(BALANCE));
    }

    @Test
    public void testGetByAccountNumberNotFound() throws Exception {
        given(accountService.findByAccountNumber(ACCOUNT_NUMBER))
                .willThrow(AccountNotFoundException.class);

        mockMvc.perform(get(URL_ACCOUNT_BY_NUMBER))
                .andExpect(status().isNotFound());
    }
}