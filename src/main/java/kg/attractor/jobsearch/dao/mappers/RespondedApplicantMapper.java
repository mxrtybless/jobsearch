package kg.attractor.jobsearch.dao.mappers;

import kg.attractor.jobsearch.model.RespondedApplicant;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RespondedApplicantMapper
        implements RowMapper<RespondedApplicant> {

    @Override
    public RespondedApplicant mapRow(
            ResultSet rs,
            int rowNum
    ) throws SQLException {

        RespondedApplicant respondedApplicant =
                new RespondedApplicant();

        respondedApplicant.setId(rs.getInt("id"));
        respondedApplicant.setResumeId(
                rs.getInt("resume_id")
        );
        respondedApplicant.setVacancyId(
                rs.getInt("vacancy_id")
        );
        respondedApplicant.setConfirmation(
                rs.getBoolean("confirmation")
        );

        return respondedApplicant;
    }
}