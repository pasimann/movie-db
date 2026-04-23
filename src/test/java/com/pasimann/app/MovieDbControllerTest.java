package com.pasimann.app;

import com.pasimann.app.model.Movie;
import com.pasimann.app.model.MovieRepository;
import com.pasimann.app.model.Person;
import com.pasimann.app.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

    // TODO more tests; better data setup
}
