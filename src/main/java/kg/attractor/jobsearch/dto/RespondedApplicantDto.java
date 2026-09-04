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
            message = "{validation.response.resumeId.notNull}"
    )
    @Positive(
            message = "{validation.response.resumeId.positive}"
    )
    private Integer resumeId;

    @NotNull(
            message = "{validation.response.vacancyId.notNull}"
    )
    @Positive(
            message = "{validation.response.vacancyId.positive}"
    )
    private Integer vacancyId;

    @JsonProperty(
            access = JsonProperty.Access.READ_ONLY
    )
    private Boolean confirmation;
}