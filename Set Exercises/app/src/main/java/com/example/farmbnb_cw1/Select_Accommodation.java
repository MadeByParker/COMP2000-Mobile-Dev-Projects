package com.example.farmbnb_cw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class Select_Accommodation extends AppCompatActivity {

    private static final String TAG = "Select_Accommodation";

    ArrayList<String> CustomerDetails;
    String AccommodationDetails = "";

    //show toast function
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_accommodation);
        Intent intent = getIntent();

        CustomerDetails = new ArrayList<>();
        CustomerDetails = intent.getStringArrayListExtra("customer");


        CardView bedroom1 = (CardView) findViewById(R.id.Bedroom1Card);

        //bedroom 1 card on click event
        bedroom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccommodationDetails = "Bedroom 1";
                showToast("Bedroom 1 Selected!");
            }
        });
        CardView bedroom2 = (CardView) findViewById(R.id.Bedroom2Card);

        //bedroom 2 card on click event
        bedroom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccommodationDetails = "Bedroom 2";
                showToast("Bedroom 2 Selected!");
            }
        });

        CardView bedroom3 = (CardView) findViewById(R.id.Bedroom3Card);

        //bedroom 3 card on click event
        bedroom3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccommodationDetails = "Bedroom 3";
                showToast("Bedroom 3 Selected!");
            }
        });

        CardView Hut = (CardView) findViewById(R.id.HutCard);

        //hut card on click event
        Hut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccommodationDetails = "Shepherd's Hut";
                showToast("You have selected the Hut!");
            }
        });

        CardView Barn = (CardView) findViewById(R.id.BarnCard);

        //barn card on click event
        Barn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccommodationDetails = "Barn";
                showToast("You have selected the Barn!");
            }
        });
    }

    public void goHome(View homeView) {
        Intent homeIntent = new Intent(Select_Accommodation.this, MainActivity.class);
        startActivity(homeIntent);
    }

    public void SubmitAccomm(View RoomView) {
        if(AccommodationDetails.equals("")){
            Toast.makeText(this, "You haven't selected an accommodation!", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent DateIntent = new Intent(Select_Accommodation.this, Select_Date.class);
            Log.d(TAG, "Accommodation: " + AccommodationDetails);
            Log.d(TAG, "Customer: " + CustomerDetails.get(0));
            DateIntent.putExtra("Accommodation", AccommodationDetails);
            DateIntent.putStringArrayListExtra("customer", CustomerDetails);
            startActivity(DateIntent);
            Toast.makeText(this, "Accommodation Selected!", Toast.LENGTH_SHORT).show();
        }
    }

    public void previous(View previousView) {
    Intent previousIntent = new Intent(Select_Accommodation.this, Enter_Customer_Details.class);
    startActivity(previousIntent);
    }
}