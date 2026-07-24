package kg.attractor.jobsearch.exception;

public class InvalidExperienceRangeException
        extends IllegalArgumentException {

    public InvalidExperienceRangeException() {
        super(
                "Minimum experience must not be greater than maximum experience"
        );
    }
}