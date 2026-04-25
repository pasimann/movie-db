package com.pasimann.app.api;

import java.util.UUID;

public class PersonData {

    String uuid;
    String firstName;
    String lastName;
    String role;

    public PersonData() {}

    public PersonData(String uuid, String firstName, String lastName, String role) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.uuid = UUID.randomUUID().toString();
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUuid() {
        return uuid;
    }
}

