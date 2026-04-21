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

    public MovieItem saveNewMovie(MovieItem movieItem) {
        // TODO FIX ME: map all the fields 
        // Persons (actor, director) could be persisted 
        // as a new person only if not already in the db 
        // So we can check the person repo for the given name and role
        // to check if we need add a new person row or use existing one


        List<Person> personel = new ArrayList<>(); 

        Optional<Person> dbDirector = personRepository
            .findByFirstNameAndLastNameAndRole(
                movieItem.getDirector().getFirstName(),
                movieItem.getDirector().getLastName(), 
                "DIRECTOR"); 
        
       if (dbDirector.isPresent()) {
         personel.add(dbDirector.get()); 
       } else {
         personel.add(new Person(movieItem.getDirector().getFirstName(),
                movieItem.getDirector().getLastName(), 
                "DIRECTOR"); 
       }

       for (PersonItem actor : movieItem.getActors()) {
         Optional<Person> dbActor = personRepository
            .findByFirstNameAndLastNameAndRole(
                actor.getFirstName(),
                actor.getLastName(), 
                "ACTOR"); 
        
         if (dbActor.isPresent()) {
           personel.add(dbActor.get()); 
         } else {
           personel.add(new Person(actor.getFirstName(),
                 actor.getLastName(), 
                 "ACTOR")); 
         }
       }

       // TODO add Movie to db with mapped fields; Director and Actors 
       // might be new ones or existing rows. 
       
       Movie movie = dataItemMapper.mapToMovie(movieItem, personel);
       return dataItemMapper.mapToMovieItem(movieRepository.save(movie));
    }
}