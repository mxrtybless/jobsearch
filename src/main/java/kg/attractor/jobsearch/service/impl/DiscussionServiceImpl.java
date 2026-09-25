package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.model.*;
import kg.attractor.jobsearch.repository.*;
import kg.attractor.jobsearch.service.DiscussionService;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DiscussionServiceImpl implements DiscussionService {
    private final RespondedApplicantRepository responseRepository;
    private final ResumeRepository resumeRepository;
    private final VacancyRepository vacancyRepository;
    private final UserService userService;

    @Override
    @Transactional
    public Integer open(Integer resumeId, Integer vacancyId, String email) {
        User user = userService.findByEmail(email).orElseThrow();
        Resume resume = resumeRepository.findById(resumeId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        boolean applicant = user.isApplicant() && resume.getApplicant().getId().equals(user.getId());
        boolean employer = user.isEmployer() && vacancy.getAuthor().getId().equals(user.getId());
        if (!applicant && !employer) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        var existing = responseRepository.findByResume_IdAndVacancy_Id(resumeId, vacancyId);
        if (existing.isPresent()) return existing.get().getId();
        if (!Boolean.TRUE.equals(resume.getIsActive()) || !Boolean.TRUE.equals(vacancy.getIsActive())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        RespondedApplicant response = new RespondedApplicant();
        response.setResume(resume);
        response.setVacancy(vacancy);
        response.setConfirmation(false);
        Integer id = responseRepository.save(response).getId();
        log.info("User {} opened discussion {}", user.getId(), id);
        return id;
    }

    @Override
    public RespondedApplicant get(Integer id, String email) {
        User user = userService.findByEmail(email).orElseThrow();
        RespondedApplicant response = responseRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!response.getResume().getApplicant().getId().equals(user.getId())
                && !response.getVacancy().getAuthor().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return response;
    }

    @Override
    public List<RespondedApplicant> list(String email, Integer vacancyId) {
        return responseRepository.findDiscussions(userService.findByEmail(email).orElseThrow().getId(), vacancyId);
    }

    @Override
    public Page<Resume> resumes(Integer categoryId, int page, String sort) {
        int index = Math.max(1, page) - 1;
        if ("responsesAsc".equals(sort)) {
            return resumeRepository.findFilteredByResponsesAsc(categoryId, PageRequest.of(index, 20));
        }
        if ("responsesDesc".equals(sort)) {
            return resumeRepository.findFilteredByResponsesDesc(categoryId, PageRequest.of(index, 20));
        }
        Sort ordering = "dateAsc".equals(sort) ? Sort.by("updateTime").ascending() : Sort.by("updateTime").descending();
        Pageable pageable = PageRequest.of(index, 20, ordering.and(Sort.by("id").descending()));
        return categoryId == null ? resumeRepository.findAllByIsActiveTrue(pageable)
                : resumeRepository.findByIsActiveTrueAndCategory_Id(categoryId, pageable);
    }

    @Override
    public Resume resume(Integer id, String email) {
        User user = userService.findByEmail(email).orElseThrow();
        Resume resume = resumeRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (resume.getApplicant().getId().equals(user.getId())) return resume;
        if (!user.isEmployer()) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        boolean participant = responseRepository.findDiscussions(user.getId(), null).stream()
                .anyMatch(r -> r.getResume().getId().equals(id));
        if (!Boolean.TRUE.equals(resume.getIsActive()) && !participant) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return resume;
    }

    @Override
    public List<Resume> ownResumes(String email) {
        User user = userService.findByEmail(email).orElseThrow();
        return user.isApplicant() ? resumeRepository.findByApplicant_IdAndIsActiveTrueOrderByUpdateTimeDesc(user.getId()) : List.of();
    }

    @Override
    public List<Vacancy> ownVacancies(String email) {
        User user = userService.findByEmail(email).orElseThrow();
        return user.isEmployer() ? vacancyRepository.findByAuthor_IdAndIsActiveTrueOrderByUpdateTimeDesc(user.getId()) : List.of();
    }

    @Override
    public Page<Resume> applicantResumes(Integer applicantId, int page) {
        return resumeRepository.findByApplicant_IdAndIsActiveTrue(applicantId,
                PageRequest.of(Math.max(1, page) - 1, 20, Sort.by("updateTime").descending()));
    }
}
