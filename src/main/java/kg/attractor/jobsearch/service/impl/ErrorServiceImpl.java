package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.exception.ErrorResponseBody;
import kg.attractor.jobsearch.service.ErrorService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ErrorServiceImpl
        implements ErrorService {

    @Override
    public ErrorResponseBody makeResponse(
            BindingResult result
    ) {
        Map<String, List<String>> reasons =
                new LinkedHashMap<>();

        result.getFieldErrors()
                .stream()
                .filter(error ->
                        error.getDefaultMessage()
                                != null
                )
                .forEach(error ->
                        reasons.computeIfAbsent(
                                        error.getField(),
                                        key ->
                                                new ArrayList<>()
                                )
                                .add(
                                        error.getDefaultMessage()
                                )
                );

        result.getGlobalErrors()
                .stream()
                .filter(error ->
                        error.getDefaultMessage()
                                != null
                )
                .forEach(error ->
                        reasons.computeIfAbsent(
                                        error.getObjectName(),
                                        key ->
                                                new ArrayList<>()
                                )
                                .add(
                                        error.getDefaultMessage()
                                )
                );

        return ErrorResponseBody.builder()
                .title("Validation error")
                .reasons(reasons)
                .build();
    }

    @Override
    public ErrorResponseBody makeResponse(
            Exception exception
    ) {
        String message =
                exception.getMessage();

        if (message == null
                || message.isBlank()) {
            message = "Request processing error";
        }

        return ErrorResponseBody.builder()
                .title(message)
                .reasons(
                        Map.of(
                                "error",
                                List.of(message)
                        )
                )
                .build();
    }
}