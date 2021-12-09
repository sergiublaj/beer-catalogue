package com.blaj.beercatalogue.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.blaj.beercatalogue.R;
import com.blaj.beercatalogue.accounts.UserActivity;
import com.blaj.beercatalogue.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private HashMap<String, String> user;

    @SuppressWarnings("all")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.userProgressBar.setVisibility(View.VISIBLE);

        FirebaseUser loggedUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance(UserActivity.DATABASE_URL).getReference("Users").child(Objects.requireNonNull(loggedUser).getUid()).get().addOnCompleteListener(task -> {
            user = (HashMap<String, String>) Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getValue());

            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            StorageReference fileReference = storageReference.child("users/" + loggedUser.getUid() + "/avatar.png");
            fileReference.getDownloadUrl().addOnSuccessListener(task1 -> {
                Picasso.get().load(task1).into(binding.userPhoto);
                binding.userProgressBar.setVisibility(View.GONE);

                binding.nameText.setText(R.string.user_name);
                binding.nameValue.setText(user.get("username"));

                binding.emailText.setText(R.string.user_email);
                binding.emailValue.setText(user.get("email"));
            });
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}