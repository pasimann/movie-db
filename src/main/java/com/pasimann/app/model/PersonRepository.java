package com.pasimann.app.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    // public List<Person> getAllPersonsByMovieId(Long id);
}
