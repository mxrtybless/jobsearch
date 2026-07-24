package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.dao.mappers.CustomerMapper;
import kg.attractor.jobsearch.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerDao {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate
            namedParameterJdbcTemplate;

    public List<Customer> findAll() {
        String sql = """
                SELECT email,
                       username,
                       password
                FROM customers
                """;

        return jdbcTemplate.query(
                sql,
                new CustomerMapper()
        );
    }

    public Optional<Customer> findById(
            String email
    ) {
        String sql = """
                SELECT email,
                       username,
                       password
                FROM customers
                WHERE email = ?
                """;

        Customer customer =
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(
                                sql,
                                new CustomerMapper(),
                                email
                        )
                );

        return Optional.ofNullable(customer);
    }

    public void save(Customer customer) {
        String sql = """
                INSERT INTO customers
                (
                    email,
                    username,
                    password
                )
                VALUES
                (
                    :email,
                    :username,
                    :password
                )
                """;

        MapSqlParameterSource parameters =
                new MapSqlParameterSource()
                        .addValue(
                                "email",
                                customer.getEmail()
                        )
                        .addValue(
                                "username",
                                customer.getUsername()
                        )
                        .addValue(
                                "password",
                                customer.getPassword()
                        );

        namedParameterJdbcTemplate.update(
                sql,
                parameters
        );
    }
}