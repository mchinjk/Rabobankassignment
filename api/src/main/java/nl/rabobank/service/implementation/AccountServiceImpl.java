package nl.rabobank.service.implementation;

import nl.rabobank.account.Account;
import nl.rabobank.exceptions.AccountNotFoundException;
import nl.rabobank.mongo.repository.AccountRepository;
import nl.rabobank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Creates an account.
     * @param account the new account to add to the system
     * @return the new account
     */
    @Override
    public Account create(Account account) {
        return accountRepository.save(account);
    }

    /**
     * Retrieves an account.
     * @param accountNumber the account number
     * @return account
     * @throws AccountNotFoundException
     */
    @Override
    public Account findByAccountNumber(String accountNumber) {
        Optional<Account> account = accountRepository.findById(accountNumber);
        if(!account.isPresent()) {
            throw new AccountNotFoundException(accountNumber);
        }
        return account.get();
    }


}
