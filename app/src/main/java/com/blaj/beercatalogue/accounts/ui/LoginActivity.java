package com.blaj.beercatalogue.accounts.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blaj.beercatalogue.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.login_progress_bar);
        progressBar.setVisibility(View.GONE);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void loginUser(View view) {
        EditText emailField = ((TextInputLayout)findViewById(R.id.login_email)).getEditText();
        EditText passwordField = ((TextInputLayout)findViewById(R.id.login_password)).getEditText();

        String email = Objects.requireNonNull(emailField).getText().toString().trim();
        String password = Objects.requireNonNull(passwordField).getText().toString().trim();

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

        if (password.isEmpty()) {
            passwordField.setError("Password is required!");
            passwordField.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);

                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Failed to log in!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    emailField.setText("");
                    passwordField.setText("");

                    Toast.makeText(LoginActivity.this, "Successfully logged in!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this.getApplicationContext(), UserActivity.class));
                });
    }

    public void proceedToRegister(View view) {
        startActivity(new Intent(this.getApplicationContext(), RegisterActivity.class));
    }
}