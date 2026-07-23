package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.RespondedApplicantDao;
import kg.attractor.jobsearch.model.RespondedApplicant;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.RespondedApplicantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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
        log.info(
                "Creating response: resume id {}, vacancy id {}",
                respondedApplicant.getResumeId(),
                respondedApplicant.getVacancyId()
        );

        boolean responseExists =
                respondedApplicantDao.exists(
                        respondedApplicant
                                .getResumeId(),
                        respondedApplicant
                                .getVacancyId()
                );

        if (responseExists) {
            log.warn(
                    "Response already exists: resume id {}, vacancy id {}",
                    respondedApplicant
                            .getResumeId(),
                    respondedApplicant
                            .getVacancyId()
            );

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

        log.info(
                "Response created successfully: resume id {}, vacancy id {}",
                respondedApplicant.getResumeId(),
                respondedApplicant.getVacancyId()
        );
    }

    @Override
    public List<User>
    findApplicantsByVacancyId(
            Integer vacancyId
    ) {
        log.debug(
                "Searching applicants for vacancy id: {}",
                vacancyId
        );

        return respondedApplicantDao
                .findApplicantsByVacancyId(
                        vacancyId
                );
    }
}