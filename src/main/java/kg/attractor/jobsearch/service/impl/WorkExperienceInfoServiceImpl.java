package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.WorkExperienceInfoDao;
import kg.attractor.jobsearch.dto.WorkExperienceInfoDto;
import kg.attractor.jobsearch.model.WorkExperienceInfo;
import kg.attractor.jobsearch.service.WorkExperienceInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkExperienceInfoServiceImpl
        implements WorkExperienceInfoService {

    private final WorkExperienceInfoDao
            workExperienceInfoDao;

    @Override
    public List<WorkExperienceInfoDto>
    findByResumeId(
            Integer resumeId
    ) {
        return workExperienceInfoDao
                .findByResumeId(resumeId)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    @Transactional
    public void saveAll(
            Integer resumeId,
            List<WorkExperienceInfoDto>
                    workExperienceInfo
    ) {
        saveRecords(
                resumeId,
                workExperienceInfo
        );
    }

    @Override
    @Transactional
    public void replaceAll(
            Integer resumeId,
            List<WorkExperienceInfoDto>
                    workExperienceInfo
    ) {
        workExperienceInfoDao
                .deleteByResumeId(
                        resumeId
                );

        saveRecords(
                resumeId,
                workExperienceInfo
        );
    }

    @Override
    public void deleteByResumeId(
            Integer resumeId
    ) {
        workExperienceInfoDao
                .deleteByResumeId(
                        resumeId
                );
    }

    private void saveRecords(
            Integer resumeId,
            List<WorkExperienceInfoDto>
                    workExperienceInfo
    ) {
        if (workExperienceInfo == null) {
            return;
        }

        for (WorkExperienceInfoDto workDto
                : workExperienceInfo) {

            WorkExperienceInfo workExperience =
                    WorkExperienceInfo.builder()
                            .resumeId(resumeId)
                            .years(
                                    workDto.getYears()
                            )
                            .companyName(
                                    workDto
                                            .getCompanyName()
                            )
                            .position(
                                    workDto
                                            .getPosition()
                            )
                            .responsibilities(
                                    workDto
                                            .getResponsibilities()
                            )
                            .build();

            workExperienceInfoDao.save(
                    workExperience
            );
        }

        log.info(
                "Work experience records saved for resume id: {}",
                resumeId
        );
    }

    private WorkExperienceInfoDto convertToDto(
            WorkExperienceInfo workExperience
    ) {
        return new WorkExperienceInfoDto(
                workExperience.getId(),
                workExperience.getResumeId(),
                workExperience.getYears(),
                workExperience.getCompanyName(),
                workExperience.getPosition(),
                workExperience.getResponsibilities()
        );
    }
}