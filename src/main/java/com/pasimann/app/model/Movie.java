package com.pasimann.app.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private final String name;
    private final int year;
    private final int ageLimit;
    private final int rating;
    private final String synopsis;
    
    @ElementCollection
    @Column(name="genres", nullable=false)
    @CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"))
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
        this.persons = new HashSet<>(persons);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public int getYear() { return year; }
    public int getAgeLimit() { return ageLimit; }
    public int getRating() { return rating; }
    public String getSynopsis() { return synopsis; }
    public List<String> getGenres() { return genres; }
    public Set<Person> getPersons() { return persons; }
  }
