package net.filmcity.app.repositories;

import net.filmcity.app.domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class SampleDataLoader {

    private final MovieRepository movieRepository;

    @Autowired
    public SampleDataLoader(MovieRepository movieRepository){

        this.movieRepository = movieRepository;
    }

    @PostConstruct
    public void addSampleMovies() {
        if (movieRepository.findAll().isEmpty()) {
            List<Movie> movies = List.of(
                    new Movie("Jurassic Park",
                            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg",
                            "Steven Spielberg",
                            1993,
                            "A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA.",
                            "Familiar"),
                    new Movie("Ratatouille",
                            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/npHNjldbeTHdKKw28bJKs7lzqzj.jpg",
                            "Brad Bird",
                            2007,
                            "Remy, a resident of Paris, appreciates good food and has quite a sophisticated palate. He would love to become a chef so he can create and enjoy culinary masterpieces to his heart's delight. The only problem is, Remy is a rat.",
                            "Dibujos"),
                    new Movie("Cruella",
                            "https://lumiere-a.akamaihd.net/v1/images/image_7ff71125.jpeg?region=0%2C0%2C540%2C810",
                            "Craig Gillespie",
                            2021,
                            "Set in London during the punk rock movement of the 1970s, the film revolves around Estella Miller, an aspiring fashion designer, as she explores the path that will lead her to become a notorious up-and-coming fashion designer known as Cruella de Vil.",
                            "Drama"),
                    new Movie("Mean Girls",
                            "https://images-na.ssl-images-amazon.com/images/I/71eQtET-kmL._RI_.jpg",
                            "Mark Waters",
                            2004,
                            "Lindsay Lohan stars as Cady Heron, a 16 year old homeschooled girl who not only makes the mistake of falling for Aaron Samuels, the ex-boyfriend of queenbee Regina George, but also unintentionally joins The Plastics, led by Regina herself.",
                            "Comedia"),
                    new Movie("Lady Bird",
                            "https://play-lh.googleusercontent.com/qh6m6We5w7325ttmO1qcA0Zmtlm2UG4JUux6VSBsjvxG9azJ6KHdBsobAviSMcTOzTULPQ",
                            "Greta Gerwig",
                            2017,
                            "Christine 'Lady Bird' McPherson (Saoirse Ronan) is a senior at a Catholic high school in Sacramento, California in 2002. She longs to attend a prestigious college in 'a city with culture'.",
                            "Drama"),
                    new Movie("Suffragette",
                            "https://musicart.xboxlive.com/7/b81f2600-0000-0000-0000-000000000002/504/image.jpg?w=1920&h=1080",
                            "Sarah Gavron",
                            2015,
                            "Inspired by true events, Suffragette movingly explores the passion and heartbreak of those who risked all they had for women's right to vote – their jobs, their homes, their children, and even their lives.",
                            "Historical_political"),
                    new Movie("On the basis of sex",
                            "https://m.media-amazon.com/images/I/71TuUvNkS4L._SL1500_.jpg",
                            "Mimi Leder",
                            2018,
                            "On the Basis of Sex is inspired by the true story of a young Ruth Bader Ginsburg – then a struggling attorney and new mother – who faces adversity and numerous obstacles in her fight for equal rights throughout her career.",
                            "Comedia"),
                    new Movie("Bridge of Spies",
                            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bORlvwnVJE1Z6yIg96qnLLY3LJQ.jpg",
                            "Steven Spielberg",
                            2015,
                            "In 1957 New York City, Rudolf Abel is arrested and charged with spying for the Soviet Union. Insurance lawyer James B. Donovan (Tom Hanks) is appointed to defend Abel, so that Abel's trial will be seen as fair. Committed to the principle that the accused deserves a vigorous defense, he mounts the best defense of Abel he can, declining along the way to cooperate in the CIA's attempts to induce him to violate the confidentiality of his communications with his client.",
                            "Drama"),
                    new Movie("The Intouchables",
                            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/ttx4GQl0azO6NJFWj4XNZCQOY7S.jpg",
                            "Olivier Nakache, Eric Toledano",
                            2011,
                            "After a paragliding accident, Philippe, a rich aristocrat, is confined to his home. He employs Driss as home help. Driss is a young guy from the projects recently out of prison. In short, the person the least adapted for the job. Vivaldi and Earth, Wind and Fire, fine language and slang, suits and jogging outfits come together and a clash is inevitable. Two worlds collide and win each other over to give birth to a friendship as crazy, funny, and fierce as it is unexpected. A unique relationship that will make sparks fly and render Philippe and Driss untouchable.",
                            "Comedia"),
                    new Movie("The Lord of the Rings: The Fellowship of the Ring",
                            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/6oom5QYQ2yQTMJIbnvbkBL9cHo6.jpg",
                            "Peter Jackson",
                            2001,
                            "Set in Middle-earth, the story tells of the Dark Lord Sauron, who seeks the One Ring, which contains part of his soul, in order to return to power. The Ring has found its way to the young hobbit Frodo Baggins. The fate of Middle-earth hangs in the balance as Frodo and eight companions (who form the Fellowship of the Ring) begin their journey to Mount Doom in the land of Mordor, the only place where the Ring can be destroyed.",
                            "Fantasy, Adventure"),
                    new Movie("The Lord of the Rings: The Two Towers",
                            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/5VTN0pR8gcqV3EPUHHfMGnJYN9L.jpg",
                            "Peter Jackson",
                            2002,
                            "Continuing the plot of The Fellowship of the Ring, the film intercuts three storylines. Frodo and Sam continue their journey towards Mordor to destroy the One Ring, meeting and joined by Gollum, the ring's former keeper. Aragorn, Legolas, and Gimli come to the war-torn nation of Rohan and are reunited with the resurrected Gandalf, before fighting against the legions of the treacherous wizard Saruman at the Battle of Helm's Deep. Merry and Pippin escape capture, meet Treebeard the Ent, and help to plan an attack on Isengard, fortress of Saruman.",
                            "Fantasy, Adventure"),
                    new Movie("The Lord of the Rings: The Return of the King",
                            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/rCzpDGLbOoPwLjy3OAm5NUPOTrC.jpg",
                            "Peter Jackson",
                            2003,
                            "Continuing the plot of The Two Towers, Frodo, Sam and Gollum are making their final way toward Mount Doom in Mordor in order to destroy the One Ring, unaware of Gollum's true intentions, while Gandalf, Aragorn, Legolas, Gimli and the rest are joining forces together against Sauron and his legions in Minas Tirith.",
                            "Fantasy, Adventure")
                    // Añadir aqui todas las pelis del catálogo!!
            );

            movieRepository.saveAll(movies);
        }
    }
}
