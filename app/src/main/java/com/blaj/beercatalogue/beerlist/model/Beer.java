package com.blaj.beercatalogue.beerlist.model;

import android.net.Uri;

public class Beer {
    private final String name;
    private final Uri photo;
    private final float rating;
    private final String country;
    private final String type;
    private final String storage;

    public Beer(String name, Uri photo, float rating, String country, String type, String storage) {
        this.name = name;
        this.photo = photo;
        this.rating = rating;
        this.country = country;
        this.type = type;
        this.storage = storage;
    }

    public String getName() {
        return name;
    }

    public Uri getPhoto() {
        return photo;
    }

    public float getRating() {
        return rating;
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

    @Override
    public String toString() {
        return "Beer{" +
                "name='" + name + '\'' +
                ", photo=" + photo +
                ", rating=" + rating +
                ", country='" + country + '\'' +
                ", type='" + type + '\'' +
                ", storage='" + storage + '\'' +
                '}';
    }
}
