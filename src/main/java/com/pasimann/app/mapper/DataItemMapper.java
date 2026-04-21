package com.pasimann.app.mapper;

public class DataItemMapper {

    public MovieItem mapToMovieItem(Movie movie) {
       List<PersonItem> personItems = mapToPersonItems(movie.getPersons());

        PersonItem director = personItems.stream().filter(
            p -> p.role().toUpperCase().equals(Role.DIRECTOR.name())).findFirst()
            .orElseThrow(() -> new RuntimeException("Director not found"));

        List<PersonItem> actors = personItems.stream().filter(
            p -> p.role().toUpperCase().equals(Role.ACTOR.name())).collect(Collectors.toList());

        return new MovieItem(
                movie.getTitle(), movie.getYear(), movie.getGenres(), movie.getAgeLimit(), 
                movie.getRating(), actors, director, movie.getSynopsis());
    }

    private List<PersonItem> mapToPersonItems(List<Person> persons) {
        List<PersonItem> results = new ArrayList<>();
        for(Person person : persons) {
            PersonItem item = new PersonItem(
                person.getFirstName(), person.getLastName(), person.getRole().name());
            results.add(item);
        }
        return results;
    }

    public Movie mapToMovie(MovieItem movieItem, List<Person> personel) {
        Movie result = new Movie(movieItem.name(), movieItem.year(), movieItem.ageLimit(), 
            movieItem.rating(), movieItem.synopsis(), movieItem.genres(), 
            new HashSet<>(personel));
        return result;
    }
}
