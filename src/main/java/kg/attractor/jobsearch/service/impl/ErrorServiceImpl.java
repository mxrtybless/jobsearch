package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.exception.ErrorResponseBody;
import kg.attractor.jobsearch.service.ErrorService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ErrorServiceImpl implements ErrorService {

    @Override
    public ErrorResponseBody makeResponse(BindingResult result) {
        Map<String, List<String>> reasons = new HashMap<>();

        result.getFieldErrors().stream()
                .filter(e -> e.getDefaultMessage() != null)
                .forEach(e -> {
                    List<String> errors = new ArrayList<>();
                    errors.add(e.getDefaultMessage());

                    if (!reasons.containsKey(e.getField())) {
                        reasons.put(e.getField(), errors);
                    }
                });

        return ErrorResponseBody.builder()
                .title("Validation error")
                .reasons(reasons)
                .build();
    }

    @Override
    public ErrorResponseBody makeResponse(Exception e) {
        String errMessage = e.getMessage();
        return ErrorResponseBody.builder()
                .title(errMessage)
                .reasons(Map.of("Exceptions", List.of(errMessage)))
                .build();
    }
}