package kg.attractor.jobsearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacancyDto {

    @JsonProperty(
            access = JsonProperty.Access.READ_ONLY
    )
    private Integer id;

    @NotBlank(
            message = "{validation.vacancy.name.notBlank}"
    )
    @Size(
            min = 2,
            max = 150,
            message = "{validation.vacancy.name.size}"
    )
    private String name;

    @NotBlank(
            message = "{validation.vacancy.description.notBlank}"
    )
    @Size(
            min = 10,
            max = 2000,
            message = "{validation.vacancy.description.size}"
    )
    private String description;

    @NotNull(
            message = "{validation.vacancy.category.notNull}"
    )
    @Positive(
            message = "{validation.vacancy.category.positive}"
    )
    private Integer categoryId;

    @NotNull(
            message = "{validation.vacancy.salary.notNull}"
    )
    @Positive(
            message = "{validation.vacancy.salary.positive}"
    )
    private BigDecimal salary;

    @NotNull(
            message = "{validation.vacancy.expFrom.notNull}"
    )
    @PositiveOrZero(
            message = "{validation.vacancy.expFrom.positiveOrZero}"
    )
    @Max(
            value = 70,
            message = "{validation.vacancy.expFrom.max}"
    )
    private Integer expFrom;

    @NotNull(
            message = "{validation.vacancy.expTo.notNull}"
    )
    @PositiveOrZero(
            message = "{validation.vacancy.expTo.positiveOrZero}"
    )
    @Max(
            value = 70,
            message = "{validation.vacancy.expTo.max}"
    )
    private Integer expTo;

    @NotNull(
            message = "{validation.vacancy.active.notNull}"
    )
    private Boolean isActive;

    @JsonProperty(
            access = JsonProperty.Access.READ_ONLY
    )
    private Integer authorId;

    @JsonProperty(
            access = JsonProperty.Access.READ_ONLY
    )
    private LocalDateTime createdDate;

    @JsonProperty(
            access = JsonProperty.Access.READ_ONLY
    )
    private LocalDateTime updateTime;
}