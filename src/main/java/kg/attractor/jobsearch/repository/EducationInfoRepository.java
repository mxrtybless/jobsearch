package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.EducationInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationInfoRepository
        extends JpaRepository<EducationInfo, Integer> {

    List<EducationInfo> findByResume_Id(
            Integer resumeId
    );

    void deleteByResume_Id(
            Integer resumeId
    );
}