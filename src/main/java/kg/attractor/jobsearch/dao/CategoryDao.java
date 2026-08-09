package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryDao {

    private final JdbcTemplate jdbcTemplate;

    public Optional<Category> findById(
            Integer id
    ) {
        String sql = """
                SELECT id, name, parent_id
                FROM categories
                WHERE id = ?
                """;

        Category category =
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(
                                sql,
                                (rs, rowNum) ->
                                        Category.builder()
                                                .id(
                                                        rs.getInt(
                                                                "id"
                                                        )
                                                )
                                                .name(
                                                        rs.getString(
                                                                "name"
                                                        )
                                                )
                                                .parentId(
                                                        (Integer)
                                                                rs.getObject(
                                                                        "parent_id"
                                                                )
                                                )
                                                .build(),
                                id
                        )
                );

        return Optional.ofNullable(category);
    }

    public List<Category> findAll() {
        String sql = """
                SELECT id, name, parent_id
                FROM categories
                ORDER BY id
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        Category.builder()
                                .id(
                                        rs.getInt(
                                                "id"
                                        )
                                )
                                .name(
                                        rs.getString(
                                                "name"
                                        )
                                )
                                .parentId(
                                        (Integer)
                                                rs.getObject(
                                                        "parent_id"
                                                )
                                )
                                .build()
        );
    }
}