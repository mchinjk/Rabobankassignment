package nl.rabobank.controller;

import nl.rabobank.account.Account;
import nl.rabobank.authorizations.Authorization;
import nl.rabobank.authorizations.PowerOfAttorney;
import nl.rabobank.dto.AccountDTO;
import nl.rabobank.dto.PowerOfAttorneyDTO;
import nl.rabobank.service.PowerOfAttorneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static nl.rabobank.controller.util.Converter.*;

/**
 * Implements a REST-based controller for the PowerOfAttorney API.
 */
@RestController
@RequestMapping("/powerofattorney")
public class PowerOfAttorneyController {

    @Autowired
    private PowerOfAttorneyService powerOfAttorneyService;

    /**
     * Creates a power of attorney.
     * @param powerOfAttorneyDTO the new power of attorney to add to the system
     * @return the new power of attorney
     */
    @PostMapping(value = "/create")
    public PowerOfAttorneyDTO createPowerOfAttorney(@RequestBody PowerOfAttorneyDTO powerOfAttorneyDTO) {
        PowerOfAttorney powerOfAttorney = convertToPowerOfAttorney(powerOfAttorneyDTO);
        PowerOfAttorney powerOfAttorneyNew = powerOfAttorneyService.create(powerOfAttorney);
        return convertToPowerOfAttorneyDTO(powerOfAttorneyNew);
    }

    /**
     * Retrieves a list of accounts granted to the grantee for given authorization type.
     * @param grantee the grantee for which to get accounts
     * @param authorization the authorization type for which to get accounts
     * @return list of accounts
     */
    @GetMapping(value = "/{grantee}/{authorization}")
    public List<AccountDTO> getAccountsByGranteeAndAuthorizationType(@PathVariable String grantee,
                                                                     @PathVariable Authorization authorization) {
        List<Account> accounts = powerOfAttorneyService.getAccountsByGranteeAndAuthorizationType(grantee, authorization);
        return accounts.stream().map(a->convertToAccountDTO(a)).collect(Collectors.toList());
    }
}
