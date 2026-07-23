package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.model.RespondedApplicant;
import kg.attractor.jobsearch.model.User;

import java.util.List;

public interface RespondedApplicantService {
    void createResponse(
            RespondedApplicant respondedApplicant
    );

    List<User> findApplicantsByVacancyId(
            Integer vacancyId
    );
}