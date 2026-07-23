package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> createVacancy(
            @RequestBody Vacancy vacancy
    ) {
        vacancyService.createVacancy(vacancy);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<Void> editVacancy(
            @PathVariable Integer id,
            @RequestBody Vacancy vacancy
    ) {
        vacancyService.editVacancy(
                id,
                vacancy
        );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteVacancy(
            @PathVariable Integer id
    ) {
        vacancyService.deleteVacancy(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("search/all")
    public ResponseEntity<List<Vacancy>>
    searchAllVacancies() {
        return ResponseEntity.ok(
                vacancyService.findAll()
        );
    }

    @GetMapping("search/active")
    public ResponseEntity<List<Vacancy>>
    searchActiveVacancies() {
        return ResponseEntity.ok(
                vacancyService.findAllActive()
        );
    }

    @GetMapping(
            "search/category/{categoryId}"
    )
    public ResponseEntity<List<Vacancy>>
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
            "search/responded/{applicantId}"
    )
    public ResponseEntity<List<Vacancy>>
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