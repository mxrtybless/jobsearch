package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository
        extends JpaRepository<Resume, Integer> {

    List<Resume> findAllByIsActiveTrueOrderByUpdateTimeDesc();

    List<Resume> findByCategory_IdOrderByUpdateTimeDesc(
            Integer categoryId
    );

    List<Resume> findByApplicant_IdOrderByUpdateTimeDesc(
            Integer applicantId
    );
}