package kg.attractor.jobsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeUpdateDto {
    private Integer applicantId;

    private String name;
    private Integer categoryId;
    private BigDecimal salary;

    private Boolean isActive;

    private List<ContactInfoDto> contacts;
    private List<EducationInfoDto> educations;
    private List<WorkExperienceInfoDto> workExperiences;
}