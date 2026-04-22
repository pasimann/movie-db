package com.pasimann.app.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    public List<Movie> getAllMovies();
}
