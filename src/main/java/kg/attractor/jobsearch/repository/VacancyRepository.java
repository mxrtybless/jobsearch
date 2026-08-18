package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {

    List<Vacancy> findAllByOrderByUpdateTimeDesc();

    List<Vacancy> findAllByIsActiveTrueOrderByUpdateTimeDesc();

    List<Vacancy> findByCategory_IdAndIsActiveTrueOrderByUpdateTimeDesc(Integer categoryId);

    List<Vacancy> findByAuthor_IdOrderByUpdateTimeDesc(Integer authorId);

    Page<Vacancy> findAllByIsActiveTrue(Pageable pageable);

    Page<Vacancy> findByAuthor_Id(Integer authorId, Pageable pageable);

    Page<Vacancy> findByAuthor_IdAndIsActiveTrue(Integer authorId, Pageable pageable);

    @Query(
            value = """
                    select v
                    from Vacancy v
                    left join v.responses r
                    where v.isActive = true
                    group by v
                    order by count(r) desc, v.updateTime desc
                    """,
            countQuery = "select count(v) from Vacancy v where v.isActive = true"
    )
    Page<Vacancy> findActiveOrderByResponses(Pageable pageable);

    @Query(
            value = """
                    select v
                    from Vacancy v
                    left join v.responses r
                    where v.author.id = :authorId
                    group by v
                    order by count(r) desc, v.updateTime desc
                    """,
            countQuery = "select count(v) from Vacancy v where v.author.id = :authorId"
    )
    Page<Vacancy> findByAuthorOrderByResponses(
            @Param("authorId") Integer authorId,
            Pageable pageable
    );

    @Query(
            value = """
                    select v
                    from Vacancy v
                    left join v.responses r
                    where v.author.id = :authorId
                      and v.isActive = true
                    group by v
                    order by count(r) desc, v.updateTime desc
                    """,
            countQuery = "select count(v) from Vacancy v where v.author.id = :authorId and v.isActive = true"
    )
    Page<Vacancy> findActiveByAuthorOrderByResponses(
            @Param("authorId") Integer authorId,
            Pageable pageable
    );

    @Query("""
            select distinct v
            from Vacancy v
            join v.responses response
            where response.resume.applicant.id = :applicantId
            order by v.updateTime desc
            """)
    List<Vacancy> findRespondedByApplicantId(
            @Param("applicantId") Integer applicantId
    );
}
