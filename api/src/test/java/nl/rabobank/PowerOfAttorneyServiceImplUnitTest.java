package nl.rabobank;

import nl.rabobank.account.Account;
import nl.rabobank.account.AccountType;
import nl.rabobank.authorizations.Authorization;
import nl.rabobank.authorizations.PowerOfAttorney;
import nl.rabobank.mongo.repository.AccountRepository;
import nl.rabobank.mongo.repository.PowerOfAttorneyRepository;
import nl.rabobank.service.implementation.PowerOfAttorneyServiceImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class PowerOfAttorneyServiceImplUnitTest {

    @Mock
    private PowerOfAttorneyRepository powerOfAttorneyRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private PowerOfAttorneyServiceImpl powerOfAttorneyService;

    // test data
    private static final String ACCOUNT_NAME = "accountName";
    private static final String ACCOUNT_NUMBER = "123-1";
    private static final AccountType ACCOUNT_TYPE = AccountType.PAYMENT;
    private static final Double BALANCE = 100d;

    private static final String GRANTEE_NAME = "grantee";
    private static final Authorization AUTHORIZATION = Authorization.READ;

    private static Account account;
    private static PowerOfAttorney powerOfAttorney;
    private static List<PowerOfAttorney> powerOfAttorneys;


    @BeforeClass
    public static void setup() {
        account = new Account(ACCOUNT_NUMBER, ACCOUNT_NAME, ACCOUNT_TYPE, BALANCE);
        powerOfAttorney = new PowerOfAttorney("1", ACCOUNT_NUMBER, GRANTEE_NAME, AUTHORIZATION);
        powerOfAttorneys = Arrays.asList(powerOfAttorney);
    }

    @Test
    public void testGetAccountsByGranteeAndAuthorizationTypeSuccess() {

        when(powerOfAttorneyRepository.findByGranteeNameAndAuthorization(GRANTEE_NAME, AUTHORIZATION)).thenReturn(powerOfAttorneys);
        when(accountRepository.findById(ACCOUNT_NUMBER)).thenReturn(Optional.of(account));

        final List<Account> accountResult = powerOfAttorneyService.getAccountsByGranteeAndAuthorizationType (GRANTEE_NAME, AUTHORIZATION);
        Assert.assertEquals(accountResult.get(0).getAccountNumber(), ACCOUNT_NUMBER);
        Assert.assertEquals(accountResult.get(0).getAccountHolderName(), ACCOUNT_NAME);
        Assert.assertEquals(accountResult.get(0).getAccountType(), AccountType.PAYMENT);
        Assert.assertEquals(accountResult.get(0).getBalance(), BALANCE);

        verify(powerOfAttorneyRepository).findByGranteeNameAndAuthorization(GRANTEE_NAME, AUTHORIZATION);
        verify(accountRepository).findById(ACCOUNT_NUMBER);
    }

    @Test
    public void testCreateSuccess() {
        when(powerOfAttorneyRepository.findByGranteeNameAndAccountNumberAndAuthorization(GRANTEE_NAME, ACCOUNT_NUMBER, AUTHORIZATION))
                .thenReturn(new ArrayList<PowerOfAttorney>());
        when(accountRepository.findById(ACCOUNT_NUMBER)).thenReturn(Optional.of(account));
        when(powerOfAttorneyRepository.save(powerOfAttorney)).thenReturn(powerOfAttorney);

        final PowerOfAttorney powerOfAttorneyResult = powerOfAttorneyService.create(powerOfAttorney);
        Assert.assertEquals(powerOfAttorneyResult.getAccountNumber(), ACCOUNT_NUMBER);
        Assert.assertEquals(powerOfAttorneyResult.getGranteeName(), GRANTEE_NAME);
        Assert.assertEquals(powerOfAttorneyResult.getAuthorization(), AUTHORIZATION);

        verify(powerOfAttorneyRepository).findByGranteeNameAndAccountNumberAndAuthorization(GRANTEE_NAME, ACCOUNT_NUMBER, AUTHORIZATION);
        verify(powerOfAttorneyRepository).save(powerOfAttorney);
        verify(accountRepository).findById(ACCOUNT_NUMBER);
    }
}
