package nl.rabobank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class GrantToAccountHolderNotAllowedException extends RuntimeException{
    public GrantToAccountHolderNotAllowedException(String accountHolder) {
        super(format("Grantee cannot be the same as account holder [%s]", accountHolder));
    }
}
