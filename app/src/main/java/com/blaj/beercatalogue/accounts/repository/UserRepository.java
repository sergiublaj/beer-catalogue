package com.blaj.beercatalogue.accounts.repository;

import androidx.annotation.NonNull;

import com.blaj.beercatalogue.accounts.models.User;
import com.blaj.beercatalogue.accounts.ui.UserActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserRepository {
    private final List<User> userList = new ArrayList<>();
    private static UserRepository INSTANCE = null;

    @SuppressWarnings("unchecked")
    private UserRepository() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance(UserActivity.DATABASE_URL).getReference().child("Users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) return;

                fetchUsers((Map<String, HashMap<String, String>>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void fetchUsers(Map<String, HashMap<String, String>> beers) {
        for (Map.Entry<String, HashMap<String, String>> entry : beers.entrySet()) {
            Map<String, String> entryMap = entry.getValue();

            FirebaseStorage.getInstance().getReference().child("users/" + Objects.requireNonNull(entryMap.get("username")).toLowerCase() + ".png")
                    .getDownloadUrl().addOnSuccessListener(uri -> {
                String id = entryMap.get("id");
                String name = entryMap.get("username");
                String email = entryMap.get("email");
                String birthdate = entryMap.get("birthdate");

                User user = new User(id, name, email, birthdate, uri.toString());
                userList.add(user);
            });
        }
    }

    public static UserRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository();
        }
        return INSTANCE;
    }

    public User getLoggedUser() {
        return userList.stream().filter(user -> user.getId().equals(FirebaseAuth.getInstance().getUid()))
                .findFirst().orElse(null);
    }

    public List<User> getUserList() {
        return userList;
    }
}
