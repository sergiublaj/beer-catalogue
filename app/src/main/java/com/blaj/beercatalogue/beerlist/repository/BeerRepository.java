package com.blaj.beercatalogue.beerlist.repository;

import androidx.annotation.NonNull;

import com.blaj.beercatalogue.accounts.ui.UserActivity;
import com.blaj.beercatalogue.beerlist.model.Beer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BeerRepository {
    private final List<Beer> beerList = new ArrayList<>();
    private static BeerRepository INSTANCE = null;

    @SuppressWarnings("unchecked")
    private BeerRepository() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance(UserActivity.DATABASE_URL).getReference().child("Beers");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fetchBeers((Map<String, HashMap<String, String>>) Objects.requireNonNull(dataSnapshot.getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void fetchBeers(Map<String, HashMap<String, String>> beers) {
        for (Map.Entry<String, HashMap<String, String>> entry : beers.entrySet()){
            Map<String, String> entryMap = entry.getValue();

            String name = entryMap.get("name");
            float rating = Float.parseFloat(Objects.requireNonNull(entryMap.get("rating")));
            String country = entryMap.get("country");
            String type = entryMap.get("type");
            String storage = entryMap.get("storage");

            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            StorageReference fileReference = storageReference.child("beers/" + Objects.requireNonNull(name).toLowerCase() + ".png");
            fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                Beer crtBeer = new Beer(name, uri, rating, country, type, storage);

                beerList.add(crtBeer);
            });
        }
    }

    public static BeerRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BeerRepository();
        }
        return INSTANCE;
    }

    public List<Beer> getBeerList() {
        return beerList;
    }
}
