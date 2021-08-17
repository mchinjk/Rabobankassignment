package nl.rabobank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PowerOfAttorneyAlreadyExistsException extends RuntimeException {
    public PowerOfAttorneyAlreadyExistsException() {
        super("Power of attorney already exists");
    }
}
