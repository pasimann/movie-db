package com.pasimann.app.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    public Optional<Person> findByFirstNameAndLastNameAndRole(
        String firstName, String lastName, String role);
}
