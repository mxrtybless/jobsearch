package kg.attractor.jobsearch.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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

    @NotNull(
            message = "Applicant id must be specified"
    )
    @Positive(
            message = "Applicant id must be positive"
    )
    private Integer applicantId;

    @NotBlank(
            message = "Resume name must not be blank"
    )
    @Size(
            min = 2,
            max = 100,
            message = "Resume name length must be between 2 and 100 characters"
    )
    private String name;

    @NotNull(
            message = "Category id must be specified"
    )
    @Positive(
            message = "Category id must be positive"
    )
    private Integer categoryId;

    @NotNull(
            message = "Salary must be specified"
    )
    @Positive(
            message = "Salary must be positive"
    )
    private BigDecimal salary;

    private Boolean isActive;

    private LocalDateTime createdDate;
    private LocalDateTime updateTime;

    @Valid
    @Size(
            max = 10,
            message = "Resume must contain no more than 10 education records"
    )
    private List<EducationInfo> educationInfo =
            new ArrayList<>();

    @Valid
    @Size(
            max = 20,
            message = "Resume must contain no more than 20 work experience records"
    )
    private List<WorkExperienceInfo>
            workExperienceInfo =
            new ArrayList<>();
}