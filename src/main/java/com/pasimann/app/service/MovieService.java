package com.pasimann.app.service;

import java.util.*;

import com.pasimann.app.api.SearchRequest;
import com.pasimann.app.api.PersonData;
import com.pasimann.app.mapper.DataMapper;
import com.pasimann.app.model.*;
import org.springframework.stereotype.Service;

import com.pasimann.app.api.MovieData;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieService {

   private final MovieRepository movieRepository;
   private final PersonRepository personRepository;
   private final DataMapper dataMapper;

    public MovieService(MovieRepository movieRepository, PersonRepository personRepository,
                        DataMapper dataMapper) {
         this.movieRepository = movieRepository;
         this.personRepository = personRepository;
         this.dataMapper = dataMapper;
    }

    @Transactional(readOnly = true)
    public List<MovieData> findMoviesByRole(SearchRequest request, Role role) {
        List<MovieData> results = new ArrayList<>();
        Optional<Person> person = personRepository.findByFirstNameAndLastNameAndRole(
                request.getFirstName(), request.getLastName(), role);
        if (person.isPresent()) {
            List<Movie> movies = person.get().getMovies().stream().toList();
            for (Movie movie : movies) {
                results.add(dataMapper.mapToMovieData(movie));
            }
        }
        return results;
    }

    @Transactional(readOnly = true)
    public List<MovieData> findMoviesByPerson(SearchRequest request) {
        List<MovieData> result = new ArrayList<>();
        Optional<Person> person = personRepository.findByFirstNameAndLastName(request.getFirstName(), request.getLastName());

        if (person.isPresent()) {
            List<Movie> movies = person.get().getMovies().stream().toList();
            for (Movie m : movies) {
                result.add(dataMapper.mapToMovieData(m));
            }
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<MovieData> getAllMovies() {
      List<MovieData> results = new ArrayList<>();
      List<Movie> movies = movieRepository.findAll();

      for (Movie movie : movies) {
          results.add(dataMapper.mapToMovieData(movie));
      }
      return results;
    }

    @Transactional
    public MovieData addNewMovie(MovieData movieData) {
        // Persons (actor, director) persisted to the db
        // as a new person only if not already in the db

        List<Person> personnel = new ArrayList<>();

        Optional<Person> dbDirector = personRepository
            .findByFirstNameAndLastNameAndRole(
                movieData.getDirector().getFirstName(),
                movieData.getDirector().getLastName(),
                Role.DIRECTOR); 
        
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
                Role.ACTOR); 
        
         if (dbActor.isPresent()) {
           personnel.add(dbActor.get());
         } else {
           personnel.add(new Person(actor.getFirstName(),
                 actor.getLastName(), 
                 Role.ACTOR));
         }
       }

       // TODO check logic
       // M:N relation needs to be set on both sides
       Movie movie = dataMapper.mapToMovie(movieData, Collections.emptyList());
       Movie newMovie = movieRepository.save(movie);

       personnel.forEach(p -> {
           p.getMovies().add(newMovie);
           personRepository.save(p);
       });
       newMovie.getPersons().addAll(personnel);
       return dataMapper.mapToMovieData(newMovie);
    }
}