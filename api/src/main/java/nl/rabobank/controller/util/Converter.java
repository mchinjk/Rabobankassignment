package nl.rabobank.controller.util;

import nl.rabobank.account.Account;
import nl.rabobank.authorizations.PowerOfAttorney;
import nl.rabobank.dto.AccountDTO;
import nl.rabobank.dto.PowerOfAttorneyDTO;
import org.springframework.beans.BeanUtils;

/**
 * Implements methods for converting DTO objects and domain objects.
 */
public class Converter {

    public static AccountDTO convertToAccountDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        BeanUtils.copyProperties(account, accountDTO);
        return accountDTO;
    }

    public  static Account convertToAccount(AccountDTO accountDTO) {
        Account account = new Account();
        BeanUtils.copyProperties(accountDTO, account);
        return account;
    }

    public static PowerOfAttorneyDTO convertToPowerOfAttorneyDTO(PowerOfAttorney powerOfAttorney) {
        PowerOfAttorneyDTO powerOfAttorneyDTO = new PowerOfAttorneyDTO();
        BeanUtils.copyProperties(powerOfAttorney, powerOfAttorneyDTO);
        return powerOfAttorneyDTO;
    }

    public static PowerOfAttorney convertToPowerOfAttorney(PowerOfAttorneyDTO powerOfAttorneyDTO) {
        PowerOfAttorney powerOfAttorney = new PowerOfAttorney();
        BeanUtils.copyProperties(powerOfAttorneyDTO, powerOfAttorney);
        return powerOfAttorney;
    }
}
