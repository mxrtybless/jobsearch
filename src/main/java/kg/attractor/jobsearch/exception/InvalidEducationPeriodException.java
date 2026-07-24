package kg.attractor.jobsearch.exception;

public class InvalidEducationPeriodException
        extends IllegalArgumentException {

    public InvalidEducationPeriodException(
            String institution
    ) {
        super(
                "Education end date must not be before start date for institution: "
                        + institution
        );
    }
}