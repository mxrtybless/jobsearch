package kg.attractor.jobsearch.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespondedApplicant {
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

    private Boolean confirmation;

    public boolean isConfirmed() {
        return Boolean.TRUE.equals(
                confirmation
        );
    }
}