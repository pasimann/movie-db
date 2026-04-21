package com.pasimann.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pasimann.app.api.MovieItem;

@Service
public class MovieService {

    public MovieService(MovieRepository movieRepository, PersonRepository personRepository) {
        this.movieRepository = movieRepository;
        this.personRepository = personRepository;
    }

    public List<MovieItem> getAllMovies() {
      // TODO map all the fields 
      List<Movie> movies = movieRepository.getAllMovies(); 
      for (Movie movie : movies) {
          List<Person> persons = personRepository.getAllPersonsByMovieId(movie.getId());
      }

    }

    public MovieItem saveItem(MovieItem item) {
        // TODO FIX ME: map all the fields 
        // Persons (actor, director) needs to be persisted as a new person only if not already in the db 
        Movie movie = new Movie(item.title(), item.description());
        movieRepository.save(movie);
        return new MovieItem(movie.getTitle(), movie.getDescription());
    }
}