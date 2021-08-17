package nl.rabobank.authorizations;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import static nl.rabobank.validation.BasicValidation.checkIsNullOrEmpty;

@Document
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class PowerOfAttorney
{
    @Id
    private String id;
    private String accountNumber;
    private String granteeName;
    private Authorization authorization;

    public PowerOfAttorney(String id, String accountNumber, String granteeName, Authorization authorization) {

        checkIsNullOrEmpty(accountNumber, "accountNumber");
        checkIsNullOrEmpty(granteeName, "granteeName");
        checkIsNullOrEmpty(authorization, "authorization");

        this.id = id;
        this.accountNumber = accountNumber;
        this.granteeName = granteeName;
        this.authorization = authorization;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAccountNumber(String accountNumber) {
        checkIsNullOrEmpty(accountNumber, "accountNumber");
        this.accountNumber = accountNumber;
    }

    public void setGranteeName(String granteeName) {
        checkIsNullOrEmpty(granteeName, "granteeName");
        this.granteeName = granteeName;
    }

    public void setAuthorization(Authorization authorization) {
        checkIsNullOrEmpty(authorization, "authorization");
        this.authorization = authorization;
    }

}
