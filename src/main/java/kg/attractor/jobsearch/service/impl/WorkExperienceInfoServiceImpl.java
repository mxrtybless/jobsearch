package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.WorkExperienceInfoDao;
import kg.attractor.jobsearch.model.WorkExperienceInfo;
import kg.attractor.jobsearch.service.WorkExperienceInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkExperienceInfoServiceImpl
        implements WorkExperienceInfoService {

    private final WorkExperienceInfoDao
            workExperienceInfoDao;

    @Override
    public List<WorkExperienceInfo>
    findByResumeId(
            Integer resumeId
    ) {
        log.debug(
                "Searching work experience records by resume id: {}",
                resumeId
        );

        return workExperienceInfoDao
                .findByResumeId(
                        resumeId
                );
    }

    @Override
    public void saveAll(
            Integer resumeId,
            List<WorkExperienceInfo>
                    workExperienceInfo
    ) {
        saveRecords(
                resumeId,
                workExperienceInfo
        );
    }

    @Override
    public void replaceAll(
            Integer resumeId,
            List<WorkExperienceInfo>
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

        log.info(
                "Work experience records replaced for resume id: {}",
                resumeId
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

        log.info(
                "Work experience records deleted for resume id: {}",
                resumeId
        );
    }

    private void saveRecords(
            Integer resumeId,
            List<WorkExperienceInfo>
                    workExperienceInfo
    ) {
        if (workExperienceInfo == null) {
            return;
        }

        for (WorkExperienceInfo workExperience
                : workExperienceInfo) {

            workExperience.setResumeId(
                    resumeId
            );

            workExperienceInfoDao.save(
                    workExperience
            );
        }

        log.info(
                "Work experience records saved for resume id: {}",
                resumeId
        );
    }
}