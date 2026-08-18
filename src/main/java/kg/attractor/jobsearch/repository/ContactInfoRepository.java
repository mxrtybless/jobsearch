package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactInfoRepository
        extends JpaRepository<ContactInfo, Integer> {

    List<ContactInfo> findByResume_Id(
            Integer resumeId
    );

    void deleteByResume_Id(
            Integer resumeId
    );
}