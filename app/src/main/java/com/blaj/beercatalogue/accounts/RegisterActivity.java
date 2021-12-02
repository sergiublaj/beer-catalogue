package com.blaj.beercatalogue.accounts;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blaj.beercatalogue.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        String name = getIntent().getStringExtra("name");
        String pass = getIntent().getStringExtra("pass");

        ((TextView)findViewById(R.id.text_register)).setText(name + " " + pass);
    }
}