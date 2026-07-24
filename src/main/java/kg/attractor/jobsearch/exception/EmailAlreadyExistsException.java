package kg.attractor.jobsearch.exception;

public class EmailAlreadyExistsException
        extends IllegalArgumentException {

    public EmailAlreadyExistsException(
            String email
    ) {
        super(
                "User with email "
                        + email
                        + " already exists"
        );
    }
}