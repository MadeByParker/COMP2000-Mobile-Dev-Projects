package com.example.farmbnb_cw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;


public class Enter_Customer_Details extends AppCompatActivity {

    private static final String TAG = "Enter_Customer_Details";
    public static ArrayList<Customer> Customers;
    public static ArrayList<String> strCustomers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_customer_details);



    }

    public void previous(View previous) {
        Intent previousIntent = new Intent(Enter_Customer_Details.this, My_Account.class);
        startActivity(previousIntent);
    }

    public void goHome(View homeView) {
        Intent intent = new Intent(Enter_Customer_Details.this, MainActivity.class);
        startActivity(intent);
    }

    public void submitDetails(View submitDetailsView) {
        //get all inputs from this page
        EditText name = (EditText)findViewById(R.id.enterName);
        String nameText = name.getText().toString().trim();
        EditText AddressLine1 = (EditText)findViewById(R.id.enterAddressLine1);
        String AddressLine1Text = AddressLine1.getText().toString().trim();
        EditText AddressLine2 = (EditText)findViewById(R.id.enterAddressLine2);
        String AddressLine2Text = AddressLine2.getText().toString().trim();
        EditText City = findViewById(R.id.enterCity);
        String CityText = City.getText().toString().trim();
        EditText PostCode = findViewById(R.id.enterPostcode);
        String PostCodeText = PostCode.getText().toString().trim();

        Customers = new ArrayList<>();

        //if any are empty then invalid input
        if(nameText.equals("") || AddressLine1Text.equals("") || AddressLine2Text.equals("") || CityText.equals("") || PostCodeText.equals("")) {
            Toast.makeText(this, "One or more fields are empty, try again!", Toast.LENGTH_SHORT).show();
        }
        //else add each input to customer object
        else {
            Customers.add(new Customer(nameText, AddressLine1Text, AddressLine2Text, CityText, PostCodeText));
            for (int i = 0; i < Customers.size(); i++) {
                Log.d(TAG, "onCreate: Customer: " + Customers.get(i).getFullName());
            }
            strCustomers = new ArrayList<>();
            for (int i = 0; i < Customers.size(); i++) {

                strCustomers.add(Customers.get(i).getFullName());
                strCustomers.add(Customers.get(i).getlAddressLine1());
                strCustomers.add(Customers.get(i).getlAddressLine2());
                strCustomers.add(Customers.get(i).getCity());
                strCustomers.add(Customers.get(i).getPostCode());
            }


            Intent detailsIntent = new Intent(Enter_Customer_Details.this, Select_Accommodation.class);
            detailsIntent.putStringArrayListExtra("customer", strCustomers);
            Toast.makeText(this, "Customer Details entered correctly!", Toast.LENGTH_SHORT).show();
            startActivity(detailsIntent);

        }
    }
}