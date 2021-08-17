package nl.rabobank.service.implementation;

import nl.rabobank.account.Account;
import nl.rabobank.authorizations.Authorization;
import nl.rabobank.authorizations.PowerOfAttorney;
import nl.rabobank.exceptions.AccountNotFoundException;
import nl.rabobank.exceptions.GrantToAccountHolderNotAllowedException;
import nl.rabobank.exceptions.PowerOfAttorneyAlreadyExistsException;
import nl.rabobank.mongo.repository.AccountRepository;
import nl.rabobank.mongo.repository.PowerOfAttorneyRepository;
import nl.rabobank.service.PowerOfAttorneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PowerOfAttorneyServiceImpl implements PowerOfAttorneyService {

    @Autowired
    private PowerOfAttorneyRepository powerOfAttorneyRepository;

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Creates a power of attorney.
     * @param powerOfAttorney the new power of attorney
     * @return the new power of attorney
     */
    @Override
    public PowerOfAttorney create(PowerOfAttorney powerOfAttorney) {
        validate(powerOfAttorney);
        return powerOfAttorneyRepository.save(powerOfAttorney);
    }

    /**
     * Retrieves a list of accounts granted to the grantee for given authorization type.
     * @param name the grantee for which to get accounts
     * @param authorization the authorization type for which to get accounts
     * @return list of accounts
     */
    public List<Account> getAccountsByGranteeAndAuthorizationType(String name, Authorization authorization) {
        return powerOfAttorneyRepository.findByGranteeNameAndAuthorization(name, authorization)
                .stream()
                .map(powerOfAttorney -> accountRepository.findById(powerOfAttorney.getAccountNumber()).orElse(null))
                .filter(account -> account!=null)
                .collect(Collectors.toList());
    }

    /**
     * Validates a power of attorney.
     * @param powerOfAttorney the power of attorney object
     * @throws AccountNotFoundException
     * @throws GrantToAccountHolderNotAllowedException
     * @throws PowerOfAttorneyAlreadyExistsException
     */
    private void validate(PowerOfAttorney powerOfAttorney) {

        // account for which access is to be granted must exist
        Optional<Account> account = accountRepository.findById(powerOfAttorney.getAccountNumber());
        if(!account.isPresent()) {
            throw new AccountNotFoundException(powerOfAttorney.getAccountNumber());
        }

        // can only grant access to someone other than account holder
        if(account.get().getAccountHolderName().equals(powerOfAttorney.getGranteeName()))
            throw new GrantToAccountHolderNotAllowedException(account.get().getAccountHolderName());

        // a duplicate power of attorney is not allowed
        if(powerOfAttorneyRepository.findByGranteeNameAndAccountNumberAndAuthorization(
                powerOfAttorney.getGranteeName(),
                powerOfAttorney.getAccountNumber(),
                powerOfAttorney.getAuthorization()).size()>0)
            throw new PowerOfAttorneyAlreadyExistsException();
    }

}
