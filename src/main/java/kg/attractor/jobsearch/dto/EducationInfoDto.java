package kg.attractor.jobsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationInfoDto {
    private Integer id;

    private Integer resumeId;

    private String institution;
    private String program;

    private LocalDate startDate;
    private LocalDate endDate;

    private String degree;
}