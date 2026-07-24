package kg.attractor.jobsearch.exception;

import kg.attractor.jobsearch.service.ErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final ErrorService errorService;

    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    public ResponseEntity<ErrorResponseBody>
    validationHandler(
            MethodArgumentNotValidException exception
    ) {
        ErrorResponseBody response =
                errorService.makeResponse(
                        exception.getBindingResult()
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(
            NoSuchElementException.class
    )
    public ResponseEntity<ErrorResponseBody>
    notFoundHandler(
            NoSuchElementException exception
    ) {
        ErrorResponseBody response =
                errorService.makeResponse(
                        exception
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler({
            EmailAlreadyExistsException.class,
            ResponseAlreadyExistsException.class
    })
    public ResponseEntity<ErrorResponseBody>
    conflictHandler(
            IllegalArgumentException exception
    ) {
        ErrorResponseBody response =
                errorService.makeResponse(
                        exception
                );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(
            IllegalArgumentException.class
    )
    public ResponseEntity<ErrorResponseBody>
    badRequestHandler(
            IllegalArgumentException exception
    ) {
        ErrorResponseBody response =
                errorService.makeResponse(
                        exception
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(
            HttpMessageNotReadableException.class
    )
    public ResponseEntity<ErrorResponseBody>
    invalidRequestBodyHandler(
            HttpMessageNotReadableException exception
    ) {
        IllegalArgumentException responseException =
                new IllegalArgumentException(
                        "Request body has an invalid format"
                );

        ErrorResponseBody response =
                errorService.makeResponse(
                        responseException
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(
            DataIntegrityViolationException.class
    )
    public ResponseEntity<ErrorResponseBody>
    databaseConflictHandler(
            DataIntegrityViolationException exception
    ) {
        IllegalArgumentException responseException =
                new IllegalArgumentException(
                        "Operation violates database relationships"
                );

        ErrorResponseBody response =
                errorService.makeResponse(
                        responseException
                );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }
}