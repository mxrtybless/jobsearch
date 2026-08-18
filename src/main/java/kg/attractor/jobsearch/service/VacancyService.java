package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.VacancyDto;
import org.springframework.data.domain.Page;

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

    void updateVacancyDate(
            Integer id,
            String userEmail
    );

    void deleteVacancy(
            Integer id,
            String userEmail
    );

    VacancyDto findById(Integer id);

    VacancyDto findOwnedById(
            Integer id,
            String userEmail
    );

    List<VacancyDto> findAll();

    List<VacancyDto> findAllActive();

    Page<VacancyDto> findAllActive(int page, int size, String sort);

    Page<VacancyDto> findByAuthorId(Integer authorId, int page, int size, String sort);

    Page<VacancyDto> findActiveByAuthorId(Integer authorId, int page, int size, String sort);

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