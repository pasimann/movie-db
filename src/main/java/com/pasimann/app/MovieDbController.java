package com.pasimann.app;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pasimann.app.api.MovieItem;
import com.pasimann.app.service.MovieService;

@Controller
public class MovieDbController {

    MovieService service;

    public MovieDbController(MovieService service) {
        this.service = service; 
    }

    @RequestMapping(value={"/get-all-movies"}, method=RequestMethod.GET)
    public @ResponseBody List<MovieItem> getAllMovies() {
        List<MovieItem> result = service.getAllMovies();
        return result;
    }


}