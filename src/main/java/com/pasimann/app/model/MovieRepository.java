package com.pasimann.app.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    public List<Movie> getAllMovies();
}
