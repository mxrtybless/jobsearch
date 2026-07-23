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
        String sql = "select * from customer;";
        return jdbcTemplate.query(sql, new CustomerMapper());
    }

    public Optional<Customer> findById(Integer id) {
        String sql = "select * from customer " +
                "where id = ?";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(sql, new CustomerMapper(), id)
                )
        );
    }

    public void save(Customer customer) {
        String sql = "insert into customer (name, password) " +
                "values(:name, :password)";
        namedParameterJdbcTemplate.update(
                sql,
                new MapSqlParameterSource()
                        .addValue("name", customer.getUsername())
                        .addValue("password", customer.getPassword())
        );
    }

    public Integer saveAndReturnId(Customer customer) {
        String sql = "insert into customer (name, password) " +
                "values(?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customer.getUsername());
            ps.setString(2, customer.getPassword());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }
}
