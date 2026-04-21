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

    @OneToMany
    @JoinColumn(name = "movie_id")
    private List<Person> actors;

    // One director for each movie in this model
    // We could have different actor and director tables, 
    // but then the actor star might want to direct the movie...
    @JoinColumn(name = "movie_id")
    private Person director;

    public Movie(String name, int year, int ageLimit, int rating, String synopsis, 
                  List<String> genres, List<Person> actors, Person director) {
        this.name = name;
        this.year = year;
        this.ageLimit = ageLimit;
        this.rating = rating;
        this.synopsis = synopsis;
        this.genres = genres;
        this.actors = actors;
        this.director = director;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public int getYear() { return year; }
    public int getAgeLimit() { return ageLimit; }
    public int getRating() { return rating; }
    public String getSynopsis() { return synopsis; }
    public List<String> getGenres() { return genres; }
    public List<Person> getActors() { return actors; }
    public Person getDirector() { return director; }
  }
