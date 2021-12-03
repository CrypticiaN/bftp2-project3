package net.filmcity.app.controllers;

import net.filmcity.app.domain.Movie;
import net.filmcity.app.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
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



    @PostMapping("/movies")
    public Movie addMovie(@RequestBody Movie movie) {

        return movieRepository.save(movie);
    }

    @PutMapping("/movies")
    public Movie updateMovie(@RequestBody Movie movie) {
        movieRepository.findById(movie.getId()).orElseThrow(MovieNotFoundException::new);
        return movieRepository.save(movie);
    }

    @DeleteMapping("/movies/{id}")
    public Movie deleteMovieById(@PathVariable Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(MovieNotFoundException::new);
        movieRepository.deleteById(id);
        return movie;
    }


    /**@PutMapping("/movies/${id}/book?customerName=${renter}")
    public Movie rented(@PathVariable Long id, @RequestParam String customerName){
        Movie movie =  movieRepository.findById(id).orElseThrow(MovieNotFoundException::new);
        movie.setAlquilado(true);
        movie.setCustomerName(customerName);
        return movieRepository.save(movie);
    }**/

    @PutMapping("/movies/{id}/book")
    public Movie bookMovie(@PathVariable Long id, @RequestParam String customerName) {
        Movie movie = movieRepository.findById(id).orElseThrow(MovieNotFoundException::new);
        movie.setAlquilado(true);
        movie.setCustomerName(customerName);
        return movieRepository.save(movie);
    }


   /** @PutMapping("/movies/{id}/return")
    public Movie bookMovie(@PathVariable Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(MovieNotFoundException::new);
        movie.setAlquilado(false);
        movie.setCustomerName(null);
        return movieRepository.save(movie);
    }**/



}
