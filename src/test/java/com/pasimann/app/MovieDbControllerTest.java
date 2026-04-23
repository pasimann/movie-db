package com.pasimann.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.pasimann.app.api.MovieData;
import com.pasimann.app.api.PersonData;
import com.pasimann.app.model.Movie;
import com.pasimann.app.model.MovieRepository;
import com.pasimann.app.model.Person;
import com.pasimann.app.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MovieDbControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    void setup() {
        Person actor = new Person("Matt", "Damon", Role.ACTOR);
        Person director = new Person("Christopher", "Nolan", Role.DIRECTOR);

        Movie movie = new Movie("Interstellar", 2010, 15, 5,
                "Very exiting movie.", List.of("Sci-fi", "Drama"), List.of(actor, director));

        movieRepository.save(movie);
    }

    @Test
    void getAllMovies_success() throws Exception {
        mockMvc.perform(get("/get-all-movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Interstellar"));
    }

    @Test
    void addNewMovie_success() throws Exception {
        PersonData directorData = new PersonData(null, "Christopher", "Nolan", "DIRECTOR");
        PersonData actorData1 = new PersonData(null, "Cillian", "Murphy", "ACTOR");
        PersonData actorData2 = new PersonData(null, "Emily", "Blunt", "ACTOR");

        MovieData movieData =
        new MovieData(
            null,
            "Oppenheimer",
            2020,
            List.of("Drama"),
            15,
            3,
                List.of(actorData1, actorData2),
            directorData,
            "A movie about nuclear scientist");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(movieData);

        mockMvc.perform(post("/add-new-movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Oppenheimer"));
    }

    // TODO more tests; better data setup
}
