package kg.attractor.jobsearch.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieDto {
    private String title;
    private String director;
    private Integer releaseYear;
    private String description = "";
}