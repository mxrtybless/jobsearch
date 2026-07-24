package kg.attractor.jobsearch.exception;

public class ResponseAlreadyExistsException
        extends IllegalArgumentException {

    public ResponseAlreadyExistsException(
            Integer resumeId,
            Integer vacancyId
    ) {
        super(
                "Response for resume "
                        + resumeId
                        + " and vacancy "
                        + vacancyId
                        + " already exists"
        );
    }
}