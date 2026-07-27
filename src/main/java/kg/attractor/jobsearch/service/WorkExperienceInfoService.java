package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.model.WorkExperienceInfo;

import java.util.List;

public interface WorkExperienceInfoService {

    List<WorkExperienceInfo> findByResumeId(
            Integer resumeId
    );

    void saveAll(
            Integer resumeId,
            List<WorkExperienceInfo>
                    workExperienceInfo
    );

    void replaceAll(
            Integer resumeId,
            List<WorkExperienceInfo>
                    workExperienceInfo
    );

    void deleteByResumeId(
            Integer resumeId
    );
}