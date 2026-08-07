package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.exception.InvalidAccountTypeException;
import kg.attractor.jobsearch.exception.InvalidExperienceRangeException;
import kg.attractor.jobsearch.exception.VacancyNotFoundException;
import kg.attractor.jobsearch.model.AccountType;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.CategoryService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacancyServiceImpl
        implements VacancyService {

    private final VacancyDao vacancyDao;
    private final UserService userService;
    private final CategoryService categoryService;

    @Override
    @Transactional
    public Integer createVacancy(
            VacancyDto vacancyDto,
            String userEmail
    ) {
        User employer =
                findAuthenticatedEmployer(
                        userEmail
                );

        categoryService.findById(
                vacancyDto.getCategoryId()
        );

        validateExperienceRange(
                vacancyDto
        );

        LocalDateTime now =
                LocalDateTime.now();

        Vacancy vacancy =
                Vacancy.builder()
                        .name(
                                vacancyDto.getName()
                        )
                        .description(
                                vacancyDto
                                        .getDescription()
                        )
                        .categoryId(
                                vacancyDto
                                        .getCategoryId()
                        )
                        .salary(
                                vacancyDto.getSalary()
                        )
                        .expFrom(
                                vacancyDto.getExpFrom()
                        )
                        .expTo(
                                vacancyDto.getExpTo()
                        )
                        .isActive(
                                vacancyDto
                                        .getIsActive()
                        )
                        .authorId(
                                employer.getId()
                        )
                        .createdDate(now)
                        .updateTime(now)
                        .build();

        Integer vacancyId =
                vacancyDao.save(vacancy);

        log.info(
                "Vacancy created successfully with id: {}",
                vacancyId
        );

        return vacancyId;
    }

    @Override
    @Transactional
    public void editVacancy(
            Integer id,
            VacancyDto vacancyDto,
            String userEmail
    ) {
        User employer =
                findAuthenticatedEmployer(
                        userEmail
                );

        Vacancy savedVacancy =
                vacancyDao.findById(id)
                        .orElseThrow(() ->
                                new VacancyNotFoundException(
                                        id
                                )
                        );

        validateVacancyOwner(
                savedVacancy,
                employer
        );

        categoryService.findById(
                vacancyDto.getCategoryId()
        );

        validateExperienceRange(
                vacancyDto
        );

        savedVacancy.setName(
                vacancyDto.getName()
        );
        savedVacancy.setDescription(
                vacancyDto.getDescription()
        );
        savedVacancy.setCategoryId(
                vacancyDto.getCategoryId()
        );
        savedVacancy.setSalary(
                vacancyDto.getSalary()
        );
        savedVacancy.setExpFrom(
                vacancyDto.getExpFrom()
        );
        savedVacancy.setExpTo(
                vacancyDto.getExpTo()
        );
        savedVacancy.setIsActive(
                vacancyDto.getIsActive()
        );
        savedVacancy.setUpdateTime(
                LocalDateTime.now()
        );

        vacancyDao.update(savedVacancy);

        log.info(
                "Vacancy updated successfully with id: {}",
                id
        );
    }

    @Override
    @Transactional
    public void deleteVacancy(
            Integer id,
            String userEmail
    ) {
        User employer =
                findAuthenticatedEmployer(
                        userEmail
                );

        Vacancy savedVacancy =
                vacancyDao.findById(id)
                        .orElseThrow(() ->
                                new VacancyNotFoundException(
                                        id
                                )
                        );

        validateVacancyOwner(
                savedVacancy,
                employer
        );

        vacancyDao.deleteById(id);

        log.info(
                "Vacancy deleted successfully with id: {}",
                id
        );
    }

    @Override
    public VacancyDto findById(Integer id) {
        return convertToDto(
                vacancyDao.findById(id)
                        .orElseThrow(() ->
                                new VacancyNotFoundException(
                                        id
                                )
                        )
        );
    }

    @Override
    public List<VacancyDto> findAll() {
        return vacancyDao.findAllActive()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<VacancyDto> findAllActive() {
        return vacancyDao.findAllActive()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<VacancyDto> findByCategoryId(
            Integer categoryId
    ) {
        categoryService.findById(categoryId);

        return vacancyDao
                .findByCategoryId(categoryId)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<VacancyDto> findByAuthorId(
            Integer authorId
    ) {
        validateEmployerById(authorId);

        return vacancyDao.findByAuthorId(
                        authorId
                )
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<VacancyDto>
    findRespondedByApplicantId(
            Integer applicantId
    ) {
        validateApplicantById(
                applicantId
        );

        return vacancyDao
                .findRespondedByApplicantId(
                        applicantId
                )
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    private VacancyDto convertToDto(
            Vacancy vacancy
    ) {
        return new VacancyDto(
                vacancy.getId(),
                vacancy.getName(),
                vacancy.getDescription(),
                vacancy.getCategoryId(),
                vacancy.getSalary(),
                vacancy.getExpFrom(),
                vacancy.getExpTo(),
                vacancy.getIsActive(),
                vacancy.getAuthorId(),
                vacancy.getCreatedDate(),
                vacancy.getUpdateTime()
        );
    }

    private User findAuthenticatedEmployer(
            String userEmail
    ) {
        User user =
                userService.findByEmail(
                                userEmail
                        )
                        .orElseThrow(() ->
                                new NoSuchElementException(
                                        "User with email "
                                                + userEmail
                                                + " not found"
                                )
                        );

        if (user.getAccountType()
                != AccountType.EMPLOYER) {
            throw new InvalidAccountTypeException(
                    user.getId(),
                    AccountType.EMPLOYER
            );
        }

        return user;
    }

    private void validateEmployerById(
            Integer employerId
    ) {
        User employer =
                userService.findProfileById(
                        employerId
                );

        if (employer.getAccountType()
                != AccountType.EMPLOYER) {
            throw new InvalidAccountTypeException(
                    employerId,
                    AccountType.EMPLOYER
            );
        }
    }

    private void validateApplicantById(
            Integer applicantId
    ) {
        User applicant =
                userService.findProfileById(
                        applicantId
                );

        if (applicant.getAccountType()
                != AccountType.APPLICANT) {
            throw new InvalidAccountTypeException(
                    applicantId,
                    AccountType.APPLICANT
            );
        }
    }

    private void validateVacancyOwner(
            Vacancy vacancy,
            User employer
    ) {
        if (!vacancy.getAuthorId()
                .equals(employer.getId())) {
            throw new IllegalArgumentException(
                    "You can only change your own vacancy"
            );
        }
    }

    private void validateExperienceRange(
            VacancyDto vacancyDto
    ) {
        if (vacancyDto.getExpFrom() != null
                && vacancyDto.getExpTo() != null
                && vacancyDto.getExpFrom()
                > vacancyDto.getExpTo()) {
            throw new InvalidExperienceRangeException();
        }
    }
}