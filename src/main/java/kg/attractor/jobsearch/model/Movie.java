package kg.attractor.jobsearch.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Movie {
    private Long id;
    private String name;
    private Integer releaseYear;
    private String description;
    private Long directorId;
}
