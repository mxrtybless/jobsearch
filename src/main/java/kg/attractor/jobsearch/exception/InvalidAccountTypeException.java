package kg.attractor.jobsearch.exception;

import kg.attractor.jobsearch.model.AccountType;

public class InvalidAccountTypeException
        extends IllegalArgumentException {

    public InvalidAccountTypeException(
            Integer userId,
            AccountType expectedAccountType
    ) {
        super(
                "User with id "
                        + userId
                        + " must have account type "
                        + expectedAccountType
        );
    }
}