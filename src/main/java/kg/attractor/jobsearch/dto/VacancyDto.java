package kg.attractor.jobsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacancyDto {
    private Integer id;

    private String name;
    private String description;

    private Integer categoryId;
    private String categoryName;

    private BigDecimal salary;

    private Integer expFrom;
    private Integer expTo;

    private Boolean isActive;

    private Integer authorId;

    private LocalDateTime createdDate;
    private LocalDateTime updateTime;
}