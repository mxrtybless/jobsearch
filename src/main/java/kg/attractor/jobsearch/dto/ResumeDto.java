package kg.attractor.jobsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDto {
    private Integer id;

    private Integer applicantId;

    private String name;
    private Integer categoryId;
    private String categoryName;

    private BigDecimal salary;
    private Boolean isActive;

    private LocalDateTime createdDate;
    private LocalDateTime updateTime;

    private List<ContactInfoDto> contacts;
    private List<EducationInfoDto> educations;
    private List<WorkExperienceInfoDto> workExperiences;
}