package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.RespondedApplicantCreateDto;
import kg.attractor.jobsearch.dto.RespondedApplicantDto;
import kg.attractor.jobsearch.service.RespondedApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RespondedApplicantController {
    private final RespondedApplicantService respondedApplicantService;

    @PostMapping("/responses")
    public ResponseEntity<RespondedApplicantDto> createResponse(@RequestBody RespondedApplicantCreateDto dto) {
        RespondedApplicantDto response = respondedApplicantService.createResponse(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/responses/{id}")
    public ResponseEntity<RespondedApplicantDto> getResponseById(@PathVariable Integer id) {
        return ResponseEntity.ok(respondedApplicantService.getResponseById(id));
    }

    @GetMapping("/vacancies/{vacancyId}/responses")
    public ResponseEntity<List<RespondedApplicantDto>> getResponsesByVacancy(@PathVariable Integer vacancyId,
                                                                             @RequestParam Integer employerId) {

        return ResponseEntity.ok(respondedApplicantService.getResponsesByVacancy(vacancyId, employerId));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
}