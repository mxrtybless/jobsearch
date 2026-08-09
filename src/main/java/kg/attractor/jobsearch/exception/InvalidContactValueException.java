package kg.attractor.jobsearch.exception;

public class InvalidContactValueException
        extends IllegalArgumentException {

    public InvalidContactValueException(
            String contactType
    ) {
        super(
                "Invalid value for contact type "
                        + contactType
        );
    }
}