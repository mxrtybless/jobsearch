package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.VacancyCreateDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.dto.VacancyUpdateDto;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;

    @GetMapping("/vacancies")
    public ResponseEntity<List<VacancyDto>>
    getActiveVacancies() {

        return ResponseEntity.ok(
                vacancyService.getActiveVacancies()
        );
    }

    @GetMapping("/vacancies/{id}")
    public ResponseEntity<VacancyDto> getVacancyById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(
                vacancyService.getVacancyById(id)
        );
    }

    @GetMapping("/vacancies/employer/{employerId}")
    public ResponseEntity<List<VacancyDto>>
    getVacanciesByEmployer(
            @PathVariable Integer employerId
    ) {
        return ResponseEntity.ok(
                vacancyService.getVacanciesByEmployer(
                        employerId
                )
        );
    }

    @GetMapping(
            "/vacancies/responded/applicant/{applicantId}"
    )
    public ResponseEntity<List<VacancyDto>>
    getVacanciesRespondedByApplicant(
            @PathVariable Integer applicantId
    ) {
        return ResponseEntity.ok(
                vacancyService
                        .getVacanciesRespondedByApplicant(
                                applicantId
                        )
        );
    }

    @GetMapping("/vacancies/search")
    public ResponseEntity<List<VacancyDto>>
    searchVacancies(
            @RequestParam String query
    ) {
        return ResponseEntity.ok(
                vacancyService
                        .searchActiveVacanciesByName(query)
        );
    }

    @GetMapping("/vacancies/category/{categoryId}")
    public ResponseEntity<List<VacancyDto>>
    getVacanciesByCategory(
            @PathVariable Integer categoryId
    ) {
        return ResponseEntity.ok(
                vacancyService
                        .getActiveVacanciesByCategory(
                                categoryId
                        )
        );
    }

    @PostMapping("/vacancies")
    public ResponseEntity<VacancyDto> createVacancy(
            @RequestBody VacancyCreateDto dto
    ) {
        VacancyDto vacancy =
                vacancyService.createVacancy(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(vacancy);
    }

    @PutMapping("/vacancies/{id}")
    public ResponseEntity<VacancyDto> updateVacancy(
            @PathVariable Integer id,
            @RequestBody VacancyUpdateDto dto
    ) {
        return ResponseEntity.ok(
                vacancyService.updateVacancy(id, dto)
        );
    }

    @DeleteMapping("/vacancies/{id}")
    public ResponseEntity<Void> deleteVacancy(
            @PathVariable Integer id,
            @RequestParam Integer authorId
    ) {
        vacancyService.deleteVacancy(id, authorId);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>>
    handleBadRequest(
            IllegalArgumentException exception
    ) {
        return ResponseEntity.badRequest()
                .body(Map.of(
                        "error",
                        exception.getMessage()
                ));
    }
}