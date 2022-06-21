package com.example.farmbnb_cw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //home page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendToLogin(View loginView) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void sendContacts(View contactsView) {
        Intent contactIntent = new Intent(MainActivity.this, Contact_FarmBnB.class);
        startActivity(contactIntent);
    }
}