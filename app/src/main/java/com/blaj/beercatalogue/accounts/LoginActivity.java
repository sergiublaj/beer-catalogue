package com.blaj.beercatalogue.accounts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.blaj.beercatalogue.R;
import com.blaj.beercatalogue.about.InfoActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void proceedToRegister(View view) {
        Intent i = new Intent(this.getApplicationContext(), RegisterActivity.class);

        String name = Objects.requireNonNull(((TextInputLayout) findViewById(R.id.login_username)).getEditText()).getText().toString();
        String password = Objects.requireNonNull(((TextInputLayout) findViewById(R.id.login_password)).getEditText()).getText().toString();

        i.putExtra("name", name);
        i.putExtra("pass", password);
        startActivity(i);
//        startActivity(new Intent(this.getApplicationContext(), RegisterActivity.class));
    }

    public void proceedToAbout(View view) {
        startActivity(new Intent(this.getApplicationContext(), InfoActivity.class));
    }
}