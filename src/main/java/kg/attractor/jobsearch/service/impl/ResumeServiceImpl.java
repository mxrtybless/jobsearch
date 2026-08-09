package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ResumeDao;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.exception.InvalidAccountTypeException;
import kg.attractor.jobsearch.exception.ResumeNotFoundException;
import kg.attractor.jobsearch.model.AccountType;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.CategoryService;
import kg.attractor.jobsearch.service.ContactInfoService;
import kg.attractor.jobsearch.service.EducationInfoService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.WorkExperienceInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl
        implements ResumeService {

    private final ResumeDao resumeDao;
    private final UserService userService;
    private final CategoryService categoryService;
    private final EducationInfoService educationInfoService;
    private final WorkExperienceInfoService workExperienceInfoService;
    private final ContactInfoService contactInfoService;

    @Override
    @Transactional
    public Integer createResume(
            ResumeDto resumeDto,
            String userEmail
    ) {
        User applicant =
                findAuthenticatedApplicant(
                        userEmail
                );

        categoryService.findById(
                resumeDto.getCategoryId()
        );

        LocalDateTime now =
                LocalDateTime.now();

        Resume resume = Resume.builder()
                .applicantId(applicant.getId())
                .name(resumeDto.getName())
                .categoryId(resumeDto.getCategoryId())
                .salary(resumeDto.getSalary())
                .isActive(resumeDto.getIsActive())
                .createdDate(now)
                .updateTime(now)
                .build();

        Integer resumeId =
                resumeDao.save(resume);

        contactInfoService.saveAll(
                resumeId,
                resumeDto.getContactInfo()
        );

        educationInfoService.saveAll(
                resumeId,
                resumeDto.getEducationInfo()
        );

        workExperienceInfoService.saveAll(
                resumeId,
                resumeDto.getWorkExperienceInfo()
        );

        log.info(
                "Resume created successfully with id: {}",
                resumeId
        );

        return resumeId;
    }

    @Override
    @Transactional
    public void editResume(
            Integer id,
            ResumeDto resumeDto,
            String userEmail
    ) {
        User applicant =
                findAuthenticatedApplicant(
                        userEmail
                );

        Resume savedResume =
                resumeDao.findById(id)
                        .orElseThrow(() ->
                                new ResumeNotFoundException(
                                        id
                                )
                        );

        validateResumeOwner(
                savedResume,
                applicant
        );

        categoryService.findById(
                resumeDto.getCategoryId()
        );

        savedResume.setName(
                resumeDto.getName()
        );
        savedResume.setCategoryId(
                resumeDto.getCategoryId()
        );
        savedResume.setSalary(
                resumeDto.getSalary()
        );
        savedResume.setIsActive(
                resumeDto.getIsActive()
        );
        savedResume.setUpdateTime(
                LocalDateTime.now()
        );

        resumeDao.update(savedResume);

        contactInfoService.replaceAll(
                id,
                resumeDto.getContactInfo()
        );

        educationInfoService.replaceAll(
                id,
                resumeDto.getEducationInfo()
        );

        workExperienceInfoService.replaceAll(
                id,
                resumeDto.getWorkExperienceInfo()
        );

        log.info(
                "Resume updated successfully with id: {}",
                id
        );
    }

    @Override
    @Transactional
    public void updateResumeDate(
            Integer id,
            String userEmail
    ) {
        User applicant =
                findAuthenticatedApplicant(
                        userEmail
                );

        Resume savedResume =
                resumeDao.findById(id)
                        .orElseThrow(() ->
                                new ResumeNotFoundException(
                                        id
                                )
                        );

        validateResumeOwner(
                savedResume,
                applicant
        );

        resumeDao.updateTime(
                id,
                LocalDateTime.now()
        );

        log.info(
                "Resume update time refreshed for id: {}",
                id
        );
    }

    @Override
    @Transactional
    public void deleteResume(
            Integer id,
            String userEmail
    ) {
        User applicant =
                findAuthenticatedApplicant(
                        userEmail
                );

        Resume savedResume =
                resumeDao.findById(id)
                        .orElseThrow(() ->
                                new ResumeNotFoundException(
                                        id
                                )
                        );

        validateResumeOwner(
                savedResume,
                applicant
        );

        contactInfoService.deleteByResumeId(id);
        educationInfoService.deleteByResumeId(id);
        workExperienceInfoService.deleteByResumeId(id);
        resumeDao.deleteById(id);

        log.info(
                "Resume deleted successfully with id: {}",
                id
        );
    }

    @Override
    public ResumeDto findById(Integer id) {
        Resume resume =
                resumeDao.findById(id)
                        .orElseThrow(() ->
                                new ResumeNotFoundException(
                                        id
                                )
                        );

        return convertToDto(resume);
    }

    @Override
    public ResumeDto findOwnedById(
            Integer id,
            String userEmail
    ) {
        User applicant =
                findAuthenticatedApplicant(
                        userEmail
                );

        Resume resume =
                resumeDao.findById(id)
                        .orElseThrow(() ->
                                new ResumeNotFoundException(
                                        id
                                )
                        );

        validateResumeOwner(
                resume,
                applicant
        );

        return convertToDto(resume);
    }

    @Override
    public List<ResumeDto> findAllActive() {
        return resumeDao.findAllActive()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<ResumeDto> findByCategoryId(
            Integer categoryId
    ) {
        categoryService.findById(categoryId);

        return resumeDao
                .findByCategoryId(categoryId)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<ResumeDto> findByApplicantId(
            Integer applicantId
    ) {
        validateApplicantById(
                applicantId
        );

        return resumeDao
                .findByApplicantId(applicantId)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    private ResumeDto convertToDto(
            Resume resume
    ) {
        ResumeDto resumeDto =
                new ResumeDto();

        resumeDto.setId(
                resume.getId()
        );
        resumeDto.setApplicantId(
                resume.getApplicantId()
        );
        resumeDto.setName(
                resume.getName()
        );
        resumeDto.setCategoryId(
                resume.getCategoryId()
        );
        resumeDto.setSalary(
                resume.getSalary()
        );
        resumeDto.setIsActive(
                resume.getIsActive()
        );
        resumeDto.setCreatedDate(
                resume.getCreatedDate()
        );
        resumeDto.setUpdateTime(
                resume.getUpdateTime()
        );

        resumeDto.setContactInfo(
                contactInfoService
                        .findByResumeId(
                                resume.getId()
                        )
        );

        resumeDto.setEducationInfo(
                educationInfoService
                        .findByResumeId(
                                resume.getId()
                        )
        );

        resumeDto.setWorkExperienceInfo(
                workExperienceInfoService
                        .findByResumeId(
                                resume.getId()
                        )
        );

        return resumeDto;
    }

    private User findAuthenticatedApplicant(
            String userEmail
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
                != AccountType.APPLICANT) {
            throw new InvalidAccountTypeException(
                    user.getId(),
                    AccountType.APPLICANT
            );
        }

        return user;
    }

    private void validateApplicantById(
            Integer applicantId
    ) {
        User user =
                userService.findProfileById(
                        applicantId
                );

        if (user.getAccountType()
                != AccountType.APPLICANT) {
            throw new InvalidAccountTypeException(
                    applicantId,
                    AccountType.APPLICANT
            );
        }
    }

    private void validateResumeOwner(
            Resume resume,
            User applicant
    ) {
        if (!resume.getApplicantId()
                .equals(applicant.getId())) {
            throw new IllegalArgumentException(
                    "You can only change your own resume"
            );
        }
    }
}