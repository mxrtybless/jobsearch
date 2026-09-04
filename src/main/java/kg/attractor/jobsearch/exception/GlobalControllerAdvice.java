package kg.attractor.jobsearch.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
            HttpServletResponse response,
            Model model
    ) {
        return errorPage(
                request,
                response,
                model,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(
            NoSuchElementException.class
    )
    private String notFoundHandler(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) {
        return errorPage(
                request,
                response,
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
            HttpServletResponse response,
            Model model
    ) {
        return errorPage(
                request,
                response,
                model,
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(
            IllegalArgumentException.class
    )
    private String badRequestHandler(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) {
        return errorPage(
                request,
                response,
                model,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(
            HttpMessageNotReadableException.class
    )
    private String invalidRequestBodyHandler(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) {
        return errorPage(
                request,
                response,
                model,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(
            DataIntegrityViolationException.class
    )
    private String databaseConflictHandler(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) {
        return errorPage(
                request,
                response,
                model,
                HttpStatus.CONFLICT
        );
    }

    private String errorPage(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model,
            HttpStatus status
    ) {
        response.setStatus(
                status.value()
        );

        model.addAttribute(
                "status",
                status.value()
        );

        model.addAttribute(
                "reason",
                status.getReasonPhrase()
        );

        model.addAttribute(
                "path",
                request.getRequestURI()
        );

        return "error";
    }
}