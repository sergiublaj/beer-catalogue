package com.blaj.beercatalogue.beerlist.service;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blaj.beercatalogue.R;
import com.blaj.beercatalogue.beerlist.model.Beer;
import com.blaj.beercatalogue.beerlist.ui.BeerActivity;
import com.blaj.beercatalogue.reviews.service.ReviewService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BeerListAdapter extends RecyclerView.Adapter<BeerListViewHolder> implements Filterable {
    private final List<Beer> beerList;
    private final List<Beer> beerListFiltered;
    private final Filter filter = initializeFilter();

    public BeerListAdapter(List<Beer> beerList) {
        this.beerList = beerList;
        this.beerListFiltered = new ArrayList<>(beerList);
    }

    private Filter initializeFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Beer> filteredList;

                if (constraint == null || constraint.toString().isEmpty()) {
                    filteredList = new ArrayList<>(beerList);
                } else {
                    filteredList = beerList.stream().filter(beer -> beer.getName().toLowerCase()
                            .contains(constraint.toString().toLowerCase())).collect(Collectors.toList());
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                beerListFiltered.clear();
                beerListFiltered.addAll((Collection<? extends Beer>) results.values);

                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public BeerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.beer_item, parent, false);

        return new BeerListViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BeerListViewHolder holder, int position) {
        Beer beer = beerListFiltered.get(position);

//        holder.getBeerPhoto().setImageBitmap(beer.getPhoto());
        holder.getBeerName().setText("Name: " + beer.getName());
        holder.getBeerType().setText("Type: " + beer.getType());
        holder.getBeerRating().setRating(ReviewService.getBeerRating(beer.getName()));

        holder.itemView.setOnClickListener(l -> {
            Intent beerPage = new Intent(holder.itemView.getContext(), BeerActivity.class);
            Beer foundBeer = beerList.stream().filter(b -> b.getName().equals(beer.getName())).findFirst().orElse(null);
            beerPage.putExtra("beer_id", beerList.indexOf(foundBeer));

            holder.itemView.getContext().startActivity(beerPage);
        });
    }

    @Override
    public int getItemCount() {
        return beerListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
}
