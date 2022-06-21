package com.example.farmbnb_cw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class Booking_Confirmation extends AppCompatActivity {

    public static ArrayList<String> BookingDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);
        //this is just to pass through the full booking from the previous activity, confirm booking details
        Intent confBookingDetails = getIntent();
        BookingDetails = confBookingDetails.getStringArrayListExtra("bookingcomplete");

    }

    public void returnHome (View finishBookingView) {
        Intent returnHomeIntent = new Intent(Booking_Confirmation.this, Booking_History.class);
        returnHomeIntent.putStringArrayListExtra("bookingcomplete", BookingDetails);
        startActivity(returnHomeIntent);
    }
}