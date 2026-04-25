package com.pasimann.app.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String name;
    private int releaseYear;
    private int ageLimit;
    private int rating;
    private String synopsis;
    
    @ElementCollection
    @Column(name="genres", nullable=false)
    @CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"))
    private List<String> genres;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "movie_person",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private Set<Person> persons;

    public Movie() { }

    public Movie(String name, int releaseYear, int ageLimit, int rating, String synopsis,
                 List<String> genres, List<Person> persons) {
        this.uuid = UUID.randomUUID().toString();
        this.name = name;
        this.releaseYear = releaseYear;
        this.ageLimit = ageLimit;
        this.rating = rating;
        this.synopsis = synopsis;
        this.genres = genres;
        this.persons = new HashSet<>(persons);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUuid() { return uuid; }
    public String getName() { return name; }
    public int getReleaseYear() { return releaseYear; }
    public int getAgeLimit() { return ageLimit; }
    public int getRating() { return rating; }
    public String getSynopsis() { return synopsis; }
    public List<String> getGenres() { return genres; }
    public Set<Person> getPersons() { return persons; }
  }
