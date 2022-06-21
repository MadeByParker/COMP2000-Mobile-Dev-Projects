package com.example.farmbnb_cw1;

import static com.example.farmbnb_cw1.Enter_Customer_Details.Customers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Select_Date extends AppCompatActivity {

    private static final String TAG = "Select_Date";
    ArrayList<String> strCustomers;
    String Accommodation;

    //DATE PICKER 1
    public static final String DATE_DIALOG_1 = "datePicker1";
    static TextView arrivalDate;
    private static int mYear1;
    private static int mMonth1;
    private static int mDay1;
    //DATE PICKER 1


    //DATE PICKER 2
    public static final String DATE_DIALOG_2 = "datePicker2";
    static TextView departDate;
    private static int mYear2;
    private static int mMonth2;
    private static int mDay2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);

        //RECEIVE ARRAYS FROM PREVIOUS ACTIVITY
        Intent AccommIntent = getIntent();
        strCustomers = AccommIntent.getStringArrayListExtra("customer");
        Accommodation = AccommIntent.getStringExtra("Accommodation");

        //DATE PICKER 1 - START CALENDARVIEW
        arrivalDate = findViewById(R.id.txt_date1);
        arrivalDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment newFragment1 = new DatePickerFragment1();
                newFragment1.show(getSupportFragmentManager(), DATE_DIALOG_1);
            }
        });


        //DATE PICKER 2 - - START CALENDARVIEW
        departDate = findViewById(R.id.txt_date2);
        departDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment newFragment2 = new DatePickerFragment2();
                newFragment2.show(getSupportFragmentManager(), DATE_DIALOG_2);
            }
        });
    }

    //DATE PICKER 1 - Get date from calendar 1
    public static class DatePickerFragment1 extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {


            //Date Time NOW
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(),R.style.DatePicker1, this, year, month, day);
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // get selected date
            mYear1 = year;
            mMonth1 = month;
            mDay1 = day;
            // show selected date to date button
            arrivalDate.setText(new StringBuilder()
                    .append(mDay1).append("/")
                    .append(mMonth1 + 1).append("/")
                    .append(mYear1).append(" "));
        }
    }
    //DATE PICKER 1


    //DATE PICKER 2  - Get date from calendar 1
    public static class DatePickerFragment2 extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // set default date

            //Date Time FROM BEFORE
            String date =  departDate.getText().toString().trim();
            String[] data = date.split("-", 3);
            int year = Integer.parseInt(data[0]);
            int month = Integer.parseInt(data[1])-1;    //Because January is 0
            int day = Integer.parseInt(data[2]);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(),R.style.DatePicker2, this, year, month, day);
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // get selected date
            mYear2 = year;
            mMonth2 = month;
            mDay2 = day;
            // show selected date to date button
            departDate.setText(new StringBuilder()
                    .append(mDay2).append("/")
                    .append(mMonth2 + 1).append("/")
                    .append(mYear2).append(" "));
        }
    }
    public void goHome(View homeView) {
        Intent homeIntent = new Intent(Select_Date.this, MainActivity.class);
        startActivity(homeIntent);
    }

    public void previous(View previousView) {
        Intent previousIntent = new Intent(Select_Date.this, Select_Accommodation.class);
        startActivity(previousIntent);
    }

    public void confirmDate(View confDateView) {
        ArrayList<String> Dates;

        ArrayList<String> FullBooking;

        Dates = new ArrayList<>();
        FullBooking = new ArrayList<>();
        //add dates to dates arraylist
        Dates.add(arrivalDate.getText().toString().trim());
        Dates.add(departDate.getText().toString().trim());

        //add everything to fullbooking arraylist
        FullBooking.add(strCustomers.get(0));
        FullBooking.add(strCustomers.get(1));
        FullBooking.add(strCustomers.get(2));
        FullBooking.add(strCustomers.get(3));
        FullBooking.add(strCustomers.get(4));
        FullBooking.add(Accommodation);
        FullBooking.add(Dates.get(0));
        FullBooking.add(Dates.get(1));

        for (int i = 0; i < FullBooking.size(); i++) {
            Log.d(TAG, "Booking: " + FullBooking.get(i));
        }

        Intent confDateIntent = new Intent(Select_Date.this, Confirm_Booking_Details.class);
        confDateIntent.putStringArrayListExtra("booking", FullBooking);
        startActivity(confDateIntent);
        Toast.makeText(this, "Confirmed Dates!", Toast.LENGTH_SHORT).show();
    }

}