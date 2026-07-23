package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.DirectorDao;
import kg.attractor.jobsearch.dao.MovieDao;
import kg.attractor.jobsearch.dto.MovieDto;
import kg.attractor.jobsearch.model.Director;
import kg.attractor.jobsearch.model.Movie;
import kg.attractor.jobsearch.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieDao movieDao;
    private final DirectorDao directorDao;

    private MovieDto getFilledMovieDto(Long movieId) {
        Movie movie = movieDao.findById(movieId)
                .orElseThrow();
        log.info("Movie: {}", movie.getId());
        Director director = directorDao.findById(movie.getDirectorId())
                .orElseThrow();
        return MovieDto.builder()
                .title(movie.getName())
                .releaseYear(movie.getReleaseYear())
                .director(director.getFullname())
                .description(movie.getDescription())
                .build();
    }

    @Override
    public List<MovieDto> findAll() {
        List<Movie> list = movieDao.findAll();
        return list.stream()
                .map(e -> getFilledMovieDto(e.getId()))
                .toList();
    }

    @Override
    public MovieDto findById(Long id) {
        return getFilledMovieDto(id);
    }

    @Override
    public void save(MovieDto dto) {
        Movie movie = new Movie();
        movie.setName(dto.getTitle());
        movie.setDescription(dto.getDescription());
        movie.setReleaseYear(dto.getReleaseYear());
        movie.setDirectorId(2L);
        movieDao.save(movie);
    }
}
