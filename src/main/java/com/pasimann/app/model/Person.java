package com.pasimann.app.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String firstName;
    private String lastName;
    
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "persons")
    Set<Movie> movies = new HashSet<>();

    public Person() { }

    public Person(
        String firstName,
        String lastName,
        Role role) {
        this.uuid = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUuid() { return uuid; }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Role getRole() { return role; }

    public Set<Movie> getMovies() {
        return movies;
    }
}
