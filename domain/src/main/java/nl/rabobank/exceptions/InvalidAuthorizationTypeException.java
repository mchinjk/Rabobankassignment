package nl.rabobank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidAuthorizationTypeException extends RuntimeException {
    public InvalidAuthorizationTypeException(String value) {
        super(format("Invalid authorization type: [%s]", value));
    }
}
