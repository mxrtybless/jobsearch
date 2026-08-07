package kg.attractor.jobsearch.exception;

import java.util.NoSuchElementException;

public class ContactTypeNotFoundException
        extends NoSuchElementException {

    public ContactTypeNotFoundException(Integer id) {
        super(
                "Contact type with id "
                        + id
                        + " not found"
        );
    }
}