package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.VacancyDto;

import java.util.List;

public interface VacancyService {

    Integer createVacancy(
            VacancyDto vacancyDto,
            String userEmail
    );

    void editVacancy(
            Integer id,
            VacancyDto vacancyDto,
            String userEmail
    );

    void deleteVacancy(
            Integer id,
            String userEmail
    );

    VacancyDto findById(Integer id);

    List<VacancyDto> findAll();

    List<VacancyDto> findAllActive();

    List<VacancyDto> findByCategoryId(
            Integer categoryId
    );

    List<VacancyDto> findByAuthorId(
            Integer authorId
    );

    List<VacancyDto> findRespondedByApplicantId(
            Integer applicantId
    );
}