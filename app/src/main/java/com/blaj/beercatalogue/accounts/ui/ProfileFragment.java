package com.blaj.beercatalogue.accounts.ui;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blaj.beercatalogue.R;
import com.blaj.beercatalogue.accounts.models.User;
import com.blaj.beercatalogue.accounts.repository.UserRepository;
import com.blaj.beercatalogue.databinding.FragmentProfileBinding;
import com.blaj.beercatalogue.reviews.model.Review;
import com.blaj.beercatalogue.reviews.repository.ReviewRepository;
import com.blaj.beercatalogue.reviews.service.ReviewListAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.stream.Collectors;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private User user;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        UserRepository userRepository = UserRepository.getInstance();
        user = userRepository.getLoggedUser();

        if (user == null) {
            binding.profileProgressBar.setVisibility(View.VISIBLE);
            return root;
        }

        fillUserDetails();

        fillUserReviews();

        return root;
    }

    @SuppressLint("SetTextI18n")
    private void fillUserDetails() {
        Picasso.get().load(Uri.parse(user.getPhoto())).into(binding.profilePhoto);
        binding.profileName.setText("Name: " + user.getUsername());
        binding.profileEmail.setText("Email: " + user.getEmail());
        binding.profileProgressBar.setVisibility(View.GONE);
    }

    private void fillUserReviews() {
        List<Review> reviewList = ReviewRepository.getInstance().getReviewList().stream()
                .filter(review -> review.getUser().equals(user.getUsername())).collect(Collectors.toList());

        TextView reviewListTitle = binding.profileReviewTitle;

        if (reviewList.isEmpty()) {
            reviewListTitle.setText(R.string.reviews_none);
            return;
        }

        reviewListTitle.setText(R.string.reviews_user);

        RecyclerView reviewListUI = binding.profileReviewlist;

        ReviewListAdapter reviewListAdapter = new ReviewListAdapter(reviewList, ReviewListAdapter.USER_REVIEW);

        reviewListUI.setAdapter(reviewListAdapter);
        reviewListUI.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}