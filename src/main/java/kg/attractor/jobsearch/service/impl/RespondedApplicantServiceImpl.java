package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.RespondedApplicantDao;
import kg.attractor.jobsearch.model.RespondedApplicant;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.RespondedApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RespondedApplicantServiceImpl
        implements RespondedApplicantService {

    private final RespondedApplicantDao
            respondedApplicantDao;

    @Override
    public void createResponse(
            RespondedApplicant respondedApplicant
    ) {
        boolean responseExists =
                respondedApplicantDao.exists(
                        respondedApplicant
                                .getResumeId(),
                        respondedApplicant
                                .getVacancyId()
                );

        if (responseExists) {
            throw new IllegalArgumentException(
                    "Response already exists"
            );
        }

        if (respondedApplicant
                .getConfirmation() == null) {

            respondedApplicant
                    .setConfirmation(false);
        }

        respondedApplicantDao.save(
                respondedApplicant
        );
    }

    @Override
    public List<User>
    findApplicantsByVacancyId(
            Integer vacancyId
    ) {
        return respondedApplicantDao
                .findApplicantsByVacancyId(
                        vacancyId
                );
    }
}