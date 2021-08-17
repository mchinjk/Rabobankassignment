package nl.rabobank;

import nl.rabobank.account.Account;
import nl.rabobank.account.AccountType;
import nl.rabobank.exceptions.AccountNotFoundException;
import nl.rabobank.mongo.repository.AccountRepository;
import nl.rabobank.service.implementation.AccountServiceImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class AccountServiceImplUnitTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    // test data
    private static final String ACCOUNT_NUMBER = "123-1";
    private static final String ACCOUNT_NAME = "accountName";
    private static final AccountType ACCOUNT_TYPE = AccountType.PAYMENT;
    private static final Double BALANCE = 100d;

    private static Account account;

    @BeforeClass
    public static void setup() {
        account = new Account(ACCOUNT_NUMBER, ACCOUNT_NAME, ACCOUNT_TYPE, BALANCE);
    }

    @Test
    public void testFindAccountSuccess() {

        when(accountRepository.findById(ACCOUNT_NUMBER)).thenReturn(Optional.of(account));

        final Account accountResult = accountService.findByAccountNumber(ACCOUNT_NUMBER);
        Assert.assertEquals(accountResult.getAccountNumber(), ACCOUNT_NUMBER);
        Assert.assertEquals(accountResult.getAccountHolderName(), ACCOUNT_NAME);
        Assert.assertEquals(accountResult.getAccountType(), ACCOUNT_TYPE);
        Assert.assertEquals(accountResult.getBalance(), BALANCE);

        verify(accountRepository).findById(ACCOUNT_NUMBER);
    }

    @Test(expected = AccountNotFoundException.class)
    public void testFindAccountFailure() {

        when(accountRepository.findById(any())).thenReturn(Optional.empty());
        accountService.findByAccountNumber(ACCOUNT_NUMBER);
    }
}
