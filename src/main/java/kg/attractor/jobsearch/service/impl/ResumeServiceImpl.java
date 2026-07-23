package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.EducationInfoDao;
import kg.attractor.jobsearch.dao.ResumeDao;
import kg.attractor.jobsearch.dao.WorkExperienceInfoDao;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.model.EducationInfo;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.WorkExperienceInfo;
import kg.attractor.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl
        implements ResumeService {

    private final ResumeDao resumeDao;

    private final EducationInfoDao
            educationInfoDao;

    private final WorkExperienceInfoDao
            workExperienceInfoDao;

    @Override
    public Integer createResume(
            ResumeDto resumeDto
    ) {
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

        return resumeId;
    }

    @Override
    public void editResume(
            Integer id,
            ResumeDto resumeDto
    ) {
        Resume savedResume =
                resumeDao.findById(id)
                        .orElseThrow();

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
    }

    @Override
    public void deleteResume(Integer id) {
        educationInfoDao.deleteByResumeId(id);

        workExperienceInfoDao
                .deleteByResumeId(id);

        resumeDao.deleteById(id);
    }

    @Override
    public ResumeDto findById(Integer id) {
        Resume resume =
                resumeDao.findById(id)
                        .orElseThrow();

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