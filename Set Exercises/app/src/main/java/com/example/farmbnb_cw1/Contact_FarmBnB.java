package com.example.farmbnb_cw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Contact_FarmBnB extends AppCompatActivity {
    //contacts page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_farm_bnb);
    }

    public void goHome(View homeView) {
        Intent homeIntent = new Intent(Contact_FarmBnB.this, MainActivity.class);
        startActivity(homeIntent);
    }
}