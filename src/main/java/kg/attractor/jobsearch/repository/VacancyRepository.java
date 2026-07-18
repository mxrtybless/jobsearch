package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.dao.mappers.VacancyMapper;
import kg.attractor.jobsearch.model.Vacancy;
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
public class VacancyRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Vacancy> findAll() {
        String sql = """
                SELECT id,
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
                SELECT id,
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
                FROM vacancies
                WHERE is_active = TRUE
                ORDER BY update_time DESC
                """;

        return jdbcTemplate.query(
                sql,
                new VacancyMapper()
        );
    }

    public Optional<Vacancy> findById(Integer id) {
        String sql = """
                SELECT id,
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
                FROM vacancies
                WHERE id = ?
                """;

        List<Vacancy> vacancies = jdbcTemplate.query(
                sql,
                new VacancyMapper(),
                id
        );

        return Optional.ofNullable(
                DataAccessUtils.singleResult(vacancies)
        );
    }

    public List<Vacancy> findByAuthorId(Integer authorId) {
        String sql = """
                SELECT id,
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
                FROM vacancies
                WHERE author_id = ?
                ORDER BY update_time DESC
                """;

        return jdbcTemplate.query(
                sql,
                new VacancyMapper(),
                authorId
        );
    }

    public List<Vacancy> findActiveByCategoryId(
            Integer categoryId
    ) {
        String sql = """
                SELECT id,
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

    public List<Vacancy> findActiveByName(String name) {
        String sql = """
                SELECT id,
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
                FROM vacancies
                WHERE LOWER(name) LIKE LOWER(?)
                  AND is_active = TRUE
                ORDER BY update_time DESC
                """;

        return jdbcTemplate.query(
                sql,
                new VacancyMapper(),
                "%" + name + "%"
        );
    }

    public List<Vacancy> findRespondedByApplicantId(
            Integer applicantId
    ) {
        String sql = """
                SELECT DISTINCT
                       v.id,
                       v.name,
                       v.description,
                       v.category_id,
                       v.salary,
                       v.exp_from,
                       v.exp_to,
                       v.is_active,
                       v.author_id,
                       v.created_date,
                       v.update_time
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

    public Vacancy save(Vacancy vacancy) {
        if (vacancy.getId() == null) {
            return insert(vacancy);
        }

        return update(vacancy);
    }

    public void deleteById(Integer id) {
        String sql = """
                DELETE FROM vacancies
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }

    private Vacancy insert(Vacancy vacancy) {
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

        KeyHolder keyHolder = new GeneratedKeyHolder();

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

            statement.setInt(
                    5,
                    vacancy.getExpFrom()
            );

            statement.setInt(
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

        Number generatedId = keyHolder.getKey();

        if (generatedId != null) {
            vacancy.setId(generatedId.intValue());
        }

        return vacancy;
    }

    private Vacancy update(Vacancy vacancy) {
        String sql = """
                UPDATE vacancies
                SET name = ?,
                    description = ?,
                    category_id = ?,
                    salary = ?,
                    exp_from = ?,
                    exp_to = ?,
                    is_active = ?,
                    author_id = ?,
                    created_date = ?,
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
                vacancy.getAuthorId(),
                vacancy.getCreatedDate(),
                vacancy.getUpdateTime(),
                vacancy.getId()
        );

        return vacancy;
    }
}