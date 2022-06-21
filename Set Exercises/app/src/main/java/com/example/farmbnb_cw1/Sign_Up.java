package com.example.farmbnb_cw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



import java.util.ArrayList;

public class Sign_Up extends AppCompatActivity {

    private static ArrayList<User> NewUsers;
    private ArrayList<String> strUser;

    private static final String TAG = "Sign_Up";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void goHome(View homeView) {
        Intent homeIntent = new Intent(Sign_Up.this, MainActivity.class);
        startActivity(homeIntent);
    }

    public void loginAgain(View loginView) {
        EditText email = (EditText) findViewById(R.id.signUpEmail);
        String emailText = email.getText().toString().trim();
        EditText password = (EditText) findViewById(R.id.signUpIntPassword);
        String passwordText = password.getText().toString().trim();
        EditText confPassword = (EditText) findViewById(R.id.signUpConfPassword);
        String confPasswordText = confPassword.getText().toString().trim();
        EditText username = (EditText) findViewById(R.id.signUpUsername);
        String usernameText = username.getText().toString().trim();
        boolean passEmpty = passwordText.isEmpty();
        boolean confPassEmpty = confPasswordText.isEmpty();
        if (passwordText.equals(confPasswordText) && !passEmpty && !confPassEmpty) {
            NewUsers = new ArrayList<>();
            NewUsers.add(new User(usernameText, passwordText, emailText));
            strUser = new ArrayList<>();
            for (int i = 0; i < NewUsers.size(); i++) {
                Log.d(TAG, "onCreate: user: " + NewUsers.get(i).getUsername());
                strUser.add(NewUsers.get(i).getUsername());
                strUser.add(NewUsers.get(i).getPassword());
                strUser.add(NewUsers.get(i).getEmailAddress());
            }


            Intent loginIntent = new Intent(Sign_Up.this, Login.class);
            loginIntent.putStringArrayListExtra("user", strUser);
            startActivity(loginIntent);

            Toast.makeText(this, "User Account Created!", Toast.LENGTH_SHORT).show();
        } else if (usernameText.equals("") || emailText.equals("") || passwordText.equals("") || confPasswordText.equals("")) {
            Toast.makeText(this, "Some details are empty! Please enter details!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Passwords don't match, try again!", Toast.LENGTH_SHORT).show();
        }
    }
}