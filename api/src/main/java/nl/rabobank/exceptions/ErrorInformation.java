package nl.rabobank.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * This class contains the error information returned to the client
 * when an exception occurs.
 */
@AllArgsConstructor
@Getter
@Setter
public class ErrorInformation {

    private Date dateTime;
    private String message;
    private String description;
}
