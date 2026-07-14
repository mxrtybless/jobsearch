package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.ResumeCreateDto;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.ResumeUpdateDto;
import kg.attractor.jobsearch.service.ResumeService;
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
public class ResumeController {
    private final ResumeService resumeService;

    @GetMapping("/resumes")
    public ResponseEntity<List<ResumeDto>> getActiveResumes() {
        return ResponseEntity.ok(resumeService.getActiveResumes());
    }

    @GetMapping("/resumes/{id}")
    public ResponseEntity<ResumeDto> getResumeById(@PathVariable Integer id) {
        return ResponseEntity.ok(resumeService.getResumeById(id));
    }

    @GetMapping("/resumes/applicant/{applicantId}")
    public ResponseEntity<List<ResumeDto>> getResumesByApplicant(@PathVariable Integer applicantId) {
        return ResponseEntity.ok(resumeService.getResumesByApplicant(applicantId));
    }

    @GetMapping("/resumes/category/{categoryId}")
    public ResponseEntity<List<ResumeDto>> getResumesByCategory(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(resumeService.getActiveResumesByCategory(categoryId));
    }

    @PostMapping("/resumes")
    public ResponseEntity<ResumeDto> createResume(@RequestBody ResumeCreateDto dto) {
        ResumeDto resume = resumeService.createResume(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resume);
    }

    @PutMapping("/resumes/{id}")
    public ResponseEntity<ResumeDto> updateResume(@PathVariable Integer id,
                                                  @RequestBody ResumeUpdateDto dto) {

        return ResponseEntity.ok(resumeService.updateResume(id, dto));
    }

    @DeleteMapping("/resumes/{id}")
    public ResponseEntity<Void> deleteResume(@PathVariable Integer id,
                                             @RequestParam Integer applicantId) {

        resumeService.deleteResume(id, applicantId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
}