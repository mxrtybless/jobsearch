package kg.attractor.jobsearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfoDto {

    @JsonProperty(
            access = JsonProperty.Access.READ_ONLY
    )
    private Integer id;

    @JsonProperty(
            access = JsonProperty.Access.READ_ONLY
    )
    private Integer resumeId;

    @NotNull(
            message = "{validation.contact.type.notNull}"
    )
    @Positive(
            message = "{validation.contact.type.positive}"
    )
    private Integer typeId;

    @Size(
            max = 255,
            message = "{validation.contact.value.size}"
    )
    private String value;
}