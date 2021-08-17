package nl.rabobank.dto;

import lombok.*;
import nl.rabobank.account.AccountType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AccountDTO
{
     private String accountNumber;
     private String accountHolderName;
     private AccountType accountType;
     private Double balance;
}
