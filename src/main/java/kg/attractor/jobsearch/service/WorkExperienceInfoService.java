package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.WorkExperienceInfoDto;
import kg.attractor.jobsearch.model.Resume;

import java.util.List;

public interface WorkExperienceInfoService {

    List<WorkExperienceInfoDto>
    findByResumeId(
            Integer resumeId
    );

    void saveAll(
            Resume resume,
            List<WorkExperienceInfoDto>
                    workExperienceInfo
    );

    void replaceAll(
            Resume resume,
            List<WorkExperienceInfoDto>
                    workExperienceInfo
    );

    void deleteByResumeId(
            Integer resumeId
    );
}