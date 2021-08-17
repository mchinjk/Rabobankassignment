package nl.rabobank.account;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import static nl.rabobank.validation.BasicValidation.checkIsNullOrEmpty;

@Document
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Account
{
    @Id
    private String accountNumber;
    private String accountHolderName;
    private AccountType accountType;
    private Double balance;

    public Account(String accountNumber, String accountHolderName, AccountType accountType, Double balance) {
        checkIsNullOrEmpty(accountNumber, "accountNumber");
        checkIsNullOrEmpty(accountHolderName, "accountHolderName");
        checkIsNullOrEmpty(accountType, "accountType");
        checkIsNullOrEmpty(balance, "balance");

        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.accountType = accountType;
        this.balance = balance;
    }

    public void setAccountNumber(String accountNumber) {
        checkIsNullOrEmpty(accountNumber, "accountNumber");
        this.accountNumber = accountNumber;
    }

    public void setAccountHolderName(String accountHolderName) {
        checkIsNullOrEmpty(accountHolderName, "accountHolderName");
        this.accountHolderName = accountHolderName;
    }

    public void setAccountType(AccountType accountType) {
        checkIsNullOrEmpty(accountType, "accountType");
        this.accountType = accountType;
    }

    public void setBalance(Double balance) {
        checkIsNullOrEmpty(balance, "balance");
        this.balance = balance;
    }
}
