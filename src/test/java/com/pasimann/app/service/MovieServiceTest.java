package com.pasimann.app.service;

import com.pasimann.app.api.MovieData;
import com.pasimann.app.api.PersonData;
import com.pasimann.app.api.SearchRequest;
import com.pasimann.app.mapper.DataMapper;
import com.pasimann.app.model.Movie;
import com.pasimann.app.model.MovieRepository;
import com.pasimann.app.model.Person;
import com.pasimann.app.model.PersonRepository;
import com.pasimann.app.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private DataMapper dataMapper;

    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // AI generated; fixed incorrect data, mocks, etc.

    @Test
    public void testFindMoviesBy_directorExists_returnsMoviesPerson() {
        SearchRequest request = new SearchRequest("John", "Doe");
        PersonData director = new PersonData(2L, "John", "Doe", "DIRECTOR");
        MovieData movieData = new MovieData(1L, "Test Movie", 2010, new ArrayList<>(), 18, 3, new ArrayList<>(), director, "A movie about testing");

        Movie movie = new Movie("Movie", 2010, 15, 4,
                "Very exiting movie.", List.of("Action", "Drama"), new ArrayList<>());

        when(movieRepository.getAllMovies()).thenReturn(List.of(movie));
        when(dataMapper.mapToMovieData(any(Movie.class))).thenReturn(movieData);

        List<MovieData> result = movieService.findMoviesByPerson(request);

        assertEquals(1, result.size());
        assertEquals("Test Movie", result.get(0).getName());
    }

    @Test
    public void testFindMoviesBy_actorExists_returnsMoviesPerson() {
        SearchRequest request = new SearchRequest("Jane", "Doe");
        PersonData director = new PersonData(2L, "John", "Doe", "DIRECTOR");
        PersonData actor = new PersonData(3L, "Jane", "Doe", "ACTOR");
        List<PersonData> actors = List.of(actor);
        MovieData movieData = new MovieData(1L, "Test Movie", 2010, new ArrayList<>(), 18, 3, actors, director, "A movie about testing");

        Movie movie = new Movie("Movie", 2010, 15, 4,
                "Very exiting movie.", List.of("Action", "Drama"), new ArrayList<>());

        when(movieRepository.getAllMovies()).thenReturn(List.of(movie));
        when(dataMapper.mapToMovieData(any(Movie.class))).thenReturn(movieData);

        List<MovieData> result = movieService.findMoviesByPerson(request);

        assertEquals(1, result.size());
        assertEquals("Test Movie", result.get(0).getName());
    }

    @Test
    public void testGetAllMovies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Movie", 2010, 15, 4,
                "Very exiting movie.", List.of("Action", "Drama"), new ArrayList<>()));
        when(movieRepository.getAllMovies()).thenReturn(movies);
        when(dataMapper.mapToMovieData(any(Movie.class))).thenReturn(new MovieData());

        List<MovieData> result = movieService.getAllMovies();

        assertEquals(1, result.size());
    }

    @Test
    public void testSaveNewMovie_withNewPersons_savesMovieAndPersons() {
        PersonData director = new PersonData(2L, "John", "Doe", "DIRECTOR");
        PersonData actor = new PersonData(3L, "Jane", "Doe", "ACTOR");
        List<PersonData> actors = new ArrayList<>();
        actors.add(actor);
        MovieData movieData = new MovieData(1L, "Test Movie", 2010, new ArrayList<>(), 18, 3, actors, director, "A movie about testing");
        when(personRepository.findByFirstNameAndLastNameAndRole("John", "Doe", "DIRECTOR")).thenReturn(Optional.empty());
        when(personRepository.findByFirstNameAndLastNameAndRole("Jane", "Doe", "ACTOR")).thenReturn(Optional.empty());

        Movie movie = new Movie("Movie", 2010, 15, 4,
                "Very exiting movie.", List.of("Action", "Drama"), new ArrayList<>());

        when(dataMapper.mapToMovie(any(MovieData.class), any(List.class))).thenReturn(movie);
        when(movieRepository.save(movie)).thenReturn(movie);
        when(dataMapper.mapToMovieData(movie)).thenReturn(movieData);

        MovieData result = movieService.saveNewMovie(movieData);

        assertEquals("Test Movie", result.getName());
    }

    @Test
    public void testSaveNewMovie_withExistingPersons_savesMovieWithExistingPersons() {
        PersonData directorData = new PersonData(2L, "John", "Doe", "DIRECTOR");
        PersonData actorData = new PersonData(3L, "Jane", "Doe", "ACTOR");
        List<PersonData> actorsData = new ArrayList<>();
        actorsData.add(actorData);
        MovieData movieData = new MovieData(1L, "Test Movie", 2010, new ArrayList<>(), 18, 3, actorsData, directorData, "A movie about testing");


        Person director = new Person("John", "Doe", Role.DIRECTOR);
        Person actor = new Person("Jane", "Doe", Role.ACTOR);

        when(personRepository.findByFirstNameAndLastNameAndRole("John", "Doe", "DIRECTOR")).thenReturn(Optional.of(director));
        when(personRepository.findByFirstNameAndLastNameAndRole("Jane", "Doe", "ACTOR")).thenReturn(Optional.of(actor));

        Movie movie = new Movie("Movie", 2010, 15, 4,
                "Very exiting movie.", List.of("Action", "Drama"), new ArrayList<>());

        when(dataMapper.mapToMovie(any(MovieData.class), any(List.class))).thenReturn(movie);
        when(movieRepository.save(movie)).thenReturn(movie);
        when(dataMapper.mapToMovieData(movie)).thenReturn(movieData);

        MovieData result = movieService.saveNewMovie(movieData);

        assertEquals("Test Movie", result.getName());
    }
}
