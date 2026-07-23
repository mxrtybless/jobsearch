package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MovieDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Movie> findAll() {
        String sql = "select * from movie";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Movie.class));
    }

    public Optional<Movie> findById(Long movieId) {
        String sql = "select * from movie where id = ?";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(
                                sql,
                                new BeanPropertyRowMapper<>(Movie.class),
                                movieId
                        )
                )
        );
    }

    public void save(Movie movie) {
        String sql = "insert into movie(name, release_year, description, director_id) " +
                "values (:name, :release_year, :description, (select id from director where id = :director_id))";
        namedParameterJdbcTemplate.update(
                sql,
                new MapSqlParameterSource()
                        .addValue("name", movie.getName())
                        .addValue("release_year", movie.getReleaseYear())
                        .addValue("description", movie.getDescription())
                        .addValue("director_id", movie.getDirectorId())
        );
    }
}
