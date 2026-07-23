package kg.attractor.jobsearch.dao.mappers;

import kg.attractor.jobsearch.model.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setUsername(rs.getString("name"));
        customer.setPassword(rs.getString("password"));
        return customer;
    }
}
