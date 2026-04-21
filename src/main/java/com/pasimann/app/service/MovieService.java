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
          results.add(dataItemMapper.mapToMovieItem(movie));
      }
      return results;
    }

    public MovieItem saveItem(MovieItem movieItem) {
        // TODO FIX ME: map all the fields 
        // Persons (actor, director) could be persisted 
        // as a new person only if not already in the db 
        // So we can check the person repo for the given name and role
        // to check if we need add a new person row or use existing one

        Person movieDirector; 

        Optional<Person> director = personRepository.
            .findByFirstNameAndLastNameAndRole(
                movieItem.getDirector().getFirstName(),
                movieItem.getDirector().getLastName(), 
                "DIRECTOR"); 
        
       if (director.isPresent()) {
         movieDirector = director.get(); 
       } else {
         movieDirector = new Person(movieItem.getDirector().getFirstName(),
                movieItem.getDirector().getLastName(), 
                "DIRECTOR"); 
       }

       List<Person> movieActors = new ArrayList<>(); 

       for (PersonItem item : movieItem.getActors()) {
         Optional<Person> actor = personRepository.
            .findByFirstNameAndLastNameAndRole(
                movieItem.getDirector().getFirstName(),
                movieItem.getDirector().getLastName(), 
                "ACTOR"); 
        
         if (actor.isPresent()) {
           movieActors.add(actor.get()); 
         } else {
           movieActors.add(new Person(movieItem.getDirector().getFirstName(),
                 movieItem.getDirector().getLastName(), 
                 "ACTOR")); 
         }
       }

       // TODO add Movie to db with mapped fields; Director and Actors 
       // might be new ones or existing rows. 
    }
}