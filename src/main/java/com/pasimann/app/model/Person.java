package com.pasimann.app.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private final String firstName;
    private final String lastName;
    
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "persons")
    Set<Movie> movies;

    public Person(
        String firstName,
        String lastName,
        Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Role getRole() { return role; }

    public Set<Movie> getMovies() {
        return movies;
    }
}
