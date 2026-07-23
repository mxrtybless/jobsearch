package kg.attractor.jobsearch.dto;

import lombok.Data;

@Data
public class ProfileUpdateDto {
    private String name;
    private String surname;
    private Integer age;

    private String email;
    private String password;
    private String phoneNumber;
}