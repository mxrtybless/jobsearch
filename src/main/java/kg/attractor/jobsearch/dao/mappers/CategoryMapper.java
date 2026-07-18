package kg.attractor.jobsearch.dao.mappers;

import kg.attractor.jobsearch.model.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements RowMapper<Category> {

    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        Category category = new Category();

        category.setId(rs.getInt("id"));
        category.setName(rs.getString("name"));
        category.setParentId(
                rs.getObject("parent_id", Integer.class)
        );

        return category;
    }
}