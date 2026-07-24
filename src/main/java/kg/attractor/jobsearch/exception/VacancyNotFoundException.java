package kg.attractor.jobsearch.exception;

import java.util.NoSuchElementException;

public class VacancyNotFoundException
        extends NoSuchElementException {

    public VacancyNotFoundException(Integer id) {
        super(
                "Vacancy with id "
                        + id
                        + " not found"
        );
    }
}