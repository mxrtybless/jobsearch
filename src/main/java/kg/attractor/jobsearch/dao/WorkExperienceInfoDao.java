package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.dao.mappers.WorkExperienceInfoMapper;
import kg.attractor.jobsearch.model.WorkExperienceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkExperienceInfoDao {
    private final JdbcTemplate jdbcTemplate;

    public List<WorkExperienceInfo>
    findByResumeId(
            Integer resumeId
    ) {
        String sql = """
                SELECT *
                FROM work_experience_info
                WHERE resume_id = ?
                ORDER BY years DESC
                """;

        return jdbcTemplate.query(
                sql,
                new WorkExperienceInfoMapper(),
                resumeId
        );
    }

    public void save(
            WorkExperienceInfo workExperience
    ) {
        String sql = """
                INSERT INTO work_experience_info
                (
                    resume_id,
                    years,
                    company_name,
                    position,
                    responsibilities
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                workExperience.getResumeId(),
                workExperience.getYears(),
                workExperience.getCompanyName(),
                workExperience.getPosition(),
                workExperience.getResponsibilities()
        );
    }

    public void deleteByResumeId(
            Integer resumeId
    ) {
        String sql = """
                DELETE FROM work_experience_info
                WHERE resume_id = ?
                """;

        jdbcTemplate.update(
                sql,
                resumeId
        );
    }
}