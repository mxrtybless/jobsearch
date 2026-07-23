package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.service.ResumeService;
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
@RequestMapping("resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @PostMapping("create")
    public ResponseEntity<Integer> createResume(
            @RequestBody ResumeDto resumeDto
    ) {
        Integer resumeId =
                resumeService.createResume(
                        resumeDto
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resumeId);
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<Void> editResume(
            @PathVariable Integer id,
            @RequestBody ResumeDto resumeDto
    ) {
        resumeService.editResume(
                id,
                resumeDto
        );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteResume(
            @PathVariable Integer id
    ) {
        resumeService.deleteResume(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("search/id/{id}")
    public ResponseEntity<ResumeDto>
    findResumeById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(
                resumeService.findById(id)
        );
    }

    @GetMapping("search/all")
    public ResponseEntity<List<ResumeDto>>
    searchAllResumes() {
        return ResponseEntity.ok(
                resumeService.findAllActive()
        );
    }

    @GetMapping(
            "search/category/{categoryId}"
    )
    public ResponseEntity<List<ResumeDto>>
    searchResumesByCategory(
            @PathVariable Integer categoryId
    ) {
        return ResponseEntity.ok(
                resumeService.findByCategoryId(
                        categoryId
                )
        );
    }

    @GetMapping(
            "search/applicant/{applicantId}"
    )
    public ResponseEntity<List<ResumeDto>>
    searchResumesByApplicant(
            @PathVariable Integer applicantId
    ) {
        return ResponseEntity.ok(
                resumeService.findByApplicantId(
                        applicantId
                )
        );
    }
}