package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.model.Vacancy;

import java.util.List;

public interface VacancyService {

    Integer createVacancy(
            Vacancy vacancy,
            String userEmail
    );

    void editVacancy(
            Integer id,
            Vacancy vacancy,
            String userEmail
    );

    void deleteVacancy(
            Integer id,
            String userEmail
    );

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