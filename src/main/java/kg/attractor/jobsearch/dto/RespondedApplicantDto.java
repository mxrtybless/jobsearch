package kg.attractor.jobsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespondedApplicantDto {
    private Integer id;

    private Integer resumeId;
    private String resumeName;

    private Integer applicantId;
    private String applicantName;

    private Integer vacancyId;
    private String vacancyName;

    private Integer employerId;
    private String employerName;

    private Boolean confirmation;
}