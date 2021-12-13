package com.blaj.beercatalogue.beerlist.ui;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blaj.beercatalogue.R;
import com.blaj.beercatalogue.accounts.repository.UserRepository;
import com.blaj.beercatalogue.accounts.ui.UserActivity;
import com.blaj.beercatalogue.beerlist.model.Beer;
import com.blaj.beercatalogue.beerlist.repository.BeerRepository;
import com.blaj.beercatalogue.reviews.model.Review;
import com.blaj.beercatalogue.reviews.repository.ReviewRepository;
import com.blaj.beercatalogue.reviews.service.ReviewListAdapter;
import com.blaj.beercatalogue.reviews.service.ReviewService;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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

        fillBeerReviews();
    }

    @SuppressLint("SetTextI18n")
    private void fillBeerDetails() {
        Picasso.get().load(Uri.parse(beer.getPhoto())).into((ImageView) findViewById(R.id.beer_photo));
        ((TextView) findViewById(R.id.beer_name)).setText("Name: " + beer.getName());
        ((TextView) findViewById(R.id.beer_type)).setText("Type: " + beer.getType());
        ((RatingBar) findViewById(R.id.beer_rating)).setRating(ReviewService.getBeerRating(beer.getName()));
        ((TextView) findViewById(R.id.beer_country)).setText("Country: " + beer.getCountry());
        ((TextView) findViewById(R.id.beer_storage)).setText("Storage: " + beer.getStorage());
    }

    private void fillBeerReviews() {
        List<Review> reviewList = ReviewRepository.getInstance().getReviewList().stream()
                .filter(review -> review.getBeer().equals(beer.getName())).collect(Collectors.toList());

        TextView reviewListTitle = findViewById(R.id.beeritem_reviews_title);

        if (reviewList.isEmpty()) {
            reviewListTitle.setText(R.string.reviews_none);
            return;
        }

        reviewListTitle.setText(R.string.reviews_all);

        RecyclerView reviewListUI = findViewById(R.id.beer_reviewlist);

        ReviewListAdapter reviewListAdapter = new ReviewListAdapter(reviewList, ReviewListAdapter.BEER_REVIEW);

        reviewListUI.setAdapter(reviewListAdapter);
        reviewListUI.setLayoutManager(new LinearLayoutManager(this));
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addReview(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Review");

        RatingBar reviewRating = new RatingBar(this);
        reviewRating.setNumStars(5);
        reviewRating.setRating(5.0f);
        reviewRating.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        EditText reviewComment = new EditText(this);
        reviewComment.setHint("Your review goes here.");

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(reviewRating);
        linearLayout.addView(reviewComment);

        alertDialog.setView(linearLayout);

        alertDialog.setPositiveButton("Add", (dialogInterface, i) -> {
            String userName = UserRepository.getInstance().getLoggedUser().getUsername();
            String beerName = beer.getName();
            String comment = reviewComment.getText().toString();
            String rating = reviewRating.getRating() + "";
            String date = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault()).format(new Date());

            if (comment.trim().isEmpty()) {
                Toast.makeText(this, "Please fill in required fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            Review newReview = new Review(userName, beerName, comment, rating, date);

            ReviewRepository.getInstance().addReview(newReview, this);

            fillBeerDetails();

            fillBeerReviews();

            UserActivity.beerListAdapter.notifyDataSetChanged();
        });

        alertDialog.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

        alertDialog.show();
    }

}
