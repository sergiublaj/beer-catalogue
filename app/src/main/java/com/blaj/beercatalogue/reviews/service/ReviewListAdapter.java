package com.blaj.beercatalogue.reviews.service;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blaj.beercatalogue.R;
import com.blaj.beercatalogue.accounts.models.User;
import com.blaj.beercatalogue.accounts.repository.UserRepository;
import com.blaj.beercatalogue.beerlist.model.Beer;
import com.blaj.beercatalogue.beerlist.repository.BeerRepository;
import com.blaj.beercatalogue.reviews.model.Review;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListViewHolder> {
    public static final int USER_REVIEW = 0;
    public static final int BEER_REVIEW = 1;

    private final List<Review> reviewList;
    private final int reviewType;

    public ReviewListAdapter(List<Review> reviewList, int reviewType) {
        this.reviewList = reviewList;
        this.reviewType = reviewType;
    }

    @NonNull
    @Override
    public ReviewListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);

        return new ReviewListViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReviewListViewHolder holder, int position) {
        Review review = reviewList.get(position);

        User user = UserRepository.getInstance().getUserList().stream().filter(u -> u.getUsername().equals(review.getUser())).findFirst().orElse(null);
        Beer beer = BeerRepository.getInstance().getBeerList().stream().filter(b -> b.getName().equals(review.getBeer())).findFirst().orElse(null);

        if (reviewType == USER_REVIEW) {
            Picasso.get().load(Uri.parse(Objects.requireNonNull(beer).getPhoto())).into(holder.getReviewPhoto());
            holder.getReviewTitle().setText(Objects.requireNonNull(beer).getName());
        } else {
            Picasso.get().load(Uri.parse(Objects.requireNonNull(user).getPhoto())).into(holder.getReviewPhoto());
            holder.getReviewTitle().setText(Objects.requireNonNull(user).getUsername() + "'s review");
        }

        holder.getReviewRating().setRating(Float.parseFloat(review.getRating()));
        holder.getReviewDate().setText("Date: " + review.getDate());
        holder.getReviewComment().setText("\"" + review.getComment() + "\"");
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}
