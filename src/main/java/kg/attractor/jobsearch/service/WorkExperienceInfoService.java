package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.WorkExperienceInfoDto;

import java.util.List;

public interface WorkExperienceInfoService {

    List<WorkExperienceInfoDto>
    findByResumeId(
            Integer resumeId
    );

    void saveAll(
            Integer resumeId,
            List<WorkExperienceInfoDto>
                    workExperienceInfo
    );

    void replaceAll(
            Integer resumeId,
            List<WorkExperienceInfoDto>
                    workExperienceInfo
    );

    void deleteByResumeId(
            Integer resumeId
    );
}