package com.pasimann.app.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    public Optional<Person> findByFirstNameAndLastNameAndRole(
        String firstName, String lastName, Role role);
    public Optional<Person> findByFirstNameAndLastName(
            String firstName, String lastName);
}
