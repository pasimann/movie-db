package com.pasimann.app.api;

public record MovieItem(
String name, 
int year, 
List<String> genres, 
int ageLimit, 
int rating; 
List<PersonItem> actors; 
PersonItem director, 
String synopsis) {

}