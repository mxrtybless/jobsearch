package kg.attractor.jobsearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespondedApplicantDto {

    @JsonProperty(
            access = JsonProperty.Access.READ_ONLY
    )
    private Integer id;

    @NotNull(
            message = "Resume id must be specified"
    )
    @Positive(
            message = "Resume id must be positive"
    )
    private Integer resumeId;

    @NotNull(
            message = "Vacancy id must be specified"
    )
    @Positive(
            message = "Vacancy id must be positive"
    )
    private Integer vacancyId;

    @JsonProperty(
            access = JsonProperty.Access.READ_ONLY
    )
    private Boolean confirmation;
}