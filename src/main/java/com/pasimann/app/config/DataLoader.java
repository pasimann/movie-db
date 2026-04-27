package com.pasimann.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pasimann.app.model.Movie;
import com.pasimann.app.model.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.List;

@Configuration
public class DataLoader {

    private static class TestData {
        private List<Movie> movies;

        public List<Movie> getMovies() {
            return movies;
        }
        public void setMovies(List<Movie> movies) {
            this.movies = movies;
        }
    }

  @Bean
  public CommandLineRunner movieDataLoader(MovieRepository movieRepository) {
    ObjectMapper objectMapper = new ObjectMapper();

    return args -> {
      try (InputStream inputStream = new ClassPathResource("data.json").getInputStream()) {
          TestData movies = objectMapper.readValue(inputStream, TestData.class);
        movies.getMovies().forEach(movieRepository::save);
      }
    };
  }
}