package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.exception.InvalidAccountTypeException;
import kg.attractor.jobsearch.exception.InvalidExperienceRangeException;
import kg.attractor.jobsearch.exception.VacancyNotFoundException;
import kg.attractor.jobsearch.model.AccountType;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    @Override
    public Integer createVacancy(
            Vacancy vacancy,
            String userEmail
    ) {
        User employer =
                findAuthenticatedEmployer(
                        userEmail
                );

        log.info(
                "Creating vacancy '{}' for employer id: {}",
                vacancy.getName(),
                employer.getId()
        );

        validateExperienceRange(vacancy);

        LocalDateTime now =
                LocalDateTime.now();

        vacancy.setAuthorId(
                employer.getId()
        );

        vacancy.setIsActive(true);
        vacancy.setCreatedDate(now);
        vacancy.setUpdateTime(now);

        Integer vacancyId =
                vacancyDao.save(vacancy);

        log.info(
                "Vacancy created successfully with id: {}",
                vacancyId
        );

        return vacancyId;
    }

    @Override
    public void editVacancy(
            Integer id,
            Vacancy vacancy,
            String userEmail
    ) {
        User employer =
                findAuthenticatedEmployer(
                        userEmail
                );

        log.info(
                "Editing vacancy with id: {} by employer id: {}",
                id,
                employer.getId()
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

        validateExperienceRange(vacancy);

        savedVacancy.setName(
                vacancy.getName()
        );

        savedVacancy.setDescription(
                vacancy.getDescription()
        );

        savedVacancy.setCategoryId(
                vacancy.getCategoryId()
        );

        savedVacancy.setSalary(
                vacancy.getSalary()
        );

        savedVacancy.setExpFrom(
                vacancy.getExpFrom()
        );

        savedVacancy.setExpTo(
                vacancy.getExpTo()
        );

        if (vacancy.getIsActive() != null) {
            savedVacancy.setIsActive(
                    vacancy.getIsActive()
            );
        }

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
    public void deleteVacancy(
            Integer id,
            String userEmail
    ) {
        User employer =
                findAuthenticatedEmployer(
                        userEmail
                );

        log.warn(
                "Deleting vacancy with id: {} by employer id: {}",
                id,
                employer.getId()
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
    public Vacancy findById(Integer id) {
        log.debug(
                "Searching vacancy by id: {}",
                id
        );

        return vacancyDao.findById(id)
                .orElseThrow(() ->
                        new VacancyNotFoundException(
                                id
                        )
                );
    }

    @Override
    public List<Vacancy> findAll() {
        log.debug(
                "Searching all vacancies"
        );

        return vacancyDao.findAll();
    }

    @Override
    public List<Vacancy> findAllActive() {
        log.debug(
                "Searching all active vacancies"
        );

        return vacancyDao.findAllActive();
    }

    @Override
    public List<Vacancy> findByCategoryId(
            Integer categoryId
    ) {
        log.debug(
                "Searching vacancies by category id: {}",
                categoryId
        );

        return vacancyDao.findByCategoryId(
                categoryId
        );
    }

    @Override
    public List<Vacancy> findByAuthorId(
            Integer authorId
    ) {
        log.debug(
                "Searching vacancies by employer id: {}",
                authorId
        );

        validateEmployerById(authorId);

        return vacancyDao.findByAuthorId(
                authorId
        );
    }

    @Override
    public List<Vacancy>
    findRespondedByApplicantId(
            Integer applicantId
    ) {
        log.debug(
                "Searching responded vacancies for applicant id: {}",
                applicantId
        );

        validateApplicantById(
                applicantId
        );

        return vacancyDao
                .findRespondedByApplicantId(
                        applicantId
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
            Vacancy vacancy
    ) {
        if (vacancy.getExpFrom() != null
                && vacancy.getExpTo() != null
                && vacancy.getExpFrom()
                > vacancy.getExpTo()) {
            throw new InvalidExperienceRangeException();
        }
    }
}