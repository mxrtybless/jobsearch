package kg.attractor.jobsearch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(
            name = "name",
            nullable = false,
            length = 100
    )
    private String name;

    @Column(
            name = "surname",
            length = 100
    )
    private String surname;

    @Column(name = "age")
    private Integer age;

    @Column(
            name = "email",
            nullable = false,
            unique = true,
            length = 255
    )
    private String email;

    @JsonProperty(
            access = JsonProperty.Access.WRITE_ONLY
    )
    @Column(
            name = "password",
            nullable = false,
            length = 255
    )
    private String password;

    @Column(
            name = "phone_number",
            nullable = false,
            length = 50
    )
    private String phoneNumber;

    @Column(
            name = "avatar",
            length = 255
    )
    private String avatar;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "account_type",
            nullable = false,
            length = 20
    )
    private AccountType accountType;

    @JsonIgnore
    @Column(
            name = "enabled",
            nullable = false
    )
    private Boolean enabled;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(
            name = "role_id",
            nullable = false
    )
    private Role role;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @OneToMany(
            mappedBy = "applicant",
            fetch = FetchType.LAZY
    )
    private List<Resume> resumes =
            new ArrayList<>();

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @OneToMany(
            mappedBy = "author",
            fetch = FetchType.LAZY
    )
    private List<Vacancy> vacancies =
            new ArrayList<>();

    public boolean isApplicant() {
        return accountType
                == AccountType.APPLICANT;
    }

    public boolean isEmployer() {
        return accountType
                == AccountType.EMPLOYER;
    }
}