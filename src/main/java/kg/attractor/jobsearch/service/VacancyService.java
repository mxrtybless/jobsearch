package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.model.Vacancy;

import java.util.List;

public interface VacancyService {
    Integer createVacancy(Vacancy vacancy);

    void editVacancy(
            Integer id,
            Vacancy vacancy
    );

    void deleteVacancy(Integer id);

    Vacancy findById(Integer id);

    List<Vacancy> findAll();

    List<Vacancy> findAllActive();

    List<Vacancy> findByCategoryId(
            Integer categoryId
    );

    List<Vacancy> findByAuthorId(
            Integer authorId
    );

    List<Vacancy> findRespondedByApplicantId(
            Integer applicantId
    );
}