package nl.rabobank.authorizations;

import com.fasterxml.jackson.annotation.JsonCreator;
import nl.rabobank.exceptions.InvalidAuthorizationTypeException;

public enum Authorization {
    READ,
    WRITE;

    @JsonCreator // This is the factory method and must be static
    public static Authorization getFromTextValue(String authorization) {
        try {
            return Authorization.valueOf(authorization);
        } catch (IllegalArgumentException e) {
            throw new InvalidAuthorizationTypeException(authorization);
        }
    }
}
