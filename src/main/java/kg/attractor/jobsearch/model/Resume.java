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
@Table(name = "resumes")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(
            name = "applicant_id",
            nullable = false
    )
    private Integer applicantId;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "applicant_id",
            insertable = false,
            updatable = false
    )
    private User applicant;

    @Column(
            name = "name",
            nullable = false,
            length = 150
    )
    private String name;

    @Column(
            name = "category_id",
            nullable = false
    )
    private Integer categoryId;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "category_id",
            insertable = false,
            updatable = false
    )
    private Category category;

    @Column(
            name = "salary",
            precision = 12,
            scale = 2
    )
    private BigDecimal salary;

    @Column(
            name = "is_active",
            nullable = false
    )
    private Boolean isActive;

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
            mappedBy = "resume",
            fetch = FetchType.LAZY
    )
    private List<ContactInfo> contactInfo =
            new ArrayList<>();

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @OneToMany(
            mappedBy = "resume",
            fetch = FetchType.LAZY
    )
    private List<EducationInfo> educationInfo =
            new ArrayList<>();

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @OneToMany(
            mappedBy = "resume",
            fetch = FetchType.LAZY
    )
    private List<WorkExperienceInfo>
            workExperienceInfo =
            new ArrayList<>();

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @OneToMany(
            mappedBy = "resume",
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