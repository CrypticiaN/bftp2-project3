package net.filmcity.app;

import net.filmcity.app.domain.Movie;
import net.filmcity.app.repositories.MovieRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    MovieRepository movieRepository;


    @BeforeEach
    void setUp() {
        movieRepository.deleteAll();
    }

    @Test
    void returnsTheExistingMovies() throws Exception {

        addSampleMovies();

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].title", equalTo("Jurassic Park")))
                .andExpect(jsonPath("$[0].coverImage", equalTo("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg")))
                .andExpect(jsonPath("$[0].director", equalTo("Steven Spielberg")))
                .andExpect(jsonPath("$[0].year", equalTo(1993)))
                .andExpect(jsonPath("$[0].synopsis", equalTo("A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA.")))
                .andExpect(jsonPath("$[0].genero", equalTo("Comedia")))
                .andExpect(jsonPath("$[0].valoracion", equalTo(4)))
                .andExpect(jsonPath("$[0].alquilado", equalTo(false)))
                .andExpect(jsonPath("$[1].title", equalTo("Ratatouille")))
                .andExpect(jsonPath("$[1].coverImage", equalTo("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/npHNjldbeTHdKKw28bJKs7lzqzj.jpg")))
                .andExpect(jsonPath("$[1].director", equalTo("Brad Bird")))
                .andExpect(jsonPath("$[1].year", equalTo(2007)))
                .andExpect(jsonPath("$[1].synopsis", equalTo("Remy, a resident of Paris, appreciates good food and has quite a sophisticated palate. He would love to become a chef so he can create and enjoy culinary masterpieces to his heart's delight. The only problem is, Remy is a rat.")))
                .andExpect(jsonPath("$[1].genero", equalTo("Familiar")))
                .andExpect(jsonPath("$[1].valoracion", equalTo(2)))
                .andExpect(jsonPath("$[1].alquilado", equalTo (false)));

    }

    @Test
    void allowsToCreateANewMovie() throws Exception {
        mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Laura\", \"coverImage\": \"PHP\", \"director\": \"Laura\", \"year\": \"PHP\", \"synopsis\": \"Laura\", \"genero\": \"PHP\"}")
        ).andExpect(status().isOk());

        List<Movie> movies = movieRepository.findAll();
        assertThat(movies, contains(allOf(
                hasProperty("title", is("Laura")),
                hasProperty("coverImage", is("PHP")),
                hasProperty("director", is("PHP")),
                hasProperty("year", is("PHP")),
                hasProperty("synopsis", is("PHP")),
                hasProperty("genero", is("PHP")),

        )));
    }

    @Test
    void allowsToFindMovieById() throws Exception {

        Movie movie = movieRepository.save(new Movie("Marta", "Kotlin"));

        mockMvc.perform(get("/movies/" + movie.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Marta")))
                .andExpect(jsonPath("$.favouriteLanguage", equalTo("Kotlin")));
    }

    @Test
    void returnsAnErrorIfTryingToGetAMovieThatDoesNotExist() throws Exception {
        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void allowsToDeleteAMovieById() throws Exception {
        Movie movie = movieRepository.save(new Movie("Marta", "Kotlin"));

        mockMvc.perform(delete("/movies/"+ movie.getId()))
                .andExpect(status().isOk());


        List<Movie> movies = movieRepository.findAll();
        assertThat(movies, not(contains(allOf(
                hasProperty("name", is("Marta")),
                hasProperty("favouriteLanguage", is("Kotlin"))
        ))));

    }

    @Test
    void returnsAnErrorIfTryingToDeleteAMovieThatDoesNotExist() throws Exception {
        mockMvc.perform(delete("/movies/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    void allowsToModifyAMovie() throws Exception {
        Movie movie = movieRepository.save(new Movie("Yeraldin", "Java"));

        mockMvc.perform(put("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"" + movie.getId() + "\", \"name\": \"Yeraldin\", \"favouriteLanguage\": \"Ruby\" }")
        ).andExpect(status().isOk());

        List<Movie> movies = movieRepository.findAll();

        assertThat(movies, hasSize(1));
        assertThat(movies.get(0).getTitle(), equalTo("Yeraldin"));
        assertThat(movies.get(0).getCoverImage(), equalTo("Ruby"));
    }

    @Test
    void returnsAnErrorWhenTryingToModifyAMovieThatDoesNotExist() throws Exception {
        addSampleMovies();

        mockMvc.perform(put("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"" + -1 + "\", \"name\": \"Pepita\", \"favouriteLanguage\": \"C++\" }")
        ).andExpect(status().isNotFound());
    }

    private void addSampleMovies() {
        List<Movie> movies = List.of(
                new Movie("Jurassic Park",
                        "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg",
                        "Steven Spielberg",
                        1993,
                        "A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA.",
                        "Comedia",
                        4, false),
                new Movie("Ratatouille",
                        "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/npHNjldbeTHdKKw28bJKs7lzqzj.jpg",
                        "Brad Bird",
                        2007,
                        "Remy, a resident of Paris, appreciates good food and has quite a sophisticated palate. He would love to become a chef so he can create and enjoy culinary masterpieces to his heart's delight. The only problem is, Remy is a rat.",
                        "Familiar",
                        2, false)
        );

        movieRepository.saveAll(movies);
    }

}
