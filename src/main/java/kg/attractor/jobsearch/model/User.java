package kg.attractor.jobsearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;

    private String name;
    private String surname;
    private Integer age;

    private String email;

    @JsonProperty(
            access = JsonProperty.Access.WRITE_ONLY
    )
    private String password;

    private String phoneNumber;
    private String avatar;

    private AccountType accountType;

    public boolean isApplicant() {
        return accountType
                == AccountType.APPLICANT;
    }

    public boolean isEmployer() {
        return accountType
                == AccountType.EMPLOYER;
    }
}