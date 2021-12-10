package com.blaj.beercatalogue.beerlist.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blaj.beercatalogue.R;
import com.blaj.beercatalogue.beerlist.model.Beer;
import com.blaj.beercatalogue.beerlist.repository.BeerRepository;
import com.squareup.picasso.Picasso;

public class BeerActivity extends AppCompatActivity {
    private Beer beer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer);

        int beerId = getIntent().getIntExtra("beer_id", -1);
        if (beerId == -1) {
            return;
        }

        beer = BeerRepository.getInstance().getBeerList().get(beerId);
        fillBeerDetails();
    }

    @SuppressLint("SetTextI18n")
    private void fillBeerDetails() {
        Picasso.get().load(beer.getPhoto()).into((ImageView) findViewById(R.id.beeritem_photo));

        ((TextView) findViewById(R.id.beeritem_name)).setText("Name: " + beer.getName());
        ((TextView) findViewById(R.id.beeritem_type)).setText("Type: " + beer.getType());
        ((RatingBar) findViewById(R.id.beeritem_rating)).setRating(beer.getRating());
        ((TextView) findViewById(R.id.beeritem_country)).setText("Country: " + beer.getCountry());
        ((TextView) findViewById(R.id.beeritem_storage)).setText("Storage: " + beer.getStorage());
    }

}
