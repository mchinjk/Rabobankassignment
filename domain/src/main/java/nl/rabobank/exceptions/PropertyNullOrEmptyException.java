package nl.rabobank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PropertyNullOrEmptyException extends RuntimeException {
    public PropertyNullOrEmptyException(String message) {
        super(message);
    }
}
