package net.filmcity.app;

import net.filmcity.app.domain.Movie;
import net.filmcity.app.repositories.MovieRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
                .content("{\"title\": \"Titanic\", \"coverImage\": \"PHP\", \"director\": \"Laura\", \"year\": \"2005\", \"synopsis\": \"PHP\", \"genero\": \"PHP\", \"valoracion\": \"4\", \"alquilado\": \"false\"}")
        ).andExpect(status().isOk());

        List<Movie> movies = movieRepository.findAll();
        assertThat(movies, contains(allOf(
                hasProperty("title", is("Titanic")),
                hasProperty("coverImage", is("PHP")),
                hasProperty("director", is("Laura")),
                hasProperty("year", is(2005)),
                hasProperty("synopsis", is("PHP")),
                hasProperty("genero", is("PHP")),
                hasProperty("valoracion", is(4)),
                hasProperty("alquilado", is(false))

        )));
    }



    @Test
    void returnsAnErrorIfTryingToGetAMovieThatDoesNotExist() throws Exception {
        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void allowsToDeleteAMovieById() throws Exception {
        Movie movie = movieRepository.save(new Movie("Jurassic Park", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg",
                "Steven Spilberg", 1993,
                "A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA.",
                "Comedia",
                4,
                false));

        Movie movie2 = movieRepository.save(new Movie("Ratatouille",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/npHNjldbeTHdKKw28bJKs7lzqzj.jpg",
                "Brad Bird", 2007,
                "Remy, a resident of Paris, appreciates good food and has quite a sophisticated palate. He would love to become a chef so he can create and enjoy culinary masterpieces to his heart's delight. The only problem is, Remy is a rat.",
                "Familiar",
                2,
                false));

        mockMvc.perform(delete("/movies/"+ movie.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/movies/"+ movie2.getId()))
                .andExpect(status().isOk());


        List<Movie> movies = movieRepository.findAll();
        assertThat(movies, not(contains(allOf(
                hasProperty("title", is("Jurassic Park")),
                hasProperty("coverImage", is("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg")),
                hasProperty("director", is("Steven Spelberg")),
                hasProperty("synopsis", is("A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA")),
                hasProperty("genero", is("Comedia")),
                hasProperty("valoracion", is(2)),
                hasProperty("alquilado", is(false))
        ))));

    }

    @Test
    void returnsAnErrorIfTryingToDeleteAMovieThatDoesNotExist() throws Exception {
        mockMvc.perform(delete("/movies/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    @Disabled
    void allowsToModifyAMovie() throws Exception {
        Movie movie = movieRepository.save(new Movie("Jurasic Park",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg",
                "Steven Spilberg",
                1999,
                "A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA.",
                "Drama",
                3,
                false));

        mockMvc.perform(put("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"" + movie.getId() + "\", \"title\": \"Jurassic Park2\"," +
                        " \"coverImage\": \"\"https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg\"\"" +
                        "\"director\": \"\"Steven\"," +
                        "\"year\": 1999," +
                        "\"synopsis\": Enamoramiento entre un animal y una persona," +
                        "\"genero\": \"Drama\"," +
                        "\"valoracion\": 3," +
                        "\"alquilado\": false }")
        ).andExpect(status().isOk());

        Movie movie2 = movieRepository.save(new Movie("Paris Texas",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg",
                " Wim Wenders ",
                1984,
                "Travis Henderson, un vagabundo sin rumbo que lleva cuatro años desaparecido, sale del desierto" +
                        " y debe reencontrarse con la sociedad, con él mismo, con su vida y con su familia.",
                "Drama",
                5,
                false));

        mockMvc.perform(put("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"" + movie2.getId() + "\", \"title\": \"Paris Texas\"," +
                        " \"coverImage\": \"\"https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg\"\"" +
                        "\"director\": \"\"Win Wenders\"," +
                        "\"year\": 1984," +
                        "\"synopsis\":Travis Henderson, un vagabundo sin rumbo que lleva cuatro años desaparecido, sale del desierto" +
                                " y debe reencontrarse con la sociedad, con él mismo, con su vida y con su familia." +
                        "\"genero\": \"Drama\"," +
                        "\"valoracion\": 5," +
                        "\"alquilado\": false }")
        ).andExpect(status().isOk());

        List<Movie> movies = movieRepository.findAll();

        assertThat(movies, hasSize(2));
        assertThat(movies.get(0).getTitle(), equalTo("Bella y Bestia"));
        assertThat(movies.get(0).getCoverImage(), equalTo("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg"));
        assertThat(movies.get(0).getDirector(), equalTo("Steven "));
        assertThat(movies.get(0).getYear(), equalTo(1999));
        assertThat(movies.get(0).getSynopsis(), equalTo("Enamoramiento entre un animal y una persona,\" "));
        assertThat(movies.get(0).getGenero(), equalTo("Drama"));
        assertThat(movies.get(0).getValoracion(), equalTo(3));
        assertThat(movies.get(0).isAlquilado(), equalTo(false));
        assertThat(movies.get(1).getTitle(), equalTo("Paris Texas"));
        assertThat(movies.get(1).getCoverImage(), equalTo("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg"));
        assertThat(movies.get(1).getDirector(), equalTo("Win Wenders "));
        assertThat(movies.get(1).getYear(), equalTo(1984));
        assertThat(movies.get(1).getSynopsis(), equalTo("Travis Henderson, un vagabundo sin rumbo que lleva cuatro años desaparecido, sale del desierto\" +\n" +
                "                                \" y debe reencontrarse con la sociedad, con él mismo, con su vida y con su familia.\"\" "));
        assertThat(movies.get(1).getGenero(), equalTo("Drama"));
        assertThat(movies.get(1).getValoracion(), equalTo(5));
        assertThat(movies.get(1).isAlquilado(), equalTo(false));

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
