package com.blaj.beercatalogue.beerlist.service;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blaj.beercatalogue.R;

public class BeerListViewHolder extends RecyclerView.ViewHolder {
    private final BeerListAdapter beerListAdapter;
    private final ImageView beerPhoto;
    private final TextView beerName;
    private final TextView beerType;
    private final RatingBar beerRating;

    public BeerListViewHolder(@NonNull View itemView, BeerListAdapter beerListAdapter) {
        super(itemView);

        this.beerListAdapter = beerListAdapter;

        beerPhoto = itemView.findViewById(R.id.beer_photo);
        beerName = itemView.findViewById(R.id.beer_name);
        beerType = itemView.findViewById(R.id.beer_type);
        beerRating = itemView.findViewById(R.id.beer_rating);
    }

    public ImageView getBeerPhoto() {
        return beerPhoto;
    }

    public TextView getBeerName() {
        return beerName;
    }

    public TextView getBeerType() {
        return beerType;
    }

    public RatingBar getBeerRating() {
        return beerRating;
    }
}
