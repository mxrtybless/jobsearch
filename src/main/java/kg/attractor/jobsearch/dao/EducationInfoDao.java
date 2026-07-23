package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.dao.mappers.EducationInfoMapper;
import kg.attractor.jobsearch.model.EducationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EducationInfoDao {
    private final JdbcTemplate jdbcTemplate;

    public List<EducationInfo> findByResumeId(
            Integer resumeId
    ) {
        String sql = """
                SELECT *
                FROM education_info
                WHERE resume_id = ?
                ORDER BY start_date DESC
                """;

        return jdbcTemplate.query(
                sql,
                new EducationInfoMapper(),
                resumeId
        );
    }

    public void save(
            EducationInfo educationInfo
    ) {
        String sql = """
                INSERT INTO education_info
                (
                    resume_id,
                    institution,
                    program,
                    start_date,
                    end_date,
                    degree
                )
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                educationInfo.getResumeId(),
                educationInfo.getInstitution(),
                educationInfo.getProgram(),
                educationInfo.getStartDate(),
                educationInfo.getEndDate(),
                educationInfo.getDegree()
        );
    }

    public void deleteByResumeId(
            Integer resumeId
    ) {
        String sql = """
                DELETE FROM education_info
                WHERE resume_id = ?
                """;

        jdbcTemplate.update(
                sql,
                resumeId
        );
    }
}