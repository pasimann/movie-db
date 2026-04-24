
# Movie DB 

## INFO 
- Springboot application, Java 21, Maven 3, H2 database 

## Testing

```bash
$ mvn spring-boot:run 

$ curl --header "Content-Type: application/json" \
--request POST \
--data '{"name":"Avengers: Endgame","year":2018,"genres":["Adventure","Sci-fi"],"ageLimit":12,"rating":4,"actors":[{"firstName":"Robert","lastName":" Downey Jr."},{"firstName":"Chris","lastName":"Evans"},{"firstName":"Scarlett","lastName":"Johansson"}],"director":{"firstName":"Anthony","lastName":"Russo"},"synopsis":"Marvel movie."}' \
http://localhost:8080/movie-db/api/add-new

$ curl localhost:8080/movie-db/api/get-all
```

## TODO

- More REST API methods
- More Integration tests with H2 database
- More Unit tests
- Pom dependency versions (CVE warnings from IDE)
- Data model issues: multiple directors (brothers), director-actor, re-use genres like persons, etc.
- Check: M:N relation Person-Movie persisting is tricky 