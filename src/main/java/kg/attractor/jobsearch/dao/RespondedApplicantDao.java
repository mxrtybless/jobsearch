package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.dao.mappers.UserMapper;
import kg.attractor.jobsearch.model.RespondedApplicant;
import kg.attractor.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RespondedApplicantDao {
    private final JdbcTemplate jdbcTemplate;

    public void save(
            RespondedApplicant respondedApplicant
    ) {
        String sql = """
                INSERT INTO responded_applicants
                (
                    resume_id,
                    vacancy_id,
                    confirmation
                )
                VALUES (?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                respondedApplicant.getResumeId(),
                respondedApplicant.getVacancyId(),
                respondedApplicant.getConfirmation()
        );
    }

    public boolean exists(
            Integer resumeId,
            Integer vacancyId
    ) {
        String sql = """
                SELECT COUNT(*)
                FROM responded_applicants
                WHERE resume_id = ?
                  AND vacancy_id = ?
                """;

        Integer count =
                jdbcTemplate.queryForObject(
                        sql,
                        Integer.class,
                        resumeId,
                        vacancyId
                );

        return count != null && count > 0;
    }

    public List<User>
    findApplicantsByVacancyId(
            Integer vacancyId
    ) {
        String sql = """
                SELECT DISTINCT u.*
                FROM users u
                JOIN resumes r
                    ON r.applicant_id = u.id
                JOIN responded_applicants ra
                    ON ra.resume_id = r.id
                WHERE ra.vacancy_id = ?
                  AND u.account_type = 'APPLICANT'
                """;

        return jdbcTemplate.query(
                sql,
                new UserMapper(),
                vacancyId
        );
    }
}