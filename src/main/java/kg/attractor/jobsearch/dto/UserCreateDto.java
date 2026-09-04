package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import kg.attractor.jobsearch.model.AccountType;
import lombok.Data;

@Data
public class UserCreateDto {

    @NotBlank(
            message = "{validation.user.name.notBlank}"
    )
    @Size(
            min = 2,
            max = 100,
            message = "{validation.user.name.size}"
    )
    @Pattern(
            regexp = "^[\\p{L}][\\p{L}\\s'-]*$",
            message = "{validation.user.name.pattern}"
    )
    private String name;

    @Size(
            max = 100,
            message = "{validation.user.surname.size}"
    )
    @Pattern(
            regexp = "^$|^[\\p{L}][\\p{L}\\s'-]{1,99}$",
            message = "{validation.user.surname.pattern}"
    )
    private String surname;

    @NotNull(
            message = "{validation.user.age.notNull}"
    )
    @Min(
            value = 18,
            message = "{validation.user.age.min}"
    )
    @Max(
            value = 100,
            message = "{validation.user.age.max}"
    )
    private Integer age;

    @NotBlank(
            message = "{validation.user.email.notBlank}"
    )
    @Email(
            message = "{validation.user.email.format}"
    )
    @Size(
            max = 255,
            message = "{validation.user.email.size}"
    )
    private String email;

    @NotBlank(
            message = "{validation.user.password.notBlank}"
    )
    @Size(
            min = 8,
            max = 32,
            message = "{validation.user.password.size}"
    )
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])\\S+$",
            message = "{validation.user.password.pattern}"
    )
    private String password;

    @NotBlank(
            message = "{validation.user.phone.notBlank}"
    )
    @Pattern(
            regexp = "^\\+996\\d{9}$",
            message = "{validation.user.phone.pattern}"
    )
    private String phoneNumber;

    @NotNull(
            message = "{validation.user.accountType.notNull}"
    )
    private AccountType accountType;
}