package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.VacancyCreateDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.dto.VacancyUpdateDto;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyService {
    private final VacancyRepository vacancyRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public List<VacancyDto> getActiveVacancies() {
        return vacancyRepository.findAllActive()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<VacancyDto> getVacanciesByEmployer(
            Integer employerId
    ) {
        User employer =
                userService.getUserModelById(employerId);

        if (!employer.isEmployer()) {
            throw new IllegalArgumentException(
                    "Only employer can have vacancies."
            );
        }

        return vacancyRepository.findByAuthorId(employerId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<VacancyDto> getVacanciesRespondedByApplicant(
            Integer applicantId
    ) {
        User applicant =
                userService.getUserModelById(applicantId);

        if (!applicant.isApplicant()) {
            throw new IllegalArgumentException(
                    "Only applicant can respond to vacancies."
            );
        }

        return vacancyRepository
                .findRespondedByApplicantId(applicantId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public VacancyDto getVacancyById(Integer id) {
        Vacancy vacancy = getVacancyModelById(id);

        return toDto(vacancy);
    }

    public List<VacancyDto> searchActiveVacanciesByName(
            String query
    ) {
        String normalizedQuery =
                query == null ? "" : query.trim();

        return vacancyRepository
                .findActiveByName(normalizedQuery)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<VacancyDto> getActiveVacanciesByCategory(
            Integer categoryId
    ) {
        categoryService.getCategoryModelById(categoryId);

        return vacancyRepository
                .findActiveByCategoryId(categoryId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public VacancyDto createVacancy(
            VacancyCreateDto dto
    ) {
        validateCreateDto(dto);

        User employer =
                userService.getUserModelById(
                        dto.getAuthorId()
                );

        if (!employer.isEmployer()) {
            throw new IllegalArgumentException(
                    "Only employer can create vacancies."
            );
        }

        categoryService.getCategoryModelById(
                dto.getCategoryId()
        );

        LocalDateTime now = LocalDateTime.now();

        Vacancy vacancy = Vacancy.builder()
                .name(dto.getName().trim())
                .description(
                        dto.getDescription().trim()
                )
                .categoryId(dto.getCategoryId())
                .salary(dto.getSalary())
                .expFrom(dto.getExpFrom())
                .expTo(dto.getExpTo())
                .isActive(true)
                .authorId(dto.getAuthorId())
                .createdDate(now)
                .updateTime(now)
                .build();

        Vacancy savedVacancy =
                vacancyRepository.save(vacancy);

        return toDto(savedVacancy);
    }

    public VacancyDto updateVacancy(
            Integer vacancyId,
            VacancyUpdateDto dto
    ) {
        validateUpdateDto(dto);

        Vacancy vacancy =
                getVacancyModelById(vacancyId);

        if (!vacancy.getAuthorId()
                .equals(dto.getAuthorId())) {

            throw new IllegalArgumentException(
                    "Only vacancy author can edit this vacancy."
            );
        }

        categoryService.getCategoryModelById(
                dto.getCategoryId()
        );

        vacancy.setName(dto.getName().trim());
        vacancy.setDescription(
                dto.getDescription().trim()
        );
        vacancy.setCategoryId(dto.getCategoryId());
        vacancy.setSalary(dto.getSalary());
        vacancy.setExpFrom(dto.getExpFrom());
        vacancy.setExpTo(dto.getExpTo());
        vacancy.setIsActive(dto.getIsActive());
        vacancy.setUpdateTime(LocalDateTime.now());

        Vacancy savedVacancy =
                vacancyRepository.save(vacancy);

        return toDto(savedVacancy);
    }

    public void deleteVacancy(
            Integer vacancyId,
            Integer authorId
    ) {
        if (authorId == null) {
            throw new IllegalArgumentException(
                    "Author id is required."
            );
        }

        Vacancy vacancy =
                getVacancyModelById(vacancyId);

        if (!vacancy.getAuthorId().equals(authorId)) {
            throw new IllegalArgumentException(
                    "Only vacancy author can delete this vacancy."
            );
        }

        vacancyRepository.deleteById(vacancyId);
    }

    public Vacancy getVacancyModelById(Integer id) {
        return vacancyRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Vacancy not found."
                        )
                );
    }

    private void validateCreateDto(
            VacancyCreateDto dto
    ) {
        if (dto == null) {
            throw new IllegalArgumentException(
                    "Request body is required."
            );
        }

        if (isBlank(dto.getName())) {
            throw new IllegalArgumentException(
                    "Vacancy name is required."
            );
        }

        if (isBlank(dto.getDescription())) {
            throw new IllegalArgumentException(
                    "Description is required."
            );
        }

        if (dto.getCategoryId() == null) {
            throw new IllegalArgumentException(
                    "Category is required."
            );
        }

        if (dto.getSalary() == null) {
            throw new IllegalArgumentException(
                    "Salary is required."
            );
        }

        if (dto.getExpFrom() == null
                || dto.getExpFrom() < 0) {

            throw new IllegalArgumentException(
                    "Experience from must be zero or greater."
            );
        }

        if (dto.getExpTo() == null
                || dto.getExpTo() < dto.getExpFrom()) {

            throw new IllegalArgumentException(
                    "Experience to must be greater than experience from."
            );
        }

        if (dto.getAuthorId() == null) {
            throw new IllegalArgumentException(
                    "Author id is required."
            );
        }
    }

    private void validateUpdateDto(
            VacancyUpdateDto dto
    ) {
        if (dto == null) {
            throw new IllegalArgumentException(
                    "Request body is required."
            );
        }

        if (isBlank(dto.getName())) {
            throw new IllegalArgumentException(
                    "Vacancy name is required."
            );
        }

        if (isBlank(dto.getDescription())) {
            throw new IllegalArgumentException(
                    "Description is required."
            );
        }

        if (dto.getCategoryId() == null) {
            throw new IllegalArgumentException(
                    "Category is required."
            );
        }

        if (dto.getSalary() == null) {
            throw new IllegalArgumentException(
                    "Salary is required."
            );
        }

        if (dto.getExpFrom() == null
                || dto.getExpFrom() < 0) {

            throw new IllegalArgumentException(
                    "Experience from must be zero or greater."
            );
        }

        if (dto.getExpTo() == null
                || dto.getExpTo() < dto.getExpFrom()) {

            throw new IllegalArgumentException(
                    "Experience to must be greater than experience from."
            );
        }

        if (dto.getIsActive() == null) {
            throw new IllegalArgumentException(
                    "Activity status is required."
            );
        }

        if (dto.getAuthorId() == null) {
            throw new IllegalArgumentException(
                    "Author id is required."
            );
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private VacancyDto toDto(Vacancy vacancy) {
        return VacancyDto.builder()
                .id(vacancy.getId())
                .name(vacancy.getName())
                .description(vacancy.getDescription())
                .categoryId(vacancy.getCategoryId())
                .categoryName(
                        categoryService.getCategoryName(
                                vacancy.getCategoryId()
                        )
                )
                .salary(vacancy.getSalary())
                .expFrom(vacancy.getExpFrom())
                .expTo(vacancy.getExpTo())
                .isActive(vacancy.getIsActive())
                .authorId(vacancy.getAuthorId())
                .createdDate(vacancy.getCreatedDate())
                .updateTime(vacancy.getUpdateTime())
                .build();
    }
}