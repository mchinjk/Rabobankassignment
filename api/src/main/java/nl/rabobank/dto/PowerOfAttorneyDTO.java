package nl.rabobank.dto;

import lombok.*;
import nl.rabobank.authorizations.Authorization;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class PowerOfAttorneyDTO
{
    private String id;
    private String accountNumber;
    private String granteeName;
    private Authorization authorization;
}
