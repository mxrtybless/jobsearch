package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.Director;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DirectorDao {
    private final JdbcTemplate jdbcTemplate;

    public Optional<Director> findById(Long id) {
        String sql = "select * from director where id = ?";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Director.class), id)
                )
        );
    }
}
