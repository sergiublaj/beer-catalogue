package com.blaj.beercatalogue.accounts.repository;

import com.blaj.beercatalogue.accounts.models.User;
import com.blaj.beercatalogue.accounts.ui.UserActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserRepository {
    private User user = null;
    private static UserRepository INSTANCE = null;

    @SuppressWarnings("unchecked")
    private UserRepository() {
        FirebaseUser loggedUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance(UserActivity.DATABASE_URL).getReference("Users").child(Objects.requireNonNull(loggedUser).getUid()).get().addOnCompleteListener(task -> {
            Map<String, String> userMap = (HashMap<String, String>) Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getValue());

            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            StorageReference fileReference = storageReference.child("users/" + loggedUser.getUid() + "/avatar.png");
            fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                String username = userMap.get("username");
                String email = userMap.get("email");

                user = new User(username, email, uri);
            });
        });
    }

    public static UserRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository();
        }
        return INSTANCE;
    }

    public User getUser() {
        return user;
    }
}
