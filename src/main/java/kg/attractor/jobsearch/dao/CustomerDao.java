package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.dao.mappers.CustomerMapper;
import kg.attractor.jobsearch.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor

public class CustomerDao {
    private final JdbcTemplate jdbcTemplate;

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

}
