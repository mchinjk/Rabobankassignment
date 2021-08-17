package nl.rabobank.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import nl.rabobank.exceptions.InvalidAccountTypeException;

public enum AccountType {
    SAVINGS,
    PAYMENT;

    @JsonCreator // This is the factory method and must be static
    public static AccountType getFromTextValue(String accountType) {
        try {
            return AccountType.valueOf(accountType);
        }
        catch (IllegalArgumentException e) {
            throw new InvalidAccountTypeException(accountType);
        }
    }
}
