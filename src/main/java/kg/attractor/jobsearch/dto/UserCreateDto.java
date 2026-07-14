package kg.attractor.jobsearch.dto;

import kg.attractor.jobsearch.model.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
    private String name;
    private String surname;
    private Integer age;

    private String email;
    private String password;
    private String phoneNumber;

    private AccountType accountType;
}