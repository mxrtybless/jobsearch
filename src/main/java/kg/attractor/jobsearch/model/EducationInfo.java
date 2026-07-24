package kg.attractor.jobsearch.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationInfo {
    private Integer id;

    private Integer resumeId;

    @NotBlank(
            message = "Institution must not be blank"
    )
    @Size(
            min = 2,
            max = 255,
            message = "Institution length must be between 2 and 255 characters"
    )
    private String institution;

    @NotBlank(
            message = "Program must not be blank"
    )
    @Size(
            min = 2,
            max = 255,
            message = "Program length must be between 2 and 255 characters"
    )
    private String program;

    @NotNull(
            message = "Education start date must be specified"
    )
    @PastOrPresent(
            message = "Education start date must not be in the future"
    )
    private LocalDate startDate;

    @PastOrPresent(
            message = "Education end date must not be in the future"
    )
    private LocalDate endDate;

    @NotBlank(
            message = "Degree must not be blank"
    )
    @Size(
            min = 2,
            max = 100,
            message = "Degree length must be between 2 and 100 characters"
    )
    private String degree;
}