package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.dao.mappers.VacancyMapper;
import kg.attractor.jobsearch.model.Vacancy;
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
public class VacancyDao {
    private final JdbcTemplate jdbcTemplate;

    public Integer save(Vacancy vacancy) {
        String sql = """
                INSERT INTO vacancies
                (
                    name,
                    description,
                    category_id,
                    salary,
                    exp_from,
                    exp_to,
                    is_active,
                    author_id,
                    created_date,
                    update_time
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder =
                new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement =
                    connection.prepareStatement(
                            sql,
                            Statement.RETURN_GENERATED_KEYS
                    );

            statement.setString(
                    1,
                    vacancy.getName()
            );

            statement.setString(
                    2,
                    vacancy.getDescription()
            );

            statement.setInt(
                    3,
                    vacancy.getCategoryId()
            );

            statement.setBigDecimal(
                    4,
                    vacancy.getSalary()
            );

            statement.setObject(
                    5,
                    vacancy.getExpFrom()
            );

            statement.setObject(
                    6,
                    vacancy.getExpTo()
            );

            statement.setBoolean(
                    7,
                    vacancy.getIsActive()
            );

            statement.setInt(
                    8,
                    vacancy.getAuthorId()
            );

            statement.setObject(
                    9,
                    vacancy.getCreatedDate()
            );

            statement.setObject(
                    10,
                    vacancy.getUpdateTime()
            );

            return statement;
        }, keyHolder);

        return Objects.requireNonNull(
                keyHolder.getKey()
        ).intValue();
    }

    public Optional<Vacancy> findById(
            Integer id
    ) {
        String sql = """
                SELECT *
                FROM vacancies
                WHERE id = ?
                """;

        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(
                                sql,
                                new VacancyMapper(),
                                id
                        )
                )
        );
    }

    public List<Vacancy> findAll() {
        String sql = """
                SELECT *
                FROM vacancies
                ORDER BY update_time DESC
                """;

        return jdbcTemplate.query(
                sql,
                new VacancyMapper()
        );
    }

    public List<Vacancy> findAllActive() {
        String sql = """
                SELECT *
                FROM vacancies
                WHERE is_active = TRUE
                ORDER BY update_time DESC
                """;

        return jdbcTemplate.query(
                sql,
                new VacancyMapper()
        );
    }

    public List<Vacancy> findByCategoryId(
            Integer categoryId
    ) {
        String sql = """
                SELECT *
                FROM vacancies
                WHERE category_id = ?
                  AND is_active = TRUE
                ORDER BY update_time DESC
                """;

        return jdbcTemplate.query(
                sql,
                new VacancyMapper(),
                categoryId
        );
    }

    public List<Vacancy>
    findRespondedByApplicantId(
            Integer applicantId
    ) {
        String sql = """
                SELECT DISTINCT v.*
                FROM vacancies v
                JOIN responded_applicants ra
                    ON ra.vacancy_id = v.id
                JOIN resumes r
                    ON r.id = ra.resume_id
                WHERE r.applicant_id = ?
                ORDER BY v.update_time DESC
                """;

        return jdbcTemplate.query(
                sql,
                new VacancyMapper(),
                applicantId
        );
    }

    public void update(Vacancy vacancy) {
        String sql = """
                UPDATE vacancies
                SET name = ?,
                    description = ?,
                    category_id = ?,
                    salary = ?,
                    exp_from = ?,
                    exp_to = ?,
                    is_active = ?,
                    update_time = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                vacancy.getName(),
                vacancy.getDescription(),
                vacancy.getCategoryId(),
                vacancy.getSalary(),
                vacancy.getExpFrom(),
                vacancy.getExpTo(),
                vacancy.getIsActive(),
                vacancy.getUpdateTime(),
                vacancy.getId()
        );
    }

    public void deleteById(Integer id) {
        String sql = """
                DELETE FROM vacancies
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }
}