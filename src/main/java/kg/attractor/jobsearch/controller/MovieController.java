package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.MovieDto;
import kg.attractor.jobsearch.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public List<MovieDto> getMovies() {
        return movieService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable Long id) {
        return new ResponseEntity<>(
                movieService.findById(id),
                HttpStatus.OK);
    }

    @PostMapping
    public HttpStatus createMovie(MovieDto dto) {
        movieService.save(dto);
        return HttpStatus.OK;
    }
}
