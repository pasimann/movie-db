package com.pasimann.app.api;

public class SearchRequest {
    String firstName;
    String lastName;

    // TODO other query options to search movies

    public SearchRequest() {
    }

    public SearchRequest(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
