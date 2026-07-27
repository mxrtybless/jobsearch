package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.model.EducationInfo;

import java.util.List;

public interface EducationInfoService {

    List<EducationInfo> findByResumeId(
            Integer resumeId
    );

    void saveAll(
            Integer resumeId,
            List<EducationInfo> educationInfo
    );

    void replaceAll(
            Integer resumeId,
            List<EducationInfo> educationInfo
    );

    void deleteByResumeId(
            Integer resumeId
    );
}