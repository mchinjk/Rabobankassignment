package nl.rabobank.service;

import nl.rabobank.account.Account;
import nl.rabobank.authorizations.Authorization;
import nl.rabobank.authorizations.PowerOfAttorney;

import java.util.List;

public interface PowerOfAttorneyService {

    PowerOfAttorney create(PowerOfAttorney powerOfAttorney);

    List<Account> getAccountsByGranteeAndAuthorizationType(String name, Authorization authorization);

}
