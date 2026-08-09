package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.ContactType;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ContactTypeDao {

    private final JdbcTemplate jdbcTemplate;

    public Optional<ContactType> findById(
            Integer id
    ) {
        String sql = """
                SELECT id, type
                FROM contact_types
                WHERE id = ?
                """;

        ContactType contactType =
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(
                                sql,
                                (rs, rowNum) ->
                                        ContactType.builder()
                                                .id(
                                                        rs.getInt(
                                                                "id"
                                                        )
                                                )
                                                .type(
                                                        rs.getString(
                                                                "type"
                                                        )
                                                )
                                                .build(),
                                id
                        )
                );

        return Optional.ofNullable(contactType);
    }

    public List<ContactType> findAll() {
        String sql = """
                SELECT id, type
                FROM contact_types
                ORDER BY id
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        ContactType.builder()
                                .id(
                                        rs.getInt(
                                                "id"
                                        )
                                )
                                .type(
                                        rs.getString(
                                                "type"
                                        )
                                )
                                .build()
        );
    }
}