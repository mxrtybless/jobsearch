package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.dao.mappers.RespondedApplicantMapper;
import kg.attractor.jobsearch.model.RespondedApplicant;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RespondedApplicantRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<RespondedApplicant> findAll() {
        String sql = """
                SELECT id,
                       resume_id,
                       vacancy_id,
                       confirmation
                FROM responded_applicants
                ORDER BY id
                """;

        return jdbcTemplate.query(
                sql,
                new RespondedApplicantMapper()
        );
    }

    public Optional<RespondedApplicant> findById(
            Integer id
    ) {
        String sql = """
                SELECT id,
                       resume_id,
                       vacancy_id,
                       confirmation
                FROM responded_applicants
                WHERE id = ?
                """;

        List<RespondedApplicant> responses =
                jdbcTemplate.query(
                        sql,
                        new RespondedApplicantMapper(),
                        id
                );

        return Optional.ofNullable(
                DataAccessUtils.singleResult(responses)
        );
    }

    public List<RespondedApplicant> findByVacancyId(
            Integer vacancyId
    ) {
        String sql = """
                SELECT id,
                       resume_id,
                       vacancy_id,
                       confirmation
                FROM responded_applicants
                WHERE vacancy_id = ?
                ORDER BY id
                """;

        return jdbcTemplate.query(
                sql,
                new RespondedApplicantMapper(),
                vacancyId
        );
    }

    public List<RespondedApplicant> findByResumeId(
            Integer resumeId
    ) {
        String sql = """
                SELECT id,
                       resume_id,
                       vacancy_id,
                       confirmation
                FROM responded_applicants
                WHERE resume_id = ?
                ORDER BY id
                """;

        return jdbcTemplate.query(
                sql,
                new RespondedApplicantMapper(),
                resumeId
        );
    }

    public RespondedApplicant save(
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

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement =
                    connection.prepareStatement(
                            sql,
                            Statement.RETURN_GENERATED_KEYS
                    );

            statement.setInt(
                    1,
                    respondedApplicant.getResumeId()
            );

            statement.setInt(
                    2,
                    respondedApplicant.getVacancyId()
            );

            statement.setBoolean(
                    3,
                    respondedApplicant.getConfirmation()
            );

            return statement;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();

        if (generatedId != null) {
            respondedApplicant.setId(
                    generatedId.intValue()
            );
        }

        return respondedApplicant;
    }

    public boolean existsByResumeIdAndVacancyId(
            Integer resumeId,
            Integer vacancyId
    ) {
        String sql = """
                SELECT COUNT(*)
                FROM responded_applicants
                WHERE resume_id = ?
                  AND vacancy_id = ?
                """;

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                resumeId,
                vacancyId
        );

        return count != null && count > 0;
    }
}