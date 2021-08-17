package nl.rabobank.config;

import nl.rabobank.authorizations.Authorization;
import nl.rabobank.exceptions.InvalidAuthorizationTypeException;
import org.springframework.core.convert.converter.Converter;

/**
 * Converts a string value to an Authorization value when a web request is made with
 * that string value as a path variable.
 * When that conversion fails an InvalidAuthorizationTypeException is thrown which is then
 * handled by the ApplicationExceptionHandler.
 */
public class StringToAuthorizationConverter implements Converter<String, Authorization> {
    @Override
    public Authorization convert(String s) {
        try {
            return Authorization.valueOf(s);
        } catch (IllegalArgumentException e) {
            throw new InvalidAuthorizationTypeException(s);
        }
    }
}
