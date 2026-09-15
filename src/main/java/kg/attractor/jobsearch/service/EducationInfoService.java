package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.EducationInfoDto;
import kg.attractor.jobsearch.model.Resume;

import java.util.List;

public interface EducationInfoService {

    List<EducationInfoDto> findByResumeId(
            Integer resumeId
    );

    void saveAll(
            Resume resume,
            List<EducationInfoDto> educationInfo
    );

    void replaceAll(
            Resume resume,
            List<EducationInfoDto> educationInfo
    );

    void deleteByResumeId(
            Integer resumeId
    );
}