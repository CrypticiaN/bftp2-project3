package net.filmcity.app.controllers;

import net.filmcity.app.domain.Movie;
import net.filmcity.app.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class MoviesController {

    private final MovieRepository movieRepository;

    @Autowired
    MoviesController(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    @GetMapping("/movies")
    public List<Movie> allMovies() {
        return movieRepository.findAll();
    }

    @GetMapping("/movies/{id}")
    public Movie findMovie(@PathVariable Long id) {
        return movieRepository.findById(id).orElseThrow(MovieNotFoundException::new);
    }

    @PostMapping("/movies")
    public Movie addMovie(@RequestBody Movie movie) {

        return movieRepository.save(movie);
    }

    @PutMapping("/movies")
    public Movie updateMovie(@RequestBody Movie movie) {
        movieRepository.findById(movie.getId()).orElseThrow(MovieNotFoundException::new);
        return movieRepository.save(movie);
    }

    @PutMapping("/movies/{id}")
    public Movie updateMovieById(@RequestBody Movie movie) {
        movieRepository.findById(movie.getId()).orElseThrow(MovieNotFoundException::new);
        return movieRepository.save(movie);
    }

    @DeleteMapping("/movies/{id}")
    public Movie deleteMovieById(@PathVariable Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(MovieNotFoundException::new);
        movieRepository.deleteById(id);
        return movie;
    }

}
