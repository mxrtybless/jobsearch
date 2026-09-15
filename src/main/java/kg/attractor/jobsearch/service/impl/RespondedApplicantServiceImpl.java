package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.RespondedApplicantDto;
import kg.attractor.jobsearch.exception.InvalidAccountTypeException;
import kg.attractor.jobsearch.exception.ResponseAlreadyExistsException;
import kg.attractor.jobsearch.model.AccountType;
import kg.attractor.jobsearch.model.RespondedApplicant;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.repository.RespondedApplicantRepository;
import kg.attractor.jobsearch.service.RespondedApplicantService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class RespondedApplicantServiceImpl
        implements RespondedApplicantService {

    private final RespondedApplicantRepository
            respondedApplicantRepository;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final UserService userService;

    @Override
    @Transactional
    public void createResponse(
            RespondedApplicantDto respondedApplicantDto,
            String userEmail
    ) {
        User applicant =
                findAuthenticatedUser(
                        userEmail,
                        AccountType.APPLICANT
                );

        Integer resumeId =
                respondedApplicantDto
                        .getResumeId();

        Integer vacancyId =
                respondedApplicantDto
                        .getVacancyId();

        Resume resume =
                resumeService.findEntityById(
                        resumeId
                );

        Vacancy vacancy =
                vacancyService.findEntityById(
                        vacancyId
                );

        if (!resume.getApplicant()
                .getId()
                .equals(applicant.getId())) {
            throw new IllegalArgumentException(
                    "You can only respond with your own resume"
            );
        }

        if (!Boolean.TRUE.equals(
                resume.getIsActive()
        )) {
            throw new IllegalArgumentException(
                    "Cannot respond with an inactive resume"
            );
        }

        if (!Boolean.TRUE.equals(
                vacancy.getIsActive()
        )) {
            throw new IllegalArgumentException(
                    "Cannot respond to an inactive vacancy"
            );
        }

        if (respondedApplicantRepository
                .existsByResume_IdAndVacancy_Id(
                        resumeId,
                        vacancyId
                )) {
            throw new ResponseAlreadyExistsException(
                    resumeId,
                    vacancyId
            );
        }

        RespondedApplicant response =
                RespondedApplicant.builder()
                        .resume(resume)
                        .vacancy(vacancy)
                        .confirmation(false)
                        .build();

        respondedApplicantRepository.save(
                response
        );

        log.info(
                "Response created: resume id {}, vacancy id {}",
                resumeId,
                vacancyId
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<User>
    findApplicantsByVacancyId(
            Integer vacancyId,
            String userEmail
    ) {
        User employer =
                findAuthenticatedUser(
                        userEmail,
                        AccountType.EMPLOYER
                );

        Vacancy vacancy =
                vacancyService.findEntityById(
                        vacancyId
                );

        if (!vacancy.getAuthor()
                .getId()
                .equals(employer.getId())) {
            throw new IllegalArgumentException(
                    "You can only view responses to your own vacancy"
            );
        }

        return respondedApplicantRepository
                .findApplicantsByVacancyId(
                        vacancyId
                );
    }

    private User findAuthenticatedUser(
            String userEmail,
            AccountType expectedAccountType
    ) {
        User user =
                userService.findByEmail(
                                userEmail
                        )
                        .orElseThrow(() ->
                                new NoSuchElementException(
                                        "User with email "
                                                + userEmail
                                                + " not found"
                                )
                        );

        if (user.getAccountType()
                != expectedAccountType) {
            throw new InvalidAccountTypeException(
                    user.getId(),
                    expectedAccountType
            );
        }

        return user;
    }
}