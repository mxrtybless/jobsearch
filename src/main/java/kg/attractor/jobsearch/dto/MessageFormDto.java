package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MessageFormDto {
    @NotBlank
    @Size(max = 2000)
    private String content;
}
