package com.blaj.beercatalogue.beerlist.model;

public class Beer {
    private final String name;
    private final String country;
    private final String type;
    private final String storage;
    private final String photo;

    public Beer(String name, String country, String type, String storage, String photo) {
        this.name = name;
        this.country = country;
        this.type = type;
        this.storage = storage;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
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
