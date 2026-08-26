package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Resume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Integer> {

    List<Resume> findAllByIsActiveTrueOrderByUpdateTimeDesc();

    List<Resume> findByCategory_IdOrderByUpdateTimeDesc(Integer categoryId);

    List<Resume> findByApplicant_IdOrderByUpdateTimeDesc(Integer applicantId);

    Page<Resume> findAllByIsActiveTrue(Pageable pageable);

    Page<Resume> findByApplicant_Id(Integer applicantId, Pageable pageable);

    @Query(
            value = """
                    select r
                    from Resume r
                    left join r.responses response
                    where r.isActive = true
                    group by r
                    order by count(response) desc, r.updateTime desc
                    """,
            countQuery = "select count(r) from Resume r where r.isActive = true"
    )
    Page<Resume> findActiveOrderByResponses(Pageable pageable);

    @Query(
            value = """
                    select r
                    from Resume r
                    left join r.responses response
                    where r.isActive = true
                    group by r
                    order by count(response) asc, r.updateTime desc
                    """,
            countQuery = "select count(r) from Resume r where r.isActive = true"
    )
    Page<Resume> findActiveOrderByResponsesAsc(Pageable pageable);

    @Query(
            value = """
                    select r
                    from Resume r
                    left join r.responses response
                    where r.applicant.id = :applicantId
                    group by r
                    order by count(response) desc, r.updateTime desc
                    """,
            countQuery = "select count(r) from Resume r where r.applicant.id = :applicantId"
    )
    Page<Resume> findByApplicantOrderByResponses(
            @Param("applicantId") Integer applicantId,
            Pageable pageable
    );

    @Query(
            value = """
                    select r
                    from Resume r
                    left join r.responses response
                    where r.applicant.id = :applicantId
                    group by r
                    order by count(response) asc, r.updateTime desc
                    """,
            countQuery = "select count(r) from Resume r where r.applicant.id = :applicantId"
    )
    Page<Resume> findByApplicantOrderByResponsesAsc(
            @Param("applicantId") Integer applicantId,
            Pageable pageable
    );
}
