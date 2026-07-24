package kg.attractor.jobsearch.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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

    @NotBlank(
            message = "Vacancy name must not be blank"
    )
    @Size(
            min = 2,
            max = 100,
            message = "Vacancy name length must be between 2 and 100 characters"
    )
    private String name;

    @NotBlank(
            message = "Vacancy description must not be blank"
    )
    @Size(
            min = 10,
            max = 3000,
            message = "Vacancy description length must be between 10 and 3000 characters"
    )
    private String description;

    @NotNull(
            message = "Category id must be specified"
    )
    @Positive(
            message = "Category id must be positive"
    )
    private Integer categoryId;

    @NotNull(
            message = "Salary must be specified"
    )
    @Positive(
            message = "Salary must be positive"
    )
    private BigDecimal salary;

    @NotNull(
            message = "Minimum experience must be specified"
    )
    @PositiveOrZero(
            message = "Minimum experience must not be negative"
    )
    @Max(
            value = 70,
            message = "Minimum experience must not be greater than 70"
    )
    private Integer expFrom;

    @NotNull(
            message = "Maximum experience must be specified"
    )
    @PositiveOrZero(
            message = "Maximum experience must not be negative"
    )
    @Max(
            value = 70,
            message = "Maximum experience must not be greater than 70"
    )
    private Integer expTo;

    private Boolean isActive;

    @NotNull(
            message = "Author id must be specified"
    )
    @Positive(
            message = "Author id must be positive"
    )
    private Integer authorId;

    private LocalDateTime createdDate;
    private LocalDateTime updateTime;

    public boolean isPublished() {
        return Boolean.TRUE.equals(isActive);
    }
}