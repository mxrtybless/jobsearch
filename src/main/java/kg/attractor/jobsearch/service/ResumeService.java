package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResumeDto;
import org.springframework.data.domain.Page;

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

    void updateResumeDate(
            Integer id,
            String userEmail
    );

    void deleteResume(
            Integer id,
            String userEmail
    );

    ResumeDto findById(Integer id);

    ResumeDto findOwnedById(
            Integer id,
            String userEmail
    );

    List<ResumeDto> findAllActive();

    Page<ResumeDto> findAllActive(int page, int size, String sort);

    Page<ResumeDto> findByApplicantId(Integer applicantId, int page, int size, String sort);

    List<ResumeDto> findByCategoryId(
            Integer categoryId
    );

    List<ResumeDto> findByApplicantId(
            Integer applicantId
    );
}