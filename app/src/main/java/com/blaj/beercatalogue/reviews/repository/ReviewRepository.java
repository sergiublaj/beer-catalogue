package com.blaj.beercatalogue.reviews.repository;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.blaj.beercatalogue.accounts.ui.UserActivity;
import com.blaj.beercatalogue.reviews.model.Review;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ReviewRepository {
    private final List<Review> reviewList = new ArrayList<>();
    private static ReviewRepository INSTANCE = null;

    @SuppressWarnings("unchecked")
    private ReviewRepository() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance(UserActivity.DATABASE_URL).getReference().child("Reviews");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) return;

                fetchReviews((Map<String, HashMap<String, String>>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void fetchReviews(Map<String, HashMap<String, String>> beers) {
        for (Map.Entry<String, HashMap<String, String>> entry : beers.entrySet()){
            Map<String, String> entryMap = entry.getValue();

            String user = entryMap.get("user");
            String beer = entryMap.get("beer");
            String comment = entryMap.get("comment");
            String rating = entryMap.get("rating");
            String date = entryMap.get("date");

            Review crtReview = new Review(user, beer, comment, rating, date);

            reviewList.add(crtReview);
        }
    }

    public static ReviewRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReviewRepository();
        }
        return INSTANCE;
    }

    public void addReview(Review review, Context context) {
        reviewList.add(review);

        FirebaseDatabase.getInstance(UserActivity.DATABASE_URL).getReference("Reviews")
                .child(UUID.randomUUID().toString())
                .setValue(review).addOnCompleteListener(task -> {

            if (!task.isSuccessful()) {
                Toast.makeText(context, "Failed to add review!", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(context, "Review added!", Toast.LENGTH_SHORT).show();
        });
    }

    public List<Review> getReviewList() {
        return reviewList;
    }
}
