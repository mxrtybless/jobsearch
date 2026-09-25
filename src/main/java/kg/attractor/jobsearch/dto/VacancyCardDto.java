package kg.attractor.jobsearch.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class VacancyCardDto {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal salary;
    private Integer expFrom;
    private Integer expTo;
    private LocalDateTime createdDate;
    private LocalDateTime updateTime;
    private Integer authorId;
    private String authorName;
    private String categoryName;
}
