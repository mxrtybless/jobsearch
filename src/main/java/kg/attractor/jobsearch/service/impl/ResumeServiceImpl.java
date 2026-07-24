package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.EducationInfoDao;
import kg.attractor.jobsearch.dao.ProfileDao;
import kg.attractor.jobsearch.dao.ResumeDao;
import kg.attractor.jobsearch.dao.WorkExperienceInfoDao;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.exception.InvalidAccountTypeException;
import kg.attractor.jobsearch.exception.InvalidEducationPeriodException;
import kg.attractor.jobsearch.exception.ResumeNotFoundException;
import kg.attractor.jobsearch.exception.UserNotFoundException;
import kg.attractor.jobsearch.model.AccountType;
import kg.attractor.jobsearch.model.EducationInfo;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.WorkExperienceInfo;
import kg.attractor.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl
        implements ResumeService {

    private final ResumeDao resumeDao;
    private final ProfileDao profileDao;

    private final EducationInfoDao
            educationInfoDao;

    private final WorkExperienceInfoDao
            workExperienceInfoDao;

    @Override
    public Integer createResume(
            ResumeDto resumeDto
    ) {
        log.info(
                "Creating resume '{}' for applicant id: {}",
                resumeDto.getName(),
                resumeDto.getApplicantId()
        );

        validateApplicant(
                resumeDto.getApplicantId()
        );

        validateEducationPeriods(
                resumeDto.getEducationInfo()
        );

        LocalDateTime now =
                LocalDateTime.now();

        Resume resume = Resume.builder()
                .applicantId(
                        resumeDto.getApplicantId()
                )
                .name(resumeDto.getName())
                .categoryId(
                        resumeDto.getCategoryId()
                )
                .salary(resumeDto.getSalary())
                .isActive(true)
                .createdDate(now)
                .updateTime(now)
                .build();

        Integer resumeId =
                resumeDao.save(resume);

        saveEducationInfo(
                resumeId,
                resumeDto.getEducationInfo()
        );

        saveWorkExperienceInfo(
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
    public void editResume(
            Integer id,
            ResumeDto resumeDto
    ) {
        log.info(
                "Editing resume with id: {}",
                id
        );

        Resume savedResume =
                resumeDao.findById(id)
                        .orElseThrow(() ->
                                new ResumeNotFoundException(
                                        id
                                )
                        );

        if (!savedResume.getApplicantId()
                .equals(
                        resumeDto.getApplicantId()
                )) {
            throw new IllegalArgumentException(
                    "Resume applicant id cannot be changed"
            );
        }

        validateApplicant(
                savedResume.getApplicantId()
        );

        validateEducationPeriods(
                resumeDto.getEducationInfo()
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

        if (resumeDto.getIsActive() != null) {
            savedResume.setIsActive(
                    resumeDto.getIsActive()
            );
        }

        savedResume.setUpdateTime(
                LocalDateTime.now()
        );

        resumeDao.update(savedResume);

        if (resumeDto.getEducationInfo()
                != null) {

            educationInfoDao
                    .deleteByResumeId(id);

            saveEducationInfo(
                    id,
                    resumeDto.getEducationInfo()
            );
        }

        if (resumeDto.getWorkExperienceInfo()
                != null) {

            workExperienceInfoDao
                    .deleteByResumeId(id);

            saveWorkExperienceInfo(
                    id,
                    resumeDto
                            .getWorkExperienceInfo()
            );
        }

        log.info(
                "Resume updated successfully with id: {}",
                id
        );
    }

    @Override
    public void deleteResume(Integer id) {
        log.warn(
                "Deleting resume with id: {}",
                id
        );

        resumeDao.findById(id)
                .orElseThrow(() ->
                        new ResumeNotFoundException(
                                id
                        )
                );

        educationInfoDao.deleteByResumeId(id);

        workExperienceInfoDao
                .deleteByResumeId(id);

        resumeDao.deleteById(id);

        log.info(
                "Resume deleted successfully with id: {}",
                id
        );
    }

    @Override
    public ResumeDto findById(Integer id) {
        log.debug(
                "Searching resume by id: {}",
                id
        );

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
    public List<ResumeDto> findAllActive() {
        log.debug(
                "Searching all active resumes"
        );

        return resumeDao.findAllActive()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<ResumeDto> findByCategoryId(
            Integer categoryId
    ) {
        log.debug(
                "Searching resumes by category id: {}",
                categoryId
        );

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
        log.debug(
                "Searching resumes by applicant id: {}",
                applicantId
        );

        validateApplicant(applicantId);

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

        resumeDto.setId(resume.getId());

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

        resumeDto.setEducationInfo(
                educationInfoDao
                        .findByResumeId(
                                resume.getId()
                        )
        );

        resumeDto.setWorkExperienceInfo(
                workExperienceInfoDao
                        .findByResumeId(
                                resume.getId()
                        )
        );

        return resumeDto;
    }

    private void validateApplicant(
            Integer applicantId
    ) {
        User user = profileDao.findById(
                        applicantId
                )
                .orElseThrow(() ->
                        new UserNotFoundException(
                                applicantId
                        )
                );

        if (user.getAccountType()
                != AccountType.APPLICANT) {

            throw new InvalidAccountTypeException(
                    applicantId,
                    AccountType.APPLICANT
            );
        }
    }

    private void validateEducationPeriods(
            List<EducationInfo> educationList
    ) {
        if (educationList == null) {
            return;
        }

        for (EducationInfo educationInfo
                : educationList) {

            if (educationInfo.getStartDate()
                    != null
                    && educationInfo.getEndDate()
                    != null
                    && educationInfo.getEndDate()
                    .isBefore(
                            educationInfo
                                    .getStartDate()
                    )) {

                throw new InvalidEducationPeriodException(
                        educationInfo
                                .getInstitution()
                );
            }
        }
    }

    private void saveEducationInfo(
            Integer resumeId,
            List<EducationInfo> educationList
    ) {
        if (educationList == null) {
            return;
        }

        for (EducationInfo educationInfo
                : educationList) {

            educationInfo.setResumeId(
                    resumeId
            );

            educationInfoDao.save(
                    educationInfo
            );
        }
    }

    private void saveWorkExperienceInfo(
            Integer resumeId,
            List<WorkExperienceInfo>
                    workExperienceList
    ) {
        if (workExperienceList == null) {
            return;
        }

        for (WorkExperienceInfo
                workExperience
                : workExperienceList) {

            workExperience.setResumeId(
                    resumeId
            );

            workExperienceInfoDao.save(
                    workExperience
            );
        }
    }
}