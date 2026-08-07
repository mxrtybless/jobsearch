package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.ContactInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ContactInfoDao {

    private final JdbcTemplate jdbcTemplate;

    public List<ContactInfo> findByResumeId(
            Integer resumeId
    ) {
        String sql = """
                SELECT id, type_id, resume_id, "VALUE" AS value
                FROM contacts_info
                WHERE resume_id = ?
                ORDER BY id
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        ContactInfo.builder()
                                .id(
                                        rs.getInt(
                                                "id"
                                        )
                                )
                                .typeId(
                                        rs.getInt(
                                                "type_id"
                                        )
                                )
                                .resumeId(
                                        rs.getInt(
                                                "resume_id"
                                        )
                                )
                                .value(
                                        rs.getString(
                                                "value"
                                        )
                                )
                                .build(),
                resumeId
        );
    }

    public void save(
            ContactInfo contactInfo
    ) {
        String sql = """
                INSERT INTO contacts_info
                (
                    type_id,
                    resume_id,
                    "VALUE"
                )
                VALUES (?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                contactInfo.getTypeId(),
                contactInfo.getResumeId(),
                contactInfo.getValue()
        );
    }

    public void deleteByResumeId(
            Integer resumeId
    ) {
        String sql = """
                DELETE FROM contacts_info
                WHERE resume_id = ?
                """;

        jdbcTemplate.update(
                sql,
                resumeId
        );
    }
}