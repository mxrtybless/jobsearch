package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ContactInfoDto;
import kg.attractor.jobsearch.dto.EducationInfoDto;
import kg.attractor.jobsearch.dto.ResumeCreateDto;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.ResumeUpdateDto;
import kg.attractor.jobsearch.dto.WorkExperienceInfoDto;
import kg.attractor.jobsearch.model.ContactInfo;
import kg.attractor.jobsearch.model.EducationInfo;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.WorkExperienceInfo;
import kg.attractor.jobsearch.repository.ContactInfoRepository;
import kg.attractor.jobsearch.repository.EducationInfoRepository;
import kg.attractor.jobsearch.repository.ResumeRepository;
import kg.attractor.jobsearch.repository.WorkExperienceInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final ContactInfoRepository contactInfoRepository;
    private final EducationInfoRepository educationInfoRepository;
    private final WorkExperienceInfoRepository workExperienceInfoRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public List<ResumeDto> getActiveResumes() {
        return resumeRepository.findAll()
                .stream()
                .filter(Resume::isPublished)
                .sorted(Comparator.comparing(Resume::getUpdateTime).reversed())
                .map(this::toDto)
                .toList();
    }

    public List<ResumeDto> getResumesByApplicant(Integer applicantId) {
        User applicant = userService.getUserModelById(applicantId);

        if (!applicant.isApplicant()) {
            throw new IllegalArgumentException("Only applicant can have resumes.");
        }

        return resumeRepository.findAll()
                .stream()
                .filter(resume -> resume.getApplicantId().equals(applicantId))
                .sorted(Comparator.comparing(Resume::getUpdateTime).reversed())
                .map(this::toDto)
                .toList();
    }

    public List<ResumeDto> getActiveResumesByCategory(Integer categoryId) {
        categoryService.getCategoryModelById(categoryId);

        return resumeRepository.findAll()
                .stream()
                .filter(Resume::isPublished)
                .filter(resume -> resume.getCategoryId().equals(categoryId))
                .sorted(Comparator.comparing(Resume::getUpdateTime).reversed())
                .map(this::toDto)
                .toList();
    }

    public ResumeDto getResumeById(Integer id) {
        Resume resume = getResumeModelById(id);
        return toDto(resume);
    }

    public ResumeDto createResume(ResumeCreateDto dto) {
        validateCreateDto(dto);

        User applicant = userService.getUserModelById(dto.getApplicantId());

        if (!applicant.isApplicant()) {
            throw new IllegalArgumentException("Only applicant can create resumes.");
        }

        categoryService.getCategoryModelById(dto.getCategoryId());

        LocalDateTime now = LocalDateTime.now();

        Resume resume = Resume.builder()
                .applicantId(dto.getApplicantId())
                .name(dto.getName().trim())
                .categoryId(dto.getCategoryId())
                .salary(dto.getSalary())
                .isActive(true)
                .createdDate(now)
                .updateTime(now)
                .build();

        Resume savedResume = resumeRepository.save(resume);

        saveContacts(savedResume.getId(), dto.getContacts());
        saveEducations(savedResume.getId(), dto.getEducations());
        saveWorkExperiences(savedResume.getId(), dto.getWorkExperiences());

        return toDto(savedResume);
    }

    public ResumeDto updateResume(Integer resumeId, ResumeUpdateDto dto) {
        validateUpdateDto(dto);

        Resume resume = getResumeModelById(resumeId);

        if (!resume.getApplicantId().equals(dto.getApplicantId())) {
            throw new IllegalArgumentException("Only resume owner can edit this resume.");
        }

        categoryService.getCategoryModelById(dto.getCategoryId());

        resume.setName(dto.getName().trim());
        resume.setCategoryId(dto.getCategoryId());
        resume.setSalary(dto.getSalary());
        resume.setIsActive(dto.getIsActive());
        resume.setUpdateTime(LocalDateTime.now());

        Resume savedResume = resumeRepository.save(resume);

        contactInfoRepository.deleteByResumeId(resumeId);
        educationInfoRepository.deleteByResumeId(resumeId);
        workExperienceInfoRepository.deleteByResumeId(resumeId);

        saveContacts(resumeId, dto.getContacts());
        saveEducations(resumeId, dto.getEducations());
        saveWorkExperiences(resumeId, dto.getWorkExperiences());

        return toDto(savedResume);
    }

    public void deleteResume(Integer resumeId, Integer applicantId) {
        if (applicantId == null) {
            throw new IllegalArgumentException("Applicant id is required.");
        }

        Resume resume = getResumeModelById(resumeId);

        if (!resume.getApplicantId().equals(applicantId)) {
            throw new IllegalArgumentException("Only resume owner can delete this resume.");
        }

        contactInfoRepository.deleteByResumeId(resumeId);
        educationInfoRepository.deleteByResumeId(resumeId);
        workExperienceInfoRepository.deleteByResumeId(resumeId);
        resumeRepository.deleteById(resumeId);
    }

    public Resume getResumeModelById(Integer id) {
        return resumeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resume not found."));
    }

    private void saveContacts(Integer resumeId, List<ContactInfoDto> contacts) {
        if (contacts == null) {
            return;
        }

        List<ContactInfo> contactInfos = contacts.stream()
                .map(contact -> ContactInfo.builder()
                        .resumeId(resumeId)
                        .typeId(contact.getTypeId())
                        .value(contact.getValue())
                        .build())
                .toList();

        contactInfoRepository.saveAll(contactInfos);
    }

    private void saveEducations(Integer resumeId, List<EducationInfoDto> educations) {
        if (educations == null) {
            return;
        }

        List<EducationInfo> educationInfos = educations.stream()
                .map(education -> EducationInfo.builder()
                        .resumeId(resumeId)
                        .institution(education.getInstitution())
                        .program(education.getProgram())
                        .startDate(education.getStartDate())
                        .endDate(education.getEndDate())
                        .degree(education.getDegree())
                        .build())
                .toList();

        educationInfoRepository.saveAll(educationInfos);
    }

    private void saveWorkExperiences(Integer resumeId, List<WorkExperienceInfoDto> workExperiences) {
        if (workExperiences == null) {
            return;
        }

        List<WorkExperienceInfo> workExperienceInfos = workExperiences.stream()
                .map(workExperience -> WorkExperienceInfo.builder()
                        .resumeId(resumeId)
                        .years(workExperience.getYears())
                        .companyName(workExperience.getCompanyName())
                        .position(workExperience.getPosition())
                        .responsibilities(workExperience.getResponsibilities())
                        .build())
                .toList();

        workExperienceInfoRepository.saveAll(workExperienceInfos);
    }

    private ResumeDto toDto(Resume resume) {
        return ResumeDto.builder()
                .id(resume.getId())
                .applicantId(resume.getApplicantId())
                .name(resume.getName())
                .categoryId(resume.getCategoryId())
                .categoryName(categoryService.getCategoryName(resume.getCategoryId()))
                .salary(resume.getSalary())
                .isActive(resume.getIsActive())
                .createdDate(resume.getCreatedDate())
                .updateTime(resume.getUpdateTime())
                .contacts(getContactDtos(resume.getId()))
                .educations(getEducationDtos(resume.getId()))
                .workExperiences(getWorkExperienceDtos(resume.getId()))
                .build();
    }

    private List<ContactInfoDto> getContactDtos(Integer resumeId) {
        return contactInfoRepository.findByResumeId(resumeId)
                .stream()
                .map(contact -> ContactInfoDto.builder()
                        .id(contact.getId())
                        .resumeId(contact.getResumeId())
                        .typeId(contact.getTypeId())
                        .value(contact.getValue())
                        .build())
                .toList();
    }

    private List<EducationInfoDto> getEducationDtos(Integer resumeId) {
        return educationInfoRepository.findByResumeId(resumeId)
                .stream()
                .map(education -> EducationInfoDto.builder()
                        .id(education.getId())
                        .resumeId(education.getResumeId())
                        .institution(education.getInstitution())
                        .program(education.getProgram())
                        .startDate(education.getStartDate())
                        .endDate(education.getEndDate())
                        .degree(education.getDegree())
                        .build())
                .toList();
    }

    private List<WorkExperienceInfoDto> getWorkExperienceDtos(Integer resumeId) {
        return workExperienceInfoRepository.findByResumeId(resumeId)
                .stream()
                .map(workExperience -> WorkExperienceInfoDto.builder()
                        .id(workExperience.getId())
                        .resumeId(workExperience.getResumeId())
                        .years(workExperience.getYears())
                        .companyName(workExperience.getCompanyName())
                        .position(workExperience.getPosition())
                        .responsibilities(workExperience.getResponsibilities())
                        .build())
                .toList();
    }

    private void validateCreateDto(ResumeCreateDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Request body is required.");
        }

        if (dto.getApplicantId() == null) {
            throw new IllegalArgumentException("Applicant id is required.");
        }

        if (isBlank(dto.getName())) {
            throw new IllegalArgumentException("Resume name is required.");
        }

        if (dto.getCategoryId() == null) {
            throw new IllegalArgumentException("Category is required.");
        }

        if (dto.getSalary() == null) {
            throw new IllegalArgumentException("Salary is required.");
        }
    }

    private void validateUpdateDto(ResumeUpdateDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Request body is required.");
        }

        if (dto.getApplicantId() == null) {
            throw new IllegalArgumentException("Applicant id is required.");
        }

        if (isBlank(dto.getName())) {
            throw new IllegalArgumentException("Resume name is required.");
        }

        if (dto.getCategoryId() == null) {
            throw new IllegalArgumentException("Category is required.");
        }

        if (dto.getSalary() == null) {
            throw new IllegalArgumentException("Salary is required.");
        }

        if (dto.getIsActive() == null) {
            throw new IllegalArgumentException("Activity status is required.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}