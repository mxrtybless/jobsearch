package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.dao.mappers.UserMapper;
import kg.attractor.jobsearch.model.User;
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
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<User> findAll() {
        String sql = """
                SELECT id,
                       name,
                       surname,
                       age,
                       email,
                       password,
                       phone_number,
                       avatar,
                       account_type
                FROM users
                ORDER BY id
                """;

        return jdbcTemplate.query(sql, new UserMapper());
    }

    public Optional<User> findById(Integer id) {
        String sql = """
                SELECT id,
                       name,
                       surname,
                       age,
                       email,
                       password,
                       phone_number,
                       avatar,
                       account_type
                FROM users
                WHERE id = ?
                """;

        List<User> users = jdbcTemplate.query(
                sql,
                new UserMapper(),
                id
        );

        return Optional.ofNullable(
                DataAccessUtils.singleResult(users)
        );
    }

    public List<User> findByName(String name) {
        String sql = """
                SELECT id,
                       name,
                       surname,
                       age,
                       email,
                       password,
                       phone_number,
                       avatar,
                       account_type
                FROM users
                WHERE LOWER(name) LIKE LOWER(?)
                ORDER BY name
                """;

        return jdbcTemplate.query(
                sql,
                new UserMapper(),
                "%" + name + "%"
        );
    }

    public List<User> findByPhoneNumber(String phoneNumber) {
        String sql = """
                SELECT id,
                       name,
                       surname,
                       age,
                       email,
                       password,
                       phone_number,
                       avatar,
                       account_type
                FROM users
                WHERE phone_number LIKE ?
                ORDER BY id
                """;

        return jdbcTemplate.query(
                sql,
                new UserMapper(),
                "%" + phoneNumber + "%"
        );
    }

    public Optional<User> findByEmail(String email) {
        String sql = """
                SELECT id,
                       name,
                       surname,
                       age,
                       email,
                       password,
                       phone_number,
                       avatar,
                       account_type
                FROM users
                WHERE LOWER(email) = LOWER(?)
                """;

        List<User> users = jdbcTemplate.query(
                sql,
                new UserMapper(),
                email
        );

        return Optional.ofNullable(
                DataAccessUtils.singleResult(users)
        );
    }

    public boolean existsByEmail(String email) {
        String sql = """
                SELECT COUNT(*)
                FROM users
                WHERE LOWER(email) = LOWER(?)
                """;

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                email
        );

        return count != null && count > 0;
    }

    public User save(User user) {
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

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setInt(3, user.getAge());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getPhoneNumber());
            statement.setString(7, user.getAvatar());
            statement.setString(8, user.getAccountType().name());

            return statement;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();

        if (generatedId != null) {
            user.setId(generatedId.intValue());
        }

        return user;
    }
}