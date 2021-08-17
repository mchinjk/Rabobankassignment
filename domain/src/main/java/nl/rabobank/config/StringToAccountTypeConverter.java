package nl.rabobank.config;

import nl.rabobank.account.AccountType;
import nl.rabobank.exceptions.InvalidAccountTypeException;
import org.springframework.core.convert.converter.Converter;

/**
 * Converts a string value to an AccountType value when a web request is made with
 * that string value as a path variable.
 * When that conversion fails an InvalidAccountTypeException is thrown which is then
 * handled by the ApplicationExceptionHandler.
 */
public class StringToAccountTypeConverter implements Converter<String, AccountType> {
    @Override
    public AccountType convert(String s) {
        try {
            return AccountType.valueOf(s);
        } catch (IllegalArgumentException e) {
            throw new InvalidAccountTypeException(s);
        }
    }
}
