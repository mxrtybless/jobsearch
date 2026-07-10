package kg.attractor.jobsearch.model;

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
public class Vacancy {
    private Integer id;

    private String name;
    private String description;

    private Integer categoryId;
    private BigDecimal salary;

    private Integer expFrom;
    private Integer expTo;

    private Boolean isActive;

    private Integer authorId;

    private LocalDateTime createdDate;
    private LocalDateTime updateTime;

    public boolean isPublished() {
        return Boolean.TRUE.equals(isActive);
    }
}