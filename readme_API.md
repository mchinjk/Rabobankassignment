## Notes for the assignment

### REST endpoints
Included are:
* create an account
* retrieve an account by account number 
* create a power of attorney
* retrieve a list of accounts for which a specific authorization has been granted to a given grantee

### Model
The PowerOfAttorney model class includes the following properties:
* the grantee name
* the account number for which access/authorization is to be granted
* the authorization type READ or WRITE

### Documentation
Basic documentation has been added using swagger.

### Testing
* unit testing of service layer
* unit testing of controller layer
* integration testing of controllers

### Exception handling and basic data validation
Included is an exception handler with custom exceptions:
* AccountNotFoundException
* GrantToAccountHolderNotAllowedException
* PowerOfAttorneyAlreadyExistsException
* InvalidAccountTypeException
* InvalidAuthorizationException
* PropertyNullOrEmptyException

