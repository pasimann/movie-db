package com.pasimann.app.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int year;
    private int ageLimit;
    private int rating;
    private String synopsis;

    @ElementCollection
    private List<String> genres;

    @ManyToMany
    @JoinTable(
        name = "movie_person",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private Set<Person> persons;

    public Movie(String name, int year, int ageLimit, int rating, String synopsis, 
                  List<String> genres, List<Person> persons) {
        this.name = name;
        this.year = year;
        this.ageLimit = ageLimit;
        this.rating = rating;
        this.synopsis = synopsis;
        this.genres = genres;
        this.persons = persons;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public int getYear() { return year; }
    public int getAgeLimit() { return ageLimit; }
    public int getRating() { return rating; }
    public String getSynopsis() { return synopsis; }
    public List<String> getGenres() { return genres; }
    public List<Person> getPersons() { return persons; }
  }
