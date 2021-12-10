package com.blaj.beercatalogue.accounts.models;

import android.net.Uri;

public class User {
    private final String username;
    private final String email;
    private final Uri photo;

    public User(String username, String email, Uri photo) {
        this.username = username;
        this.email = email;
        this.photo = photo;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Uri getPhoto() {
        return photo;
    }
}
