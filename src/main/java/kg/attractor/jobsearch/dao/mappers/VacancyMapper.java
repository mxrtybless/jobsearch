package kg.attractor.jobsearch.dao.mappers;

import kg.attractor.jobsearch.model.Vacancy;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VacancyMapper implements RowMapper<Vacancy> {

    @Override
    public Vacancy mapRow(
            ResultSet rs,
            int rowNum
    ) throws SQLException {

        Vacancy vacancy = new Vacancy();

        vacancy.setId(rs.getInt("id"));
        vacancy.setName(rs.getString("name"));
        vacancy.setDescription(
                rs.getString("description")
        );
        vacancy.setCategoryId(
                rs.getInt("category_id")
        );
        vacancy.setSalary(
                rs.getBigDecimal("salary")
        );
        vacancy.setExpFrom(
                rs.getInt("exp_from")
        );
        vacancy.setExpTo(
                rs.getInt("exp_to")
        );
        vacancy.setIsActive(
                rs.getBoolean("is_active")
        );
        vacancy.setAuthorId(
                rs.getInt("author_id")
        );

        vacancy.setCreatedDate(
                rs.getTimestamp("created_date")
                        .toLocalDateTime()
        );

        vacancy.setUpdateTime(
                rs.getTimestamp("update_time")
                        .toLocalDateTime()
        );

        return vacancy;
    }
}