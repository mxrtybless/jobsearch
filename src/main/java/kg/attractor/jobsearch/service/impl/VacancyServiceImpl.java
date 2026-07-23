package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl
        implements VacancyService {

    private final VacancyDao vacancyDao;

    @Override
    public Integer createVacancy(
            Vacancy vacancy
    ) {
        LocalDateTime now =
                LocalDateTime.now();

        vacancy.setIsActive(true);
        vacancy.setCreatedDate(now);
        vacancy.setUpdateTime(now);

        return vacancyDao.save(vacancy);
    }

    @Override
    public void editVacancy(
            Integer id,
            Vacancy vacancy
    ) {
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
    }

    @Override
    public void deleteVacancy(Integer id) {
        vacancyDao.findById(id)
                .orElseThrow();

        vacancyDao.deleteById(id);
    }

    @Override
    public Vacancy findById(Integer id) {
        return vacancyDao.findById(id)
                .orElseThrow();
    }

    @Override
    public List<Vacancy> findAll() {
        return vacancyDao.findAll();
    }

    @Override
    public List<Vacancy> findAllActive() {
        return vacancyDao.findAllActive();
    }

    @Override
    public List<Vacancy> findByCategoryId(
            Integer categoryId
    ) {
        return vacancyDao.findByCategoryId(
                categoryId
        );
    }

    @Override
    public List<Vacancy> findByAuthorId(
            Integer authorId
    ) {
        return vacancyDao.findByAuthorId(
                authorId
        );
    }

    @Override
    public List<Vacancy>
    findRespondedByApplicantId(
            Integer applicantId
    ) {
        return vacancyDao
                .findRespondedByApplicantId(
                        applicantId
                );
    }
}