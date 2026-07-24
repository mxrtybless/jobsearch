package kg.attractor.jobsearch.dao.mappers;

import kg.attractor.jobsearch.model.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper
        implements RowMapper<Customer> {

    @Override
    public Customer mapRow(
            ResultSet resultSet,
            int rowNum
    ) throws SQLException {

        Customer customer = new Customer();

        customer.setEmail(
                resultSet.getString("email")
        );

        customer.setUsername(
                resultSet.getString("username")
        );

        customer.setPassword(
                resultSet.getString("password")
        );

        return customer;
    }
}