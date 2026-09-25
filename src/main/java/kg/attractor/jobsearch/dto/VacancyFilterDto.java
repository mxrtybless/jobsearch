package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class VacancyFilterDto {
    @Size(max = 150)
    private String name = "";
    @Positive
    private Integer category;
    @DecimalMin("0")
    private BigDecimal salary;
    @Min(0)
    @Max(70)
    private Integer experience;
    private String sort = "dateDesc";
    @Min(1)
    private int page = 1;
}
