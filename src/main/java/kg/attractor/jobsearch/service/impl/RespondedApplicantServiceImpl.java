package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.RespondedApplicantDao;
import kg.attractor.jobsearch.dao.ResumeDao;
import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.exception.ResponseAlreadyExistsException;
import kg.attractor.jobsearch.exception.ResumeNotFoundException;
import kg.attractor.jobsearch.exception.VacancyNotFoundException;
import kg.attractor.jobsearch.model.RespondedApplicant;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.Vacancy;
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

    private final ResumeDao resumeDao;
    private final VacancyDao vacancyDao;

    @Override
    public void createResponse(
            RespondedApplicant respondedApplicant
    ) {
        Integer resumeId =
                respondedApplicant.getResumeId();

        Integer vacancyId =
                respondedApplicant.getVacancyId();

        log.info(
                "Creating response: resume id {}, vacancy id {}",
                resumeId,
                vacancyId
        );

        Resume resume =
                resumeDao.findById(resumeId)
                        .orElseThrow(() ->
                                new ResumeNotFoundException(
                                        resumeId
                                )
                        );

        Vacancy vacancy =
                vacancyDao.findById(vacancyId)
                        .orElseThrow(() ->
                                new VacancyNotFoundException(
                                        vacancyId
                                )
                        );

        if (!resume.isPublished()) {
            throw new IllegalArgumentException(
                    "Cannot respond with an inactive resume"
            );
        }

        if (!vacancy.isPublished()) {
            throw new IllegalArgumentException(
                    "Cannot respond to an inactive vacancy"
            );
        }

        boolean responseExists =
                respondedApplicantDao.exists(
                        resumeId,
                        vacancyId
                );

        if (responseExists) {
            log.warn(
                    "Response already exists: resume id {}, vacancy id {}",
                    resumeId,
                    vacancyId
            );

            throw new ResponseAlreadyExistsException(
                    resumeId,
                    vacancyId
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
                resumeId,
                vacancyId
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

        vacancyDao.findById(vacancyId)
                .orElseThrow(() ->
                        new VacancyNotFoundException(
                                vacancyId
                        )
                );

        return respondedApplicantDao
                .findApplicantsByVacancyId(
                        vacancyId
                );
    }
}