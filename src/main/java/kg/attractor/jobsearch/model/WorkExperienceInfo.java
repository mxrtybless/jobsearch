package kg.attractor.jobsearch.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperienceInfo {
    private Integer id;

    private Integer resumeId;

    @NotNull(
            message = "Years of experience must be specified"
    )
    @Min(
            value = 0,
            message = "Years of experience must not be negative"
    )
    @Max(
            value = 70,
            message = "Years of experience must not be greater than 70"
    )
    private Integer years;

    @NotBlank(
            message = "Company name must not be blank"
    )
    @Size(
            min = 2,
            max = 255,
            message = "Company name length must be between 2 and 255 characters"
    )
    private String companyName;

    @NotBlank(
            message = "Position must not be blank"
    )
    @Size(
            min = 2,
            max = 255,
            message = "Position length must be between 2 and 255 characters"
    )
    private String position;

    @NotBlank(
            message = "Responsibilities must not be blank"
    )
    @Size(
            max = 2000,
            message = "Responsibilities must not be longer than 2000 characters"
    )
    private String responsibilities;
}