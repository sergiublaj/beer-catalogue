package com.blaj.beercatalogue.reviews.businesslogic;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;

import com.blaj.beercatalogue.reviews.model.Review;

import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;

public class ReviewSorter implements Comparator<Review> {
    @SuppressLint("SimpleDateFormat")
    @Override
    public int compare(Review review1, Review review2) {
        try {
             Date date1 = new SimpleDateFormat("HH:mm dd/MM/yyyy").parse(review1.getDate());
             Date date2 = new SimpleDateFormat("HH:mm dd/MM/yyyy").parse(review2.getDate());

             return -date1.compareTo(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
