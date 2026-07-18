package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.dao.mappers.ResumeMapper;
import kg.attractor.jobsearch.model.Resume;
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
public class ResumeRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Resume> findAll() {
        String sql = """
                SELECT id,
                       applicant_id,
                       name,
                       category_id,
                       salary,
                       is_active,
                       created_date,
                       update_time
                FROM resumes
                ORDER BY update_time DESC
                """;

        return jdbcTemplate.query(
                sql,
                new ResumeMapper()
        );
    }

    public List<Resume> findAllActive() {
        String sql = """
                SELECT id,
                       applicant_id,
                       name,
                       category_id,
                       salary,
                       is_active,
                       created_date,
                       update_time
                FROM resumes
                WHERE is_active = TRUE
                ORDER BY update_time DESC
                """;

        return jdbcTemplate.query(
                sql,
                new ResumeMapper()
        );
    }

    public Optional<Resume> findById(Integer id) {
        String sql = """
                SELECT id,
                       applicant_id,
                       name,
                       category_id,
                       salary,
                       is_active,
                       created_date,
                       update_time
                FROM resumes
                WHERE id = ?
                """;

        List<Resume> resumes = jdbcTemplate.query(
                sql,
                new ResumeMapper(),
                id
        );

        return Optional.ofNullable(
                DataAccessUtils.singleResult(resumes)
        );
    }

    public List<Resume> findByApplicantId(Integer applicantId) {
        String sql = """
                SELECT id,
                       applicant_id,
                       name,
                       category_id,
                       salary,
                       is_active,
                       created_date,
                       update_time
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

    public List<Resume> findActiveByCategoryId(Integer categoryId) {
        String sql = """
                SELECT id,
                       applicant_id,
                       name,
                       category_id,
                       salary,
                       is_active,
                       created_date,
                       update_time
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

    public Resume save(Resume resume) {
        if (resume.getId() == null) {
            return insert(resume);
        }

        return update(resume);
    }

    public void deleteById(Integer id) {
        String sql = """
                DELETE FROM resumes
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }

    private Resume insert(Resume resume) {
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

        KeyHolder keyHolder = new GeneratedKeyHolder();

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

        Number generatedId = keyHolder.getKey();

        if (generatedId != null) {
            resume.setId(generatedId.intValue());
        }

        return resume;
    }

    private Resume update(Resume resume) {
        String sql = """
                UPDATE resumes
                SET applicant_id = ?,
                    name = ?,
                    category_id = ?,
                    salary = ?,
                    is_active = ?,
                    created_date = ?,
                    update_time = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                resume.getApplicantId(),
                resume.getName(),
                resume.getCategoryId(),
                resume.getSalary(),
                resume.getIsActive(),
                resume.getCreatedDate(),
                resume.getUpdateTime(),
                resume.getId()
        );

        return resume;
    }
}