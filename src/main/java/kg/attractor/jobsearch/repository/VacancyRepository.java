package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VacancyRepository
        extends JpaRepository<Vacancy, Integer> {

    List<Vacancy> findAllByOrderByUpdateTimeDesc();

    List<Vacancy>
    findAllByIsActiveTrueOrderByUpdateTimeDesc();

    List<Vacancy>
    findByCategory_IdAndIsActiveTrueOrderByUpdateTimeDesc(
            Integer categoryId
    );

    List<Vacancy>
    findByAuthor_IdOrderByUpdateTimeDesc(
            Integer authorId
    );

    @Query("""
            select distinct v
            from Vacancy v
            join v.responses response
            where response.resume.applicant.id = :applicantId
            order by v.updateTime desc
            """)
    List<Vacancy> findRespondedByApplicantId(
            @Param("applicantId")
            Integer applicantId
    );
}