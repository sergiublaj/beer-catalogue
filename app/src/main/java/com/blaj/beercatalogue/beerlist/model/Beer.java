package com.blaj.beercatalogue.beerlist.model;

import android.graphics.Bitmap;

public class Beer {
    private final String name;
    private final Bitmap photo;
    private final String country;
    private final String type;
    private final String storage;

    public Beer(String name, Bitmap photo, String country, String type, String storage) {
        this.name = name;
        this.photo = photo;
        this.country = country;
        this.type = type;
        this.storage = storage;
    }

    public String getName() {
        return name;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public String getCountry() {
        return country;
    }

    public String getType() {
        return type;
    }

    public String getStorage() {
        return storage;
    }
}
