package kg.attractor.jobsearch.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    private String validationHandler(
            HttpServletRequest request,
            Model model
    ) {
        return errorPage(
                request,
                model,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(
            NoSuchElementException.class
    )
    private String notFoundHandler(
            HttpServletRequest request,
            Model model
    ) {
        return errorPage(
                request,
                model,
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler({
            EmailAlreadyExistsException.class,
            ResponseAlreadyExistsException.class
    })
    private String conflictHandler(
            HttpServletRequest request,
            Model model
    ) {
        return errorPage(
                request,
                model,
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(
            IllegalArgumentException.class
    )
    private String badRequestHandler(
            HttpServletRequest request,
            Model model
    ) {
        return errorPage(
                request,
                model,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(
            HttpMessageNotReadableException.class
    )
    private String invalidRequestBodyHandler(
            HttpServletRequest request,
            Model model
    ) {
        return errorPage(
                request,
                model,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(
            DataIntegrityViolationException.class
    )
    private String databaseConflictHandler(
            HttpServletRequest request,
            Model model
    ) {
        return errorPage(
                request,
                model,
                HttpStatus.CONFLICT
        );
    }

    private String errorPage(
            HttpServletRequest request,
            Model model,
            HttpStatus status
    ) {
        model.addAttribute(
                "status",
                status.value()
        );

        model.addAttribute(
                "reason",
                status.getReasonPhrase()
        );

        model.addAttribute(
                "details",
                request
        );

        return "errors/error";
    }
}