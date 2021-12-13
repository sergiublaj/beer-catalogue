package com.blaj.beercatalogue.accounts.models;

public class User {
    private final String id;
    private final String username;
    private final String email;
    private final String birthdate;
    private final String photo;

    public User(String id, String username, String email, String birthdate, String photo) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.birthdate = birthdate;
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getPhoto() {
        return photo;
    }
}
