package com.pasimann.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.pasimann.app.api.SearchRequest;
import com.pasimann.app.api.PersonData;
import com.pasimann.app.mapper.DataMapper;
import com.pasimann.app.model.*;
import org.springframework.stereotype.Service;

import com.pasimann.app.api.MovieData;

@Service
public class MovieService {

   MovieRepository movieRepository; 
   PersonRepository personRepository; 
   DataMapper dataMapper;

    public MovieService(MovieRepository movieRepository, PersonRepository personRepository,
                        DataMapper dataMapper) {
         this.movieRepository = movieRepository;
         this.personRepository = personRepository;
         this.dataMapper = dataMapper;
    }

    public List<MovieData> findMoviesByRole(SearchRequest request, Role role) {
        List<MovieData> results = new ArrayList<>();
        Optional<Person> person = personRepository.findByFirstNameAndLastNameAndRole(
                request.getFirstName(), request.getLastName(), role.name());
        if (person.isPresent()) {
            List<Movie> movies = person.get().getMovies().stream().toList();
            for (Movie movie : movies) {
                results.add(dataMapper.mapToMovieData(movie));
            }
        }
        return results;
    }

    public List<MovieData> findMoviesByPerson(SearchRequest request) {
        List<MovieData> result = new ArrayList<>();
        // TODO you could query Person's name (any role) from the DB and access its movies list
        for (MovieData movie : getAllMovies()) {
            if ((movie.getDirector().getFirstName().equals(request.getFirstName())
                  && movie.getDirector().getLastName().equals(request.getLastName()))
               || isActorInTheMovie(request, movie)) {
                result.add(movie);
            }
        }
        return result;
    }

    private Boolean isActorInTheMovie(SearchRequest request, MovieData movieData) {
        for (PersonData person : movieData.getActors()) {
            if (person.getFirstName().equals(request.getFirstName())
                    && person.getLastName().equals(request.getLastName())) {
                return true;
            }
        }
        return false;
    }

    public List<MovieData> getAllMovies() {
      List<MovieData> results = new ArrayList<>();
      List<Movie> movies = movieRepository.getAllMovies();

      for (Movie movie : movies) {
          results.add(dataMapper.mapToMovieData(movie));
      }
      return results;
    }

    public MovieData addNewMovie(MovieData movieData) {
        // Persons (actor, director) persisted to the db
        // as a new person only if not already in the db

        List<Person> personnel = new ArrayList<>();

        Optional<Person> dbDirector = personRepository
            .findByFirstNameAndLastNameAndRole(
                movieData.getDirector().getFirstName(),
                movieData.getDirector().getLastName(),
                "DIRECTOR"); 
        
       if (dbDirector.isPresent()) {
         personnel.add(dbDirector.get());
       } else {
         personnel.add(new Person(movieData.getDirector().getFirstName(),
                movieData.getDirector().getLastName(),
                 Role.DIRECTOR));
       }

       for (PersonData actor : movieData.getActors()) {
         Optional<Person> dbActor = personRepository
            .findByFirstNameAndLastNameAndRole(
                actor.getFirstName(),
                actor.getLastName(), 
                "ACTOR"); 
        
         if (dbActor.isPresent()) {
           personnel.add(dbActor.get());
         } else {
           personnel.add(new Person(actor.getFirstName(),
                 actor.getLastName(), 
                 Role.ACTOR));
         }
       }

       Movie movie = dataMapper.mapToMovie(movieData, personnel);
       return dataMapper.mapToMovieData(movieRepository.save(movie));
    }
}