package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.dao.mappers.CustomerMapper;
import kg.attractor.jobsearch.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
public class CustomerDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public List<Customer> findAll() {
        String sql = "select * from customers;";
        return jdbcTemplate.query(sql, new CustomerMapper());
    }

    public Optional<Customer> findById(String email) {
        String sql = "select * from customers " +
                "where email = ?";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(sql, new CustomerMapper(), email)
                )
        );
    }

    public void save(Customer customer) {
        String sql = "insert into customers (email, username, password, enabled, role_id) " +
                "values(:email, :name, :password, :enabled, :role_id)";

        namedParameterJdbcTemplate.update(
                sql,
                new MapSqlParameterSource()
                        .addValue("email", customer.getEmail())
                        .addValue("name", customer.getUsername())
                        .addValue("password", customer.getPassword())
                        .addValue("enabled", customer.getEnabled())
                        .addValue("role_id", 3)
        );
    }

    public Integer saveAndReturnId(Customer customer) {
        String sql = "insert into customers (email, username, password) " +
                "values(?, ?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customer.getEmail());
            ps.setString(2, customer.getUsername());
            ps.setString(3, customer.getPassword());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }
}