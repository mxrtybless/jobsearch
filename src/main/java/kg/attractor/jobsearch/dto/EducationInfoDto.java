package kg.attractor.jobsearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EducationInfoDto {

    @JsonProperty(
            access = JsonProperty.Access.READ_ONLY
    )
    private Integer id;

    @JsonProperty(
            access = JsonProperty.Access.READ_ONLY
    )
    private Integer resumeId;

    @NotBlank(
            message = "{validation.education.institution.notBlank}"
    )
    @Size(
            min = 2,
            max = 255,
            message = "{validation.education.institution.size}"
    )
    private String institution;

    @NotBlank(
            message = "{validation.education.program.notBlank}"
    )
    @Size(
            min = 2,
            max = 255,
            message = "{validation.education.program.size}"
    )
    private String program;

    @NotNull(
            message = "{validation.education.startDate.notNull}"
    )
    @PastOrPresent(
            message = "{validation.education.startDate.pastOrPresent}"
    )
    private LocalDate startDate;

    @PastOrPresent(
            message = "{validation.education.endDate.pastOrPresent}"
    )
    private LocalDate endDate;

    @NotBlank(
            message = "{validation.education.degree.notBlank}"
    )
    @Size(
            min = 2,
            max = 100,
            message = "{validation.education.degree.size}"
    )
    private String degree;
}