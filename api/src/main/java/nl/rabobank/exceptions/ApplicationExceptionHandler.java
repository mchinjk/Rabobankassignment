package nl.rabobank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ApplicationExceptionHandler {

    // handle specific exceptions
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<?> handleAccountNotFoundException(
            AccountNotFoundException exception,
            WebRequest webRequest) {

        return new ResponseEntity(getErrorInformation(exception, webRequest), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PropertyNullOrEmptyException.class)
    public ResponseEntity<?> handlePropertyNullOrEmptyException(
            PropertyNullOrEmptyException exception,
            WebRequest webRequest) {

        return new ResponseEntity(getErrorInformation(exception, webRequest), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PowerOfAttorneyAlreadyExistsException.class)
    public ResponseEntity<?> handlePowerOfAttorneyAlreadyExistsException(
            PowerOfAttorneyAlreadyExistsException exception,
            WebRequest webRequest) {

        return new ResponseEntity(getErrorInformation(exception, webRequest), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GrantToAccountHolderNotAllowedException.class)
    public ResponseEntity<?> handleGrantorAndGranteeSameException(
            GrantToAccountHolderNotAllowedException exception,
            WebRequest webRequest) {

        return new ResponseEntity(getErrorInformation(exception, webRequest), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidAccountTypeException.class)
    public ResponseEntity<?> handleInvalidAccountTypeException(
            InvalidAccountTypeException exception,
            WebRequest webRequest) {

        return new ResponseEntity(getErrorInformation(exception, webRequest), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidAuthorizationTypeException.class)
    public ResponseEntity<?> handleInvalidAuthorizationTypeException(
            InvalidAuthorizationTypeException exception,
            WebRequest webRequest) {

        return new ResponseEntity(getErrorInformation(exception, webRequest), HttpStatus.BAD_REQUEST);
    }

    private ErrorInformation getErrorInformation(Exception exception, WebRequest request) {
        /* get a url from the request and send to client */
        return new ErrorInformation(new Date(), exception.getMessage(), request.getDescription(false));
    }

    // handle general exceptions

}
