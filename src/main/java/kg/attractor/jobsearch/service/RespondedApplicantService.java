package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.RespondedApplicantCreateDto;
import kg.attractor.jobsearch.dto.RespondedApplicantDto;
import kg.attractor.jobsearch.model.RespondedApplicant;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.repository.RespondedApplicantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RespondedApplicantService {
    private final RespondedApplicantRepository respondedApplicantRepository;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final UserService userService;

    public RespondedApplicantDto createResponse(RespondedApplicantCreateDto dto) {
        validateCreateDto(dto);

        Resume resume = resumeService.getResumeModelById(dto.getResumeId());
        Vacancy vacancy = vacancyService.getVacancyModelById(dto.getVacancyId());

        User applicant = userService.getUserModelById(resume.getApplicantId());

        if (!applicant.isApplicant()) {
            throw new IllegalArgumentException("Only applicant can respond to vacancy.");
        }

        if (!resume.isPublished()) {
            throw new IllegalArgumentException("Resume is not active.");
        }

        if (!vacancy.isPublished()) {
            throw new IllegalArgumentException("Vacancy is not active.");
        }

        if (respondedApplicantRepository.existsByResumeIdAndVacancyId(dto.getResumeId(), dto.getVacancyId())) {
            throw new IllegalArgumentException("This resume has already responded to this vacancy.");
        }

        RespondedApplicant respondedApplicant = RespondedApplicant.builder()
                .resumeId(dto.getResumeId())
                .vacancyId(dto.getVacancyId())
                .confirmation(false)
                .build();

        RespondedApplicant savedResponse = respondedApplicantRepository.save(respondedApplicant);
        return toDto(savedResponse);
    }

    public List<RespondedApplicantDto> getResponsesByVacancy(Integer vacancyId, Integer employerId) {
        if (employerId == null) {
            throw new IllegalArgumentException("Employer id is required.");
        }

        Vacancy vacancy = vacancyService.getVacancyModelById(vacancyId);
        User employer = userService.getUserModelById(employerId);

        if (!employer.isEmployer()) {
            throw new IllegalArgumentException("Only employer can view vacancy responses.");
        }

        if (!vacancy.getAuthorId().equals(employerId)) {
            throw new IllegalArgumentException("Only vacancy author can view responses.");
        }

        return respondedApplicantRepository.findByVacancyId(vacancyId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public RespondedApplicantDto getResponseById(Integer id) {
        RespondedApplicant response = getResponseModelById(id);
        return toDto(response);
    }

    public RespondedApplicant getResponseModelById(Integer id) {
        return respondedApplicantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Response not found."));
    }

    private void validateCreateDto(RespondedApplicantCreateDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Request body is required.");
        }

        if (dto.getResumeId() == null) {
            throw new IllegalArgumentException("Resume id is required.");
        }

        if (dto.getVacancyId() == null) {
            throw new IllegalArgumentException("Vacancy id is required.");
        }
    }

    private RespondedApplicantDto toDto(RespondedApplicant response) {
        Resume resume = resumeService.getResumeModelById(response.getResumeId());
        Vacancy vacancy = vacancyService.getVacancyModelById(response.getVacancyId());

        User applicant = userService.getUserModelById(resume.getApplicantId());
        User employer = userService.getUserModelById(vacancy.getAuthorId());

        return RespondedApplicantDto.builder()
                .id(response.getId())
                .resumeId(response.getResumeId())
                .resumeName(resume.getName())
                .applicantId(applicant.getId())
                .applicantName(makeUserName(applicant))
                .vacancyId(response.getVacancyId())
                .vacancyName(vacancy.getName())
                .employerId(employer.getId())
                .employerName(makeUserName(employer))
                .confirmation(response.getConfirmation())
                .build();
    }

    private String makeUserName(User user) {
        String surname = user.getSurname() == null ? "" : " " + user.getSurname();
        return user.getName() + surname;
    }
}