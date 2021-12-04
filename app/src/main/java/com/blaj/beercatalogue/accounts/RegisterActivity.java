package com.blaj.beercatalogue.accounts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blaj.beercatalogue.R;
import com.blaj.beercatalogue.accounts.models.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressBar = findViewById(R.id.register_progress_bar);
        progressBar.setVisibility(View.GONE);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void registerUser(View view) {
        EditText usernameField = ((TextInputLayout)findViewById(R.id.register_username)).getEditText();
        EditText emailField = ((TextInputLayout)findViewById(R.id.register_email)).getEditText();
        EditText ageField = ((TextInputLayout)findViewById(R.id.register_age)).getEditText();
        EditText passwordField = ((TextInputLayout)findViewById(R.id.register_password)).getEditText();

        String username = Objects.requireNonNull(usernameField).getText().toString().trim();
        String email = Objects.requireNonNull(emailField).getText().toString().trim();
        String age = Objects.requireNonNull(ageField).getText().toString().trim();
        String password = Objects.requireNonNull(passwordField).getText().toString().trim();

        if (username.isEmpty()) {
            usernameField.setError("Username is required!");
            usernameField.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            emailField.setError("Email is required!");
            emailField.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailField.setError("Please enter a valid email!");
            emailField.requestFocus();
            return;
        }

        if (age.isEmpty()) {
            ageField.setError("Age is required!");
            ageField.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordField.setError("Password is required!");
            passwordField.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passwordField.setError("Please insert a longer password!");
            passwordField.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);

                    if (!task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Failed to register!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    User user = new User(username, email, Integer.parseInt(age));
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                            .setValue(user)
                            .addOnCompleteListener(task1 -> {
                                System.out.println("---------------");
                                System.out.println(user);
                                System.out.println("---------------");

                                if (!task1.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Failed to register!", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                Toast.makeText(RegisterActivity.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
                            });
                });
    }

    public void proceedToLogin(View view) {
        startActivity(new Intent(this.getApplicationContext(), LoginActivity.class));
    }
}