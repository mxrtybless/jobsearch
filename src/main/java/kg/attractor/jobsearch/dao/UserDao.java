package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.dao.mappers.UserMapper;
import kg.attractor.jobsearch.model.User;
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
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    public Integer save(User user) {
        String sql = """
                INSERT INTO users
                (
                    name,
                    surname,
                    age,
                    email,
                    password,
                    phone_number,
                    avatar,
                    account_type
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
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
                    user.getName()
            );

            statement.setString(
                    2,
                    user.getSurname()
            );

            statement.setObject(
                    3,
                    user.getAge()
            );

            statement.setString(
                    4,
                    user.getEmail()
            );

            statement.setString(
                    5,
                    user.getPassword()
            );

            statement.setString(
                    6,
                    user.getPhoneNumber()
            );

            statement.setString(
                    7,
                    user.getAvatar()
            );

            statement.setString(
                    8,
                    user.getAccountType().name()
            );

            return statement;
        }, keyHolder);

        return Objects.requireNonNull(
                keyHolder.getKey()
        ).intValue();
    }

    public List<User> findByName(String name) {
        String sql = """
                SELECT *
                FROM users
                WHERE LOWER(name) LIKE LOWER(?)
                """;

        return jdbcTemplate.query(
                sql,
                new UserMapper(),
                "%" + name + "%"
        );
    }

    public List<User> findByPhoneNumber(
            String phoneNumber
    ) {
        String sql = """
                SELECT *
                FROM users
                WHERE phone_number LIKE ?
                """;

        return jdbcTemplate.query(
                sql,
                new UserMapper(),
                "%" + phoneNumber + "%"
        );
    }

    public Optional<User> findByEmail(
            String email
    ) {
        String sql = """
                SELECT *
                FROM users
                WHERE LOWER(email) = LOWER(?)
                """;

        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(
                                sql,
                                new UserMapper(),
                                email
                        )
                )
        );
    }

    public boolean existsByEmail(String email) {
        String sql = """
                SELECT COUNT(*)
                FROM users
                WHERE LOWER(email) = LOWER(?)
                """;

        Integer count =
                jdbcTemplate.queryForObject(
                        sql,
                        Integer.class,
                        email
                );

        return count != null && count > 0;
    }

    public List<User> findApplicants(
            String query
    ) {
        String sql = """
                SELECT *
                FROM users
                WHERE account_type = 'APPLICANT'
                  AND
                  (
                      LOWER(name) LIKE LOWER(?)
                      OR LOWER(surname) LIKE LOWER(?)
                      OR LOWER(email) LIKE LOWER(?)
                      OR phone_number LIKE ?
                  )
                """;

        String search = "%" + query + "%";

        return jdbcTemplate.query(
                sql,
                new UserMapper(),
                search,
                search,
                search,
                search
        );
    }

    public List<User> findEmployers(
            String query
    ) {
        String sql = """
                SELECT *
                FROM users
                WHERE account_type = 'EMPLOYER'
                  AND
                  (
                      LOWER(name) LIKE LOWER(?)
                      OR LOWER(surname) LIKE LOWER(?)
                      OR LOWER(email) LIKE LOWER(?)
                      OR phone_number LIKE ?
                  )
                """;

        String search = "%" + query + "%";

        return jdbcTemplate.query(
                sql,
                new UserMapper(),
                search,
                search,
                search,
                search
        );
    }

    public void updateAvatar(
            Integer userId,
            String avatar
    ) {
        String sql = """
                UPDATE users
                SET avatar = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                avatar,
                userId
        );
    }
}