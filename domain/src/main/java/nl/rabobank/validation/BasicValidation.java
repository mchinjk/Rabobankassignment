package nl.rabobank.validation;

import nl.rabobank.exceptions.PropertyNullOrEmptyException;

public class BasicValidation {

    public static <T> void checkIsNullOrEmpty(T object, String name) {
        if(object==null || (object instanceof String && ((String) object).isEmpty()))
            throw new PropertyNullOrEmptyException(name + " cannot be null or empty");
    }
}
