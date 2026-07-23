package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResumeDto;

import java.util.List;

public interface ResumeService {
    Integer createResume(
            ResumeDto resumeDto
    );

    void editResume(
            Integer id,
            ResumeDto resumeDto
    );

    void deleteResume(Integer id);

    ResumeDto findById(Integer id);

    List<ResumeDto> findAllActive();

    List<ResumeDto> findByCategoryId(
            Integer categoryId
    );

    List<ResumeDto> findByApplicantId(
            Integer applicantId
    );
}