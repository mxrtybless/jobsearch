package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.EducationInfoDao;
import kg.attractor.jobsearch.exception.InvalidEducationPeriodException;
import kg.attractor.jobsearch.model.EducationInfo;
import kg.attractor.jobsearch.service.EducationInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EducationInfoServiceImpl
        implements EducationInfoService {

    private final EducationInfoDao
            educationInfoDao;

    @Override
    public List<EducationInfo> findByResumeId(
            Integer resumeId
    ) {
        log.debug(
                "Searching education records by resume id: {}",
                resumeId
        );

        return educationInfoDao.findByResumeId(
                resumeId
        );
    }

    @Override
    public void saveAll(
            Integer resumeId,
            List<EducationInfo> educationInfo
    ) {
        validatePeriods(educationInfo);

        saveRecords(
                resumeId,
                educationInfo
        );
    }

    @Override
    public void replaceAll(
            Integer resumeId,
            List<EducationInfo> educationInfo
    ) {
        validatePeriods(educationInfo);

        educationInfoDao.deleteByResumeId(
                resumeId
        );

        saveRecords(
                resumeId,
                educationInfo
        );

        log.info(
                "Education records replaced for resume id: {}",
                resumeId
        );
    }

    @Override
    public void deleteByResumeId(
            Integer resumeId
    ) {
        educationInfoDao.deleteByResumeId(
                resumeId
        );

        log.info(
                "Education records deleted for resume id: {}",
                resumeId
        );
    }

    private void saveRecords(
            Integer resumeId,
            List<EducationInfo> educationInfo
    ) {
        if (educationInfo == null) {
            return;
        }

        for (EducationInfo education
                : educationInfo) {

            education.setResumeId(
                    resumeId
            );

            educationInfoDao.save(
                    education
            );
        }

        log.info(
                "Education records saved for resume id: {}",
                resumeId
        );
    }

    private void validatePeriods(
            List<EducationInfo> educationInfo
    ) {
        if (educationInfo == null) {
            return;
        }

        for (EducationInfo education
                : educationInfo) {

            if (education.getStartDate()
                    != null
                    && education.getEndDate()
                    != null
                    && education.getEndDate()
                    .isBefore(
                            education.getStartDate()
                    )) {

                throw new InvalidEducationPeriodException(
                        education.getInstitution()
                );
            }
        }
    }
}