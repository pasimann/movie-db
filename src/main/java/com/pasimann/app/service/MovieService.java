package com.pasimann.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pasimann.app.api.MovieItem;

@Service
public class MovieService {

   MovieRepository movieRepository; 
   PersonRepository personRepository; 
   DataItemMapper dataItemMapper;

    public MovieService(MovieRepository movieRepository, PersonRepository personRepository, 
           DataItemMapper dataItemMapper) {
         this.movieRepository = movieRepository;
         this.personRepository = personRepository;
         this.dataItemMapper = dataItemMapper;
    }

    public List<MovieItem> getAllMovies() {
      List<MovieItem> results = new ArrayList<>();
      List<Movie> movies = movieRepository.getAllMovies();

      for (Movie movie : movies) {
          List<Person> personel = personRepository.getAllPersonsByMovieId(movie.getId());
          results.add(dataItemMapper.mapToMovieItem(movie, personel));
      }
      return results;
    }

    public MovieItem saveItem(MovieItem item) {
        // TODO FIX ME: map all the fields 
        // Persons (actor, director) needs to be persisted as a new person only if not already in the db 
        Movie movie = new Movie(item.title(), item.description());
        movieRepository.save(movie);
        return new MovieItem(movie.getTitle(), movie.getDescription());
    }
}