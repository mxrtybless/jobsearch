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
            message = "{validation.profile.name.size}"
    )
    @Pattern(
            regexp = "^[\\p{L}][\\p{L}\\s'-]*$",
            message = "{validation.profile.name.pattern}"
    )
    private String name;

    @Size(
            max = 100,
            message = "{validation.profile.surname.size}"
    )
    @Pattern(
            regexp = "^$|^[\\p{L}][\\p{L}\\s'-]{1,99}$",
            message = "{validation.profile.surname.pattern}"
    )
    private String surname;

    @Min(
            value = 18,
            message = "{validation.profile.age.min}"
    )
    @Max(
            value = 100,
            message = "{validation.profile.age.max}"
    )
    private Integer age;
}