package com.blaj.beercatalogue.accounts.models;

public class User {
    private final String id;
    private final String username;
    private final String email;
    private final String photo;

    public User(String id, String username, String email, String photo) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.photo = photo;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoto() {
        return photo;
    }
}
