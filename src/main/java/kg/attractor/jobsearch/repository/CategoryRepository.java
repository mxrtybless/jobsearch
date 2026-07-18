package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.dao.mappers.CategoryMapper;
import kg.attractor.jobsearch.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Category> findAll() {
        String sql = """
                SELECT id,
                       name,
                       parent_id
                FROM categories
                ORDER BY id
                """;

        return jdbcTemplate.query(
                sql,
                new CategoryMapper()
        );
    }

    public Optional<Category> findById(Integer id) {
        String sql = """
                SELECT id,
                       name,
                       parent_id
                FROM categories
                WHERE id = ?
                """;

        List<Category> categories = jdbcTemplate.query(
                sql,
                new CategoryMapper(),
                id
        );

        return Optional.ofNullable(
                DataAccessUtils.singleResult(categories)
        );
    }
}