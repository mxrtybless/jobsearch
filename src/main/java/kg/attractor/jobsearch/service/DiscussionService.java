package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.model.*;
import org.springframework.data.domain.Page;
import java.util.List;

public interface DiscussionService {
    Integer open(Integer resumeId, Integer vacancyId, String email);
    RespondedApplicant get(Integer id, String email);
    List<RespondedApplicant> list(String email, Integer vacancyId);
    Page<Resume> resumes(Integer categoryId, int page, String sort);
    Resume resume(Integer id, String email);
    List<Resume> ownResumes(String email);
    List<Vacancy> ownVacancies(String email);
    Page<Resume> applicantResumes(Integer applicantId, int page);
}
