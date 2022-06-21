package com.example.students;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Password_Reset extends AppCompatActivity {

    //define all user arrays
    public static ArrayList<StudentUser> NewUsers;
    public static ArrayList<String> strUser;
    public ArrayList<String> studentIDs;
    public ArrayList<String> emailAddresses;
    public ArrayList<String> passwords;

    private static final String TAG = "Reset_Password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        //retrieve incoming data
        Intent resetIntent = getIntent();
        studentIDs = resetIntent.getStringArrayListExtra("studentIDs");
        emailAddresses = resetIntent.getStringArrayListExtra("email");
        passwords = resetIntent.getStringArrayListExtra("passwords");
    }

    //back button
    public void goBack(View homeView) {
        Intent homeIntent = new Intent(Password_Reset.this, Login.class);
        startActivity(homeIntent);
    }

    public void btnResetPassword(View confirmView) {
        //take in all inputs
        EditText email = findViewById(R.id.etResetEmail);
        String emailText = email.getText().toString().trim();
        EditText id = findViewById(R.id.etResetID);
        String idText = id.getText().toString().trim();
        EditText newPassword = findViewById(R.id.etResetPassword);
        String newPasswordText = newPassword.getText().toString().trim();
        EditText confPassword = findViewById(R.id.etNewPassword);
        String confPasswordText = confPassword.getText().toString().trim();

        //boolean to see if the array contains an existing user
        boolean containsID = studentIDs.contains(idText);
        boolean containsEmail = emailAddresses.contains(emailText);
        //if user exists
        if (containsID && containsEmail) {
            //if passwords match and not equal to old password then reset password
            if (newPasswordText.equals(confPasswordText)) {
                NewUsers = new ArrayList<>();
                NewUsers.add(new StudentUser(idText, newPasswordText, emailText));
                strUser = new ArrayList<>();
                for (int i = 0; i < NewUsers.size(); i++) {
                    Log.d(TAG, "onCreate: user: " + NewUsers.get(i).getStudentId());
                    strUser.add(NewUsers.get(i).getStudentId());
                    strUser.add(NewUsers.get(i).getPassword());
                    strUser.add(NewUsers.get(i).getEmailAddress());
                }


                Intent confirmIntent = new Intent(Password_Reset.this, Login.class);
                confirmIntent.putStringArrayListExtra("passwordReset", strUser);
                startActivity(confirmIntent);

                Toast.makeText(this, "Password Reset Successfully!", Toast.LENGTH_SHORT).show();
            } else if (!newPasswordText.equals(confPasswordText)) {
                Toast.makeText(this, "Passwords don't match, try again!", Toast.LENGTH_SHORT).show();
            }
        } else if (!containsID){
            Toast.makeText(this, "Username is incorrect! Wrong Account!", Toast.LENGTH_SHORT).show();
        } else if (!containsEmail) {
            Toast.makeText(this, "Student ID is incorrect! Wrong Account!", Toast.LENGTH_SHORT).show();
        }
        else if (idText.equals("") || emailText.equals("") || newPasswordText.equals("") || confPasswordText.equals("")) {
            Toast.makeText(this, "Some details are empty! Please enter details!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "The account does not exist!", Toast.LENGTH_SHORT).show();
        }
    }
}