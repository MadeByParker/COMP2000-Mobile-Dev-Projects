package com.example.farmbnb_cw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Reset_Password extends AppCompatActivity {

    //define all user arrays
    public static ArrayList<User> NewUsers;
    public static ArrayList<String> strUser;
    public ArrayList<String> usernames;
    public ArrayList<String> emailAddresses;
    public ArrayList<String> passwords;

    private static final String TAG = "Reset_Password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        //retrieve incoming data
        Intent resetIntent = getIntent();
        usernames = resetIntent.getStringArrayListExtra("usernames");
        emailAddresses = resetIntent.getStringArrayListExtra("email");
        passwords = resetIntent.getStringArrayListExtra("passwords");
    }

    public void goHome(View homeView) {
        Intent homeIntent = new Intent(Reset_Password.this, MainActivity.class);
        startActivity(homeIntent);
    }

    public void confirmChange(View confirmView) {
        //take in all inputs
        EditText email = findViewById(R.id.ResetPageEmail);
        String emailText = email.getText().toString().trim();
        EditText username = findViewById(R.id.ResetPageUsername);
        String usernameText = username.getText().toString().trim();
        EditText oldPassword = findViewById(R.id.EnterOldPassword);
        String oldPasswordText = oldPassword.getText().toString().trim();
        EditText newPassword = findViewById(R.id.enterNewPassword);
        String newPasswordText = newPassword.getText().toString().trim();
        EditText confPassword = findViewById(R.id.confNewPassword);
        String confPasswordText = confPassword.getText().toString().trim();

        //boolean to seee if the array contains an exisitng user
        boolean containsUsername = usernames.contains(usernameText);
        boolean containsEmail = emailAddresses.contains(emailText);
        boolean oldPasswordCheck = passwords.contains(oldPasswordText);
        //if user exists
        if (containsUsername && containsEmail) {
            //if passwords match and not equal to old password then reset password
            if (newPasswordText.equals(confPasswordText) && !oldPasswordText.equals(newPasswordText) && !oldPasswordText.equals(confPasswordText)) {
                NewUsers = new ArrayList<>();
                NewUsers.add(new User(usernameText, newPasswordText, emailText));
                strUser = new ArrayList<>();
                for (int i = 0; i < NewUsers.size(); i++) {
                    Log.d(TAG, "onCreate: user: " + NewUsers.get(i).getUsername());
                    strUser.add(NewUsers.get(i).getUsername());
                    strUser.add(NewUsers.get(i).getPassword());
                    strUser.add(NewUsers.get(i).getEmailAddress());
                }


                Intent confirmIntent = new Intent(Reset_Password.this, Login.class);
                confirmIntent.putStringArrayListExtra("passwordReset", strUser);
                startActivity(confirmIntent);

                Toast.makeText(this, "Password Reset Successfully!", Toast.LENGTH_SHORT).show();
            } else if (!newPasswordText.equals(confPasswordText)) {
                Toast.makeText(this, "Passwords don't match, try again!", Toast.LENGTH_SHORT).show();
            } else if (oldPasswordText.equals(newPasswordText)) {
                Toast.makeText(this, "The new password matches your old one", Toast.LENGTH_SHORT).show();
            }
        } else if (!containsUsername){
            Toast.makeText(this, "Username is incorrect! Wrong Account!", Toast.LENGTH_SHORT).show();
        } else if (!containsEmail) {
            Toast.makeText(this, "Email Address is incorrect! Wrong Account!", Toast.LENGTH_SHORT).show();
        } else if (!oldPasswordCheck){
            Toast.makeText(this, "Password is incorrect! Wrong Account!", Toast.LENGTH_SHORT).show();
        }
        else if (usernameText.equals("") || emailText.equals("") || oldPasswordText.equals("") || newPasswordText.equals("") || confPasswordText.equals("")) {
            Toast.makeText(this, "Some details are empty! Please enter details!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "The account that you're trying to reset password on is incorrect!", Toast.LENGTH_SHORT).show();
        }
    }
}