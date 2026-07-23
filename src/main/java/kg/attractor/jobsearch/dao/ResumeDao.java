package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.dao.mappers.ResumeMapper;
import kg.attractor.jobsearch.model.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ResumeDao {
    private final JdbcTemplate jdbcTemplate;

    public Integer save(Resume resume) {
        String sql = """
                INSERT INTO resumes
                (
                    applicant_id,
                    name,
                    category_id,
                    salary,
                    is_active,
                    created_date,
                    update_time
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder =
                new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement =
                    connection.prepareStatement(
                            sql,
                            Statement.RETURN_GENERATED_KEYS
                    );

            statement.setInt(
                    1,
                    resume.getApplicantId()
            );

            statement.setString(
                    2,
                    resume.getName()
            );

            statement.setInt(
                    3,
                    resume.getCategoryId()
            );

            statement.setBigDecimal(
                    4,
                    resume.getSalary()
            );

            statement.setBoolean(
                    5,
                    resume.getIsActive()
            );

            statement.setObject(
                    6,
                    resume.getCreatedDate()
            );

            statement.setObject(
                    7,
                    resume.getUpdateTime()
            );

            return statement;
        }, keyHolder);

        return Objects.requireNonNull(
                keyHolder.getKey()
        ).intValue();
    }

    public Optional<Resume> findById(
            Integer id
    ) {
        String sql = """
                SELECT *
                FROM resumes
                WHERE id = ?
                """;

        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(
                                sql,
                                new ResumeMapper(),
                                id
                        )
                )
        );
    }

    public List<Resume> findAllActive() {
        String sql = """
                SELECT *
                FROM resumes
                WHERE is_active = TRUE
                ORDER BY update_time DESC
                """;

        return jdbcTemplate.query(
                sql,
                new ResumeMapper()
        );
    }

    public List<Resume> findByCategoryId(
            Integer categoryId
    ) {
        String sql = """
                SELECT *
                FROM resumes
                WHERE category_id = ?
                  AND is_active = TRUE
                ORDER BY update_time DESC
                """;

        return jdbcTemplate.query(
                sql,
                new ResumeMapper(),
                categoryId
        );
    }

    public List<Resume> findByApplicantId(
            Integer applicantId
    ) {
        String sql = """
                SELECT *
                FROM resumes
                WHERE applicant_id = ?
                ORDER BY update_time DESC
                """;

        return jdbcTemplate.query(
                sql,
                new ResumeMapper(),
                applicantId
        );
    }

    public void update(Resume resume) {
        String sql = """
                UPDATE resumes
                SET name = ?,
                    category_id = ?,
                    salary = ?,
                    is_active = ?,
                    update_time = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                resume.getName(),
                resume.getCategoryId(),
                resume.getSalary(),
                resume.getIsActive(),
                resume.getUpdateTime(),
                resume.getId()
        );
    }

    public void deleteById(Integer id) {
        String sql = """
                DELETE FROM resumes
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }
}