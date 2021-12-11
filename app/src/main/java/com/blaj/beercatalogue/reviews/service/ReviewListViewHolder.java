package com.blaj.beercatalogue.reviews.service;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blaj.beercatalogue.R;

public class ReviewListViewHolder extends RecyclerView.ViewHolder {
    private final ImageView reviewPhoto;
    private final RatingBar reviewRating;
    private final TextView reviewTitle;
    private final TextView reviewDate;
    private final TextView reviewComment;

    public ReviewListViewHolder(@NonNull View itemView) {
        super(itemView);

        reviewPhoto = itemView.findViewById(R.id.reviewitem_photo);
        reviewRating = itemView.findViewById(R.id.reviewitem_rating);
        reviewTitle = itemView.findViewById(R.id.reviewitem_title);
        reviewDate = itemView.findViewById(R.id.reviewitem_date);
        reviewComment = itemView.findViewById(R.id.reviewitem_comment);
    }

    public ImageView getReviewPhoto() {
        return reviewPhoto;
    }

    public RatingBar getReviewRating() {
        return reviewRating;
    }

    public TextView getReviewTitle() {
        return reviewTitle;
    }

    public TextView getReviewDate() {
        return reviewDate;
    }

    public TextView getReviewComment() {
        return reviewComment;
    }
}
