package kg.attractor.jobsearch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vacancies")
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(
            name = "name",
            nullable = false,
            length = 150
    )
    private String name;

    @Column(
            name = "description",
            nullable = false,
            length = 2000
    )
    private String description;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "category_id",
            nullable = false
    )
    private Category category;

    @Column(
            name = "salary",
            precision = 12,
            scale = 2
    )
    private BigDecimal salary;

    @Column(name = "exp_from")
    private Integer expFrom;

    @Column(name = "exp_to")
    private Integer expTo;

    @Column(
            name = "is_active",
            nullable = false
    )
    private Boolean isActive;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "author_id",
            nullable = false
    )
    private User author;

    @Column(
            name = "created_date",
            nullable = false
    )
    private LocalDateTime createdDate;

    @Column(
            name = "update_time",
            nullable = false
    )
    private LocalDateTime updateTime;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @OneToMany(
            mappedBy = "vacancy",
            fetch = FetchType.LAZY
    )
    private List<RespondedApplicant> responses =
            new ArrayList<>();

    public boolean isPublished() {
        return Boolean.TRUE.equals(
                isActive
        );
    }
}