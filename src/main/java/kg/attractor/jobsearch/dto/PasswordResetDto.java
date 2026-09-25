package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordResetDto {
    @NotBlank
    private String token;
    @NotBlank(message = "{validation.user.password.notBlank}")
    @Size(min = 8, max = 32, message = "{validation.user.password.size}")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])\\S+$", message = "{validation.user.password.pattern}")
    private String password;
}
