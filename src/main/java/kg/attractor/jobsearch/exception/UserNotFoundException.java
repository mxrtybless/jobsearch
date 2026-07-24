package kg.attractor.jobsearch.exception;

import java.util.NoSuchElementException;

public class UserNotFoundException
        extends NoSuchElementException {

    public UserNotFoundException(Integer id) {
        super(
                "User with id "
                        + id
                        + " not found"
        );
    }
}