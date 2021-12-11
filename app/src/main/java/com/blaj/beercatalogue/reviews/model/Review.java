package com.blaj.beercatalogue.reviews.model;

public class Review {
    private final String user;
    private final String beer;
    private final String comment;
    private final String rating;
    private final String date;

    public Review(String user, String beer, String comment, String rating, String date) {
        this.user = user;
        this.beer = beer;
        this.comment = comment;
        this.rating = rating;
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public String getBeer() {
        return beer;
    }

    public String getComment() {
        return comment;
    }

    public String getRating() {
        return rating;
    }

    public String getDate() {
        return date;
    }
}
