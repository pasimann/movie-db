package com.pasimann.app;

import java.util.List;

import com.pasimann.app.api.SearchRequest;
import com.pasimann.app.model.Role;
import com.pasimann.app.validator.MovieDataValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pasimann.app.api.MovieData;
import com.pasimann.app.service.MovieService;

@RequestMapping(path = "/movie-db/api")
@Controller
public class MovieDbController {

    MovieService service;
    MovieDataValidator movieDataValidator;

    public MovieDbController(MovieService service, MovieDataValidator movieDataValidator) {
        this.service = service;
        this.movieDataValidator = movieDataValidator;
    }

    @RequestMapping(value={"/get-all-movies"}, method=RequestMethod.GET)
    public @ResponseBody List<MovieData> getAllMovies() {
        return service.getAllMovies();
    }

    @RequestMapping(value={"/find-movie-by-person"}, method=RequestMethod.GET)
    public @ResponseBody List<MovieData> findMoviesByPerson(@RequestBody SearchRequest request) {
        return service.findMoviesByPerson(request);
    }

    @RequestMapping(value={"/find-movie-by-actor"}, method=RequestMethod.GET)
    public @ResponseBody List<MovieData> findMoviesByActor(@RequestBody SearchRequest request) {
        return service.findMoviesByRole(request, Role.ACTOR);
    }

    @RequestMapping(value={"/find-movie-by-director"}, method=RequestMethod.GET)
    public @ResponseBody List<MovieData> findMoviesByDirector(@RequestBody SearchRequest request) {
        return service.findMoviesByRole(request, Role.DIRECTOR);
    }

    @RequestMapping(value={"/add-new-movie"}, method=RequestMethod.POST)
    public @ResponseBody MovieData addNewMovie(@RequestBody MovieData data) {
        movieDataValidator.validate(data);
        return service.addNewMovie(data);
    }
}