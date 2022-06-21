package com.example.farmbnb_cw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Confirm_Booking_Details extends AppCompatActivity {

    public static ArrayList<String> BookingDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking_details);
        Intent confBookingDetails = getIntent();
        BookingDetails = confBookingDetails.getStringArrayListExtra("booking");
        //lay out the booking information
        TextView name = findViewById(R.id.confFullName);
        name.setText(BookingDetails.get(0));
        TextView address = findViewById(R.id.confAddress);
        address.setText(BookingDetails.get(1) + ", " + BookingDetails.get(2) + ", " + BookingDetails.get(3) + ", " + BookingDetails.get(4));
        TextView AccommodationDetails = findViewById(R.id.confAccommodation);
        AccommodationDetails.setText(BookingDetails.get(5));
        TextView Dates = findViewById(R.id.confDate);
        Dates.setText(BookingDetails.get(6) + " - " + BookingDetails.get(7));
    }

    public void goHome(View homeView) {
        Intent homeIntent = new Intent(Confirm_Booking_Details.this, My_Account.class);
        startActivity(homeIntent);
    }

    public void previous(View previousView){
        Intent previousIntent = new Intent(Confirm_Booking_Details.this, Select_Date.class);
        startActivity(previousIntent);
    }

    public void confBooking(View confBookingView){
        Intent confBookingIntent = new Intent(Confirm_Booking_Details.this, Booking_Confirmation.class);
        confBookingIntent.putStringArrayListExtra("bookingcomplete", BookingDetails);
        startActivity(confBookingIntent);
    }
}