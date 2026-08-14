package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProfileUpdateDto {

    @Size(
            min = 2,
            max = 100,
            message = "Name length must be between 2 and 100 characters"
    )
    @Pattern(
            regexp = "^[\\p{L}][\\p{L}\\s'-]*$",
            message = "Name must contain only letters, spaces, apostrophes and hyphens"
    )
    private String name;

    @Size(
            max = 100,
            message = "Surname must not be longer than 100 characters"
    )
    @Pattern(
            regexp = "^$|^[\\p{L}][\\p{L}\\s'-]{1,99}$",
            message = "Surname must contain only letters, spaces, apostrophes and hyphens"
    )
    private String surname;

    @Min(
            value = 18,
            message = "Age must be at least 18"
    )
    @Max(
            value = 100,
            message = "Age must not be greater than 100"
    )
    private Integer age;
}