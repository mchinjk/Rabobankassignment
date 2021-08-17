package nl.rabobank.service;

import nl.rabobank.account.Account;

public interface AccountService {

    Account create(Account account);

    Account findByAccountNumber(String accountNumber);
}

