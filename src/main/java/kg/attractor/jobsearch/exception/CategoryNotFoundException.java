package kg.attractor.jobsearch.exception;

import java.util.NoSuchElementException;

public class CategoryNotFoundException
        extends NoSuchElementException {

    public CategoryNotFoundException(Integer id) {
        super(
                "Category with id "
                        + id
                        + " not found"
        );
    }
}