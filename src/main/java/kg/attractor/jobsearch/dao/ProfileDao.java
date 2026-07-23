package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.dao.mappers.UserMapper;
import kg.attractor.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProfileDao {
    private final JdbcTemplate jdbcTemplate;

    public Optional<User> findById(Integer id) {
        String sql = """
                SELECT *
                FROM users
                WHERE id = ?
                """;

        User user = DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new UserMapper(),
                        id
                )
        );

        return Optional.ofNullable(user);
    }

    public void update(User user) {
        String sql = """
                UPDATE users
                SET name = ?,
                    surname = ?,
                    age = ?,
                    email = ?,
                    password = ?,
                    phone_number = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                user.getName(),
                user.getSurname(),
                user.getAge(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getId()
        );
    }
}