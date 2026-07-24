package kg.attractor.jobsearch.exception;

import java.util.NoSuchElementException;

public class ResumeNotFoundException
        extends NoSuchElementException {

    public ResumeNotFoundException(Integer id) {
        super(
                "Resume with id "
                        + id
                        + " not found"
        );
    }
}