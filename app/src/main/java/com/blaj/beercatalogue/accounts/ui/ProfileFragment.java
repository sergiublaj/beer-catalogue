package com.blaj.beercatalogue.accounts.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.blaj.beercatalogue.accounts.models.User;
import com.blaj.beercatalogue.accounts.repository.UserRepository;
import com.blaj.beercatalogue.databinding.FragmentProfileBinding;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        UserRepository userRepository = UserRepository.getInstance();
        User user = userRepository.getUser();

        if (user == null) {
            binding.profileProgressBar.setVisibility(View.VISIBLE);
        } else {
            Picasso.get().load(user.getPhoto()).into(binding.profilePhoto);
            binding.profileName.setText("Name: " + user.getUsername());
            binding.profileEmail.setText("Email: " + user.getEmail());
            binding.profileProgressBar.setVisibility(View.GONE);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}