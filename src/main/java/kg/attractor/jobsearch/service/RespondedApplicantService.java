package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.RespondedApplicantDto;
import kg.attractor.jobsearch.model.User;

import java.util.List;

public interface RespondedApplicantService {

    void createResponse(
            RespondedApplicantDto respondedApplicantDto,
            String userEmail
    );

    List<User> findApplicantsByVacancyId(
            Integer vacancyId,
            String userEmail
    );
}