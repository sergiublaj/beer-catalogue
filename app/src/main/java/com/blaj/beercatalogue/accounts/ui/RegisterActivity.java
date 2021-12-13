package com.blaj.beercatalogue.accounts.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.blaj.beercatalogue.R;
import com.blaj.beercatalogue.accounts.models.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private EditText usernameField;
    private EditText emailField;
    private EditText birthdateField;
    private EditText photoField;
    private EditText passwordField;
    private Uri localPhoto;
    private ActivityResultLauncher<String> mGetContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressBar = findViewById(R.id.register_progress_bar);
        progressBar.setVisibility(View.GONE);

        firebaseAuth = FirebaseAuth.getInstance();

        Objects.requireNonNull(((TextInputLayout) findViewById(R.id.register_birthdate))
                .getEditText()).setInputType(InputType.TYPE_NULL);

        Objects.requireNonNull(((TextInputLayout) findViewById(R.id.register_photo))
                .getEditText()).setInputType(InputType.TYPE_NULL);

        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    localPhoto = uri;
                    photoField.setText(uri.toString());
                });

        usernameField = ((TextInputLayout) findViewById(R.id.register_username)).getEditText();
        emailField = ((TextInputLayout) findViewById(R.id.register_email)).getEditText();
        birthdateField = ((TextInputLayout) findViewById(R.id.register_birthdate)).getEditText();
        photoField = ((TextInputLayout) findViewById(R.id.register_photo)).getEditText();
        passwordField = ((TextInputLayout) findViewById(R.id.register_password)).getEditText();

        FirebaseAuth.getInstance().signOut();
    }

    public void setBirthdate(View view) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        if (!birthdateField.getText().toString().trim().isEmpty()) {
            String[] currentBirthdate = birthdateField.getText().toString().split("/");
            day = Integer.parseInt(currentBirthdate[0]);
            month = Integer.parseInt(currentBirthdate[1]) - 1;
            year = Integer.parseInt(currentBirthdate[2]);
        }

        DatePickerDialog.OnDateSetListener onDateSetListener = (view1, year1, month1, day1) -> {
            month1++;
            String date = ((day1 < 10) ? "0" + day1 : day1) + "/" + ((month1 < 10) ? "0" + month1 : month1) + "/" + year1;
            Objects.requireNonNull(((TextInputLayout) findViewById(R.id.register_birthdate)).getEditText()).setText(date);
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, android.R.style.Theme_Light_NoTitleBar,
                onDateSetListener, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }

    public void setPhoto(View view) {
        mGetContent.launch("image/*");
    }

    public void registerUser(View view) {
        String username = Objects.requireNonNull(usernameField).getText().toString().trim();
        String email = Objects.requireNonNull(emailField).getText().toString().trim();
        String birthdate = Objects.requireNonNull(birthdateField).getText().toString().trim();
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

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
            Date birthDate = dateFormat.parse(birthdate);
            Period period = Period.between(birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now());

            if (period.getYears() < 18 || period.getYears() == 18 && (period.getMonths() < Calendar.MONTH + 1 || period.getDays() < Calendar.DAY_OF_MONTH)) {
                birthdateField.setError("You must be over 18!");
                birthdateField.requestFocus();
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
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
                    if (!task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Failed to register!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    FirebaseStorage.getInstance().getReference().child("users/" + username + ".png")
                            .putFile(localPhoto).addOnCompleteListener(task2 -> {
                        if (!task2.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Failed to register!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        FirebaseUser loggedUser = FirebaseAuth.getInstance().getCurrentUser();

                        FirebaseStorage.getInstance().getReference().child("users/" + username + ".png")
                                .getDownloadUrl().addOnSuccessListener(uri -> {
                            User user = new User(Objects.requireNonNull(loggedUser).getUid(), username, email, birthdate, uri.toString());
                            FirebaseDatabase.getInstance(UserActivity.DATABASE_URL).getReference("Users")
                                    .child(Objects.requireNonNull(loggedUser).getUid())
                                    .setValue(user).addOnCompleteListener(task3 -> {

                                if (!task2.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Failed to register!", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                clearFields();

                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(RegisterActivity.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
                                firebaseAuth.signInWithEmailAndPassword(email, password);
                                startActivity(new Intent(this.getApplicationContext(), UserActivity.class));
                            });
                        });
                    });
                });
    }

    public void proceedToLogin(View view) {
        startActivity(new Intent(this.getApplicationContext(), LoginActivity.class));
    }

    private void clearFields() {
        usernameField.setText("");
        emailField.setText("");
        birthdateField.setText("");
        Objects.requireNonNull(photoField).setText("");
        passwordField.setText("");
    }
}