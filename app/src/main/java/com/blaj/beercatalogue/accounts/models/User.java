package com.blaj.beercatalogue.accounts.models;

import android.graphics.Bitmap;

public class User {
    private final String id;
    private final String username;
    private final String email;
    private Bitmap photo;

    public User(String id, String username, String email, Bitmap photo) {
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

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}
