package kg.attractor.jobsearch.controller.api;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.RespondedApplicantDto;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.RespondedApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("responses")
@RequiredArgsConstructor
public class RespondedApplicantController {

    private final RespondedApplicantService
            respondedApplicantService;

    @PostMapping("create")
    public ResponseEntity<Void> createResponse(
            @Valid
            @RequestBody
            RespondedApplicantDto respondedApplicantDto,
            Authentication authentication
    ) {
        respondedApplicantService
                .createResponse(
                        respondedApplicantDto,
                        authentication.getName()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping(
            "search/vacancy/{vacancyId}"
    )
    public ResponseEntity<List<User>>
    searchApplicantsByVacancy(
            @PathVariable Integer vacancyId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                respondedApplicantService
                        .findApplicantsByVacancyId(
                                vacancyId,
                                authentication.getName()
                        )
        );
    }
}