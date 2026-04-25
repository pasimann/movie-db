package com.pasimann.app.api;

import java.util.List;

public class MovieData {
    String uuid;
    String name;
    int year;
    List<String> genres;
    int ageLimit;
    int rating;
    List<PersonData> actors;
    PersonData director;
    String synopsis;

    public MovieData() {}

    public MovieData(String uuid, String name, int year, List<String> genres, int ageLimit, int rating, List<PersonData> actors, PersonData director, String synopsis) {
        this.uuid = uuid;
        this.name = name;
        this.year = year;
        this.genres = genres;
        this.ageLimit = ageLimit;
        this.rating = rating;
        this.actors = actors;
        this.director = director;
        this.synopsis = synopsis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public List<PersonData> getActors() {
        return actors;
    }

    public void setActors(List<PersonData> actors) {
        this.actors = actors;
    }

    public PersonData getDirector() {
        return director;
    }

    public void setDirector(PersonData director) {
        this.director = director;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getUuid() {
        return uuid;
    }
}