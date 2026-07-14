package kg.attractor.jobsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacancyUpdateDto {
    private String name;
    private String description;

    private Integer categoryId;
    private BigDecimal salary;

    private Integer expFrom;
    private Integer expTo;

    private Boolean isActive;

    private Integer authorId;
}