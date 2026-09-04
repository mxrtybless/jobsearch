package kg.attractor.jobsearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperienceInfoDto {

    @JsonProperty(
            access = JsonProperty.Access.READ_ONLY
    )
    private Integer id;

    @JsonProperty(
            access = JsonProperty.Access.READ_ONLY
    )
    private Integer resumeId;

    @NotNull(
            message = "{validation.work.years.notNull}"
    )
    @Min(
            value = 0,
            message = "{validation.work.years.min}"
    )
    @Max(
            value = 70,
            message = "{validation.work.years.max}"
    )
    private Integer years;

    @NotBlank(
            message = "{validation.work.company.notBlank}"
    )
    @Size(
            min = 2,
            max = 255,
            message = "{validation.work.company.size}"
    )
    private String companyName;

    @NotBlank(
            message = "{validation.work.position.notBlank}"
    )
    @Size(
            min = 2,
            max = 255,
            message = "{validation.work.position.size}"
    )
    private String position;

    @NotBlank(
            message = "{validation.work.responsibilities.notBlank}"
    )
    @Size(
            max = 2000,
            message = "{validation.work.responsibilities.size}"
    )
    private String responsibilities;
}