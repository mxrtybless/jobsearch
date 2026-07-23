package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.MovieDto;

import java.util.List;

public interface MovieService {
    List<MovieDto> findAll();
    MovieDto findById(Long id);
    void save(MovieDto dto);
}
