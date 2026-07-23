package kg.attractor.jobsearch.dao.mappers;

import kg.attractor.jobsearch.model.WorkExperienceInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkExperienceInfoMapper
        implements RowMapper<WorkExperienceInfo> {

    @Override
    public WorkExperienceInfo mapRow(
            ResultSet rs,
            int rowNum
    ) throws SQLException {

        WorkExperienceInfo workExperience =
                new WorkExperienceInfo();

        workExperience.setId(
                rs.getInt("id")
        );

        workExperience.setResumeId(
                rs.getInt("resume_id")
        );

        workExperience.setYears(
                rs.getInt("years")
        );

        workExperience.setCompanyName(
                rs.getString("company_name")
        );

        workExperience.setPosition(
                rs.getString("position")
        );

        workExperience.setResponsibilities(
                rs.getString("responsibilities")
        );

        return workExperience;
    }
}