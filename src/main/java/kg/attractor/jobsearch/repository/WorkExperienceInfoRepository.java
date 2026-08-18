package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.WorkExperienceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkExperienceInfoRepository
        extends JpaRepository<WorkExperienceInfo, Integer> {

    List<WorkExperienceInfo> findByResume_Id(
            Integer resumeId
    );

    void deleteByResume_Id(
            Integer resumeId
    );
}