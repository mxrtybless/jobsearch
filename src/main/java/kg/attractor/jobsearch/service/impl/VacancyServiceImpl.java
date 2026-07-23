package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacancyServiceImpl
        implements VacancyService {

    private final VacancyDao vacancyDao;

    @Override
    public Integer createVacancy(
            Vacancy vacancy
    ) {
        log.info(
                "Creating vacancy '{}' for employer id: {}",
                vacancy.getName(),
                vacancy.getAuthorId()
        );

        LocalDateTime now =
                LocalDateTime.now();

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
            Vacancy vacancy
    ) {
        log.info(
                "Editing vacancy with id: {}",
                id
        );

        Vacancy savedVacancy =
                vacancyDao.findById(id)
                        .orElseThrow();

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
    public void deleteVacancy(Integer id) {
        log.warn(
                "Deleting vacancy with id: {}",
                id
        );

        vacancyDao.findById(id)
                .orElseThrow();

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
                .orElseThrow();
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

        return vacancyDao
                .findRespondedByApplicantId(
                        applicantId
                );
    }
}