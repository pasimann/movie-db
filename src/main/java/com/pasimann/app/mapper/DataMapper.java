package com.pasimann.app.mapper;

import com.pasimann.app.api.MovieData;
import com.pasimann.app.api.PersonData;
import com.pasimann.app.model.Movie;
import com.pasimann.app.model.Person;
import com.pasimann.app.model.Role;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataMapper {

    public MovieData mapToMovieData(Movie movie) {
       List<PersonData> personData = mapToPersonData(movie.getPersons().stream().toList());

        PersonData director = personData.stream().filter(
            p -> p.getRole().toUpperCase().equals(Role.DIRECTOR.name())).findFirst().get();
        
        List<PersonData> actors = personData.stream().filter(
            p -> p.getRole().toUpperCase().equals(Role.ACTOR.name())).collect(Collectors.toList());

        return new MovieData(movie.getUuid(),
                movie.getName(), movie.getReleaseYear(), movie.getGenres(), movie.getAgeLimit(),
                movie.getRating(), actors, director, movie.getSynopsis());
    }

    private List<PersonData> mapToPersonData(List<Person> persons) {
        List<PersonData> results = new ArrayList<>();
        for(Person person : persons) {
            PersonData data = new PersonData(person.getUuid(),
                person.getFirstName(), person.getLastName(), person.getRole().name());
            results.add(data);
        }
        return results;
    }

    public Movie mapToMovie(MovieData movieData, List<Person> persons) {
        return new Movie(movieData.getName(), movieData.getYear(), movieData.getAgeLimit(),
            movieData.getRating(), movieData.getSynopsis(), movieData.getGenres(),
                persons);
    }
}
