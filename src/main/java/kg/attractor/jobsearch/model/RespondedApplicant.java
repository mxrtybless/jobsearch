package kg.attractor.jobsearch.model;

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

    private Integer resumeId;
    private Integer vacancyId;

    private Boolean confirmation;

    public boolean isConfirmed() {
        return Boolean.TRUE.equals(confirmation);
    }
}