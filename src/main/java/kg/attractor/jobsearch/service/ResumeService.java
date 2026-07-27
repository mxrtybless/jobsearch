package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResumeDto;

import java.util.List;

public interface ResumeService {

    Integer createResume(
            ResumeDto resumeDto,
            String userEmail
    );

    void editResume(
            Integer id,
            ResumeDto resumeDto,
            String userEmail
    );

    void deleteResume(
            Integer id,
            String userEmail
    );

    ResumeDto findById(Integer id);

    List<ResumeDto> findAllActive();

    List<ResumeDto> findByCategoryId(
            Integer categoryId
    );

    List<ResumeDto> findByApplicantId(
            Integer applicantId
    );
}