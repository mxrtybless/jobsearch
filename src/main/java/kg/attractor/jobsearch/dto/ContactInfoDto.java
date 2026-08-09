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
            message = "Contact type id must be specified"
    )
    @Positive(
            message = "Contact type id must be positive"
    )
    private Integer typeId;

    @Size(
            max = 255,
            message = "Contact value must not be longer than 255 characters"
    )
    private String value;
}