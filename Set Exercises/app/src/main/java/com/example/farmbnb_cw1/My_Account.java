package com.example.farmbnb_cw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class My_Account extends AppCompatActivity {

    //my account page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
    }

    public void viewHistory(View historyView) {
        Intent historyIntent = new Intent(My_Account.this, Booking_History.class);
        startActivity(historyIntent);
    }

    public void bookRoom(View bookView) {
        Intent bookIntent = new Intent(My_Account.this, Enter_Customer_Details.class);
        startActivity(bookIntent);
    }
}