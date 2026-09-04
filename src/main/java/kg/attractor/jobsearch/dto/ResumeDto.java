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
            message = "{validation.resume.name.notBlank}"
    )
    @Size(
            min = 2,
            max = 150,
            message = "{validation.resume.name.size}"
    )
    private String name;

    @NotNull(
            message = "{validation.resume.category.notNull}"
    )
    @Positive(
            message = "{validation.resume.category.positive}"
    )
    private Integer categoryId;

    @NotNull(
            message = "{validation.resume.salary.notNull}"
    )
    @Positive(
            message = "{validation.resume.salary.positive}"
    )
    private BigDecimal salary;

    @NotNull(
            message = "{validation.resume.active.notNull}"
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
            message = "{validation.resume.education.size}"
    )
    private List<EducationInfoDto> educationInfo =
            new ArrayList<>();

    @Valid
    @Size(
            min = 1,
            max = 1,
            message = "{validation.resume.work.size}"
    )
    private List<WorkExperienceInfoDto>
            workExperienceInfo =
            new ArrayList<>();

    @Valid
    private List<ContactInfoDto> contactInfo =
            new ArrayList<>();
}