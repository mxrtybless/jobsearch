package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.Email;
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
            min = 2,
            max = 100,
            message = "Surname length must be between 2 and 100 characters"
    )
    @Pattern(
            regexp = "^[\\p{L}][\\p{L}\\s'-]*$",
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

    @Pattern(
            regexp = "^(?!\\s*$).+",
            message = "Email must not be blank"
    )
    @Email(
            message = "Email must have a valid format"
    )
    @Size(
            max = 255,
            message = "Email must not be longer than 255 characters"
    )
    private String email;

    @Size(
            min = 8,
            max = 32,
            message = "Password length must be between 8 and 32 characters"
    )
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])\\S+$",
            message = "Password must contain uppercase and lowercase letters, a number and no spaces"
    )
    private String password;

    @Pattern(
            regexp = "^\\+996\\d{9}$",
            message = "Phone number must have format +996XXXXXXXXX"
    )
    private String phoneNumber;
}