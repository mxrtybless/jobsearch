package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.RespondedApplicant;
import kg.attractor.jobsearch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RespondedApplicantRepository
        extends JpaRepository<RespondedApplicant, Integer> {

    boolean existsByResume_IdAndVacancy_Id(
            Integer resumeId,
            Integer vacancyId
    );

    @Query("""
            select distinct response.resume.applicant
            from RespondedApplicant response
            where response.vacancy.id = :vacancyId
            """)
    List<User> findApplicantsByVacancyId(
            @Param("vacancyId")
            Integer vacancyId
    );

    java.util.Optional<RespondedApplicant> findByResume_IdAndVacancy_Id(Integer resumeId, Integer vacancyId);

    @Query("""
            select r from RespondedApplicant r
            where (r.resume.applicant.id = :userId or r.vacancy.author.id = :userId)
              and (:vacancyId is null or r.vacancy.id = :vacancyId)
            order by r.id desc
            """)
    List<RespondedApplicant> findDiscussions(@Param("userId") Integer userId,
                                             @Param("vacancyId") Integer vacancyId);

}
