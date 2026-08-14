package kg.attractor.jobsearch.controller.api;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("vacancies")
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;

    @PostMapping("create")
    public ResponseEntity<Integer> createVacancy(
            @Valid
            @RequestBody
            VacancyDto vacancyDto,
            Authentication authentication
    ) {
        Integer vacancyId =
                vacancyService.createVacancy(
                        vacancyDto,
                        authentication.getName()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(vacancyId);
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<Void> editVacancy(
            @PathVariable Integer id,
            @Valid
            @RequestBody
            VacancyDto vacancyDto,
            Authentication authentication
    ) {
        vacancyService.editVacancy(
                id,
                vacancyDto,
                authentication.getName()
        );

        return ResponseEntity
                .ok()
                .build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteVacancy(
            @PathVariable Integer id,
            Authentication authentication
    ) {
        vacancyService.deleteVacancy(
                id,
                authentication.getName()
        );

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("search/id/{id}")
    public ResponseEntity<VacancyDto>
    findVacancyById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(
                vacancyService.findById(id)
        );
    }

    @GetMapping("search/all")
    public ResponseEntity<List<VacancyDto>>
    searchAllVacancies() {
        return ResponseEntity.ok(
                vacancyService.findAll()
        );
    }

    @GetMapping("search/active")
    public ResponseEntity<List<VacancyDto>>
    searchActiveVacancies() {
        return ResponseEntity.ok(
                vacancyService.findAllActive()
        );
    }

    @GetMapping(
            "search/category/{categoryId}"
    )
    public ResponseEntity<List<VacancyDto>>
    searchVacanciesByCategory(
            @PathVariable Integer categoryId
    ) {
        return ResponseEntity.ok(
                vacancyService.findByCategoryId(
                        categoryId
                )
        );
    }

    @GetMapping(
            "search/employer/{authorId}"
    )
    public ResponseEntity<List<VacancyDto>>
    searchVacanciesByEmployer(
            @PathVariable Integer authorId
    ) {
        return ResponseEntity.ok(
                vacancyService.findByAuthorId(
                        authorId
                )
        );
    }

    @GetMapping(
            "search/responded/{applicantId}"
    )
    public ResponseEntity<List<VacancyDto>>
    searchRespondedVacancies(
            @PathVariable Integer applicantId
    ) {
        return ResponseEntity.ok(
                vacancyService
                        .findRespondedByApplicantId(
                                applicantId
                        )
        );
    }
}