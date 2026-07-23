package kg.attractor.jobsearch.dto;

import kg.attractor.jobsearch.model.EducationInfo;
import kg.attractor.jobsearch.model.WorkExperienceInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDto {
    private Integer id;
    private Integer applicantId;

    private String name;
    private Integer categoryId;
    private BigDecimal salary;

    private Boolean isActive;

    private LocalDateTime createdDate;
    private LocalDateTime updateTime;

    private List<EducationInfo> educationInfo =
            new ArrayList<>();

    private List<WorkExperienceInfo>
            workExperienceInfo =
            new ArrayList<>();
}