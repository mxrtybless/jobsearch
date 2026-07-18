package kg.attractor.jobsearch.dao.mappers;

import kg.attractor.jobsearch.model.AccountType;
import kg.attractor.jobsearch.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setAge(rs.getInt("age"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setAvatar(rs.getString("avatar"));

        String accountType = rs.getString("account_type");
        user.setAccountType(AccountType.valueOf(accountType.toUpperCase()));

        return user;
    }
}