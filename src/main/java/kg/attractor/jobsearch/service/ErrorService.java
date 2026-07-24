package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.exception.ErrorResponseBody;
import org.springframework.validation.BindingResult;

public interface ErrorService {
    ErrorResponseBody makeResponse(BindingResult result);

    ErrorResponseBody makeResponse(Exception e);
}