package com.pasimann.app;

import java.util.List;

import com.pasimann.app.api.SearchRequest;
import com.pasimann.app.model.Role;
import com.pasimann.app.validator.MovieDataValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.pasimann.app.api.MovieData;
import com.pasimann.app.service.MovieService;

@RequestMapping(path = "/movie-db/api")
@RestController
public class MovieDbController {

    MovieService service;
    MovieDataValidator movieDataValidator;

    public MovieDbController(MovieService service, MovieDataValidator movieDataValidator) {
        this.service = service;
        this.movieDataValidator = movieDataValidator;
    }

    @GetMapping(value={"/get-all"})
    public @ResponseBody List<MovieData> getAllMovies() {
        return service.getAllMovies();
    }

    @GetMapping(value={"/find-by-person"})
    public @ResponseBody List<MovieData> findMoviesByPerson(@RequestBody SearchRequest request) {
        return service.findMoviesByPerson(request);
    }

    @GetMapping(value={"/find-by-actor"})
    public @ResponseBody List<MovieData> findMoviesByActor(@RequestBody SearchRequest request) {
        return service.findMoviesByRole(request, Role.ACTOR);
    }

    @GetMapping(value={"/find-by-director"})
    public @ResponseBody List<MovieData> findMoviesByDirector(@RequestBody SearchRequest request) {
        return service.findMoviesByRole(request, Role.DIRECTOR);
    }

    @PostMapping(value={"/add-new"})
    public @ResponseBody MovieData addNewMovie(@RequestBody MovieData data) {
        movieDataValidator.validate(data);
        return service.addNewMovie(data);
    }
}