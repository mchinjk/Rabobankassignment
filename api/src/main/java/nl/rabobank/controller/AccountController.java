package nl.rabobank.controller;

import nl.rabobank.account.Account;
import nl.rabobank.dto.AccountDTO;
import nl.rabobank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static nl.rabobank.controller.util.Converter.convertToAccount;
import static nl.rabobank.controller.util.Converter.convertToAccountDTO;

/**
 * Implements a REST-based controller for the Account API.
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * Creates an account.
     * @param accountDTO the new account to add to the system
     * @return the new account
     */
    @PostMapping(value = "/create")
    public AccountDTO create(@RequestBody AccountDTO accountDTO) {
        Account account = convertToAccount(accountDTO);
        Account accountNew = accountService.create(account);
        return convertToAccountDTO(accountNew);
    }

    /**
     * Retrieves an account.
     * @param accountNumber the account number
     * @return account
     */
    @GetMapping(value = "/{accountNumber}")
    public AccountDTO getByAccountNumber(@PathVariable("accountNumber") String accountNumber) {
        Account account = accountService.findByAccountNumber(accountNumber);
        return convertToAccountDTO(account);
    }
}
