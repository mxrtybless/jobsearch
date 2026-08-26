package kg.attractor.jobsearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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

    @JsonProperty(
            access = JsonProperty.Access.READ_ONLY
    )
    private Integer id;

    @JsonProperty(
            access = JsonProperty.Access.READ_ONLY
    )
    private Integer applicantId;

    @NotBlank(
            message = "Resume name must not be blank"
    )
    @Size(
            min = 2,
            max = 150,
            message = "Resume name length must be between 2 and 150 characters"
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

    @NotNull(
            message = "Active status must be specified"
    )
    private Boolean isActive;

    @JsonProperty(
            access = JsonProperty.Access.READ_ONLY
    )
    private LocalDateTime createdDate;

    @JsonProperty(
            access = JsonProperty.Access.READ_ONLY
    )
    private LocalDateTime updateTime;

    @Valid
    @Size(
            min = 1,
            max = 1,
            message = "Resume must contain exactly one education record"
    )
    private List<EducationInfoDto> educationInfo =
            new ArrayList<>();

    @Valid
    @Size(
            min = 1,
            max = 1,
            message = "Resume must contain exactly one work experience record"
    )
    private List<WorkExperienceInfoDto>
            workExperienceInfo =
            new ArrayList<>();

    @Valid
    private List<ContactInfoDto> contactInfo =
            new ArrayList<>();
}