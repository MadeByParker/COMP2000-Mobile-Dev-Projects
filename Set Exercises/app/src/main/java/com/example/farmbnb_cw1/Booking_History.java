package com.example.farmbnb_cw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Booking_History extends AppCompatActivity {

    public static ArrayList<String> BookingDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);
        Intent confBookingDetails = getIntent();
        //if the intent is the confirmation page then pass in the booking information
        if (confBookingDetails.hasExtra("bookingcomplete")) {
            BookingDetails = confBookingDetails.getStringArrayListExtra("bookingcomplete");
            TextView name = findViewById(R.id.bookingFullName);
            name.setText(BookingDetails.get(0));
            TextView address = findViewById(R.id.bookingAddress);
            address.setText(new StringBuilder().append(BookingDetails.get(1)).append(", ").append(BookingDetails.get(2)).append(", ").append(BookingDetails.get(3)).append(", ").append(BookingDetails.get(4)).toString());
            TextView AccommodationDetails = findViewById(R.id.bookingAccommodation);
            AccommodationDetails.setText(BookingDetails.get(5));
            TextView Dates = findViewById(R.id.bookingDates);
            Dates.setText(new StringBuilder().append(BookingDetails.get(6)).append(" - ").append(BookingDetails.get(7)).toString());
        }
    }

    public void goHome(View homeView) {
        Intent intent = new Intent(this, My_Account.class);
        startActivity(intent);
    }
}