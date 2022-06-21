package com.example.farmbnb_cw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    private static final String TAG = "Login";

    //define arrays for different intents
    public static ArrayList<User> Users;
    public static ArrayList<String> UsersTemp;
    public static ArrayList<String> UsersEmail;
    public static ArrayList<String> UsersPasswords;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        ArrayList<String> NewUsers;

        ArrayList<String> UserNames;
        UserNames = new ArrayList<>();
        //if the login page is being opened by the sign up for account page then add the new user
        if(intent.hasExtra("user")){
            Users = new ArrayList<>();
            Users.add(new User("user", "test1234", "user@test.com"));
            for (int i = 0; i < Users.size(); i++){
                Log.d(TAG, "onCreate: user: " + Users.get(i).getUsername());
            }
            NewUsers = intent.getStringArrayListExtra("user");
            for (int i = 0; i < NewUsers.size(); i++) {
                Users.add(new User(NewUsers.get(0), NewUsers.get(1), NewUsers.get(2)));
                Log.d(TAG, "New User" + NewUsers.get(i));
            }
        }
        //else if the user just reset their password then update the user with the username of that who just updated
        else if(intent.hasExtra("passwordReset")){
            Users = new ArrayList<>();
            Users.add(new User("user", "test1234", "user@test.com"));
            for (int i = 0; i < Users.size(); i++){
                Log.d(TAG, "onCreate: user: " + Users.get(i).getUsername());
            }
            NewUsers = intent.getStringArrayListExtra("passwordReset");
            for (int i = 0; i < NewUsers.size(); i++) {
                Users.add(new User(NewUsers.get(0), NewUsers.get(1), NewUsers.get(2)));
                Log.d(TAG, "Updated Users: " + Users.get(i).getUsername() + Users.get(i).getPassword());
            }

            for (int p = 0; p < Users.size(); p++)
            {
                UserNames.add(Users.get(p).getUsername());
                for (String user : UserNames) {
                    if(!NewUsers.get(2).equals(Users.get(p).getPassword())){
                        Users.get(p).setPassword(NewUsers.get(1));
                    }
                    else {
                        break;
                    }
                }
                Log.d(TAG, "Updated Users: " + Users.get(p).getUsername() + " " + Users.get(p).getPassword());
            }
        }
        //else if coming from home page just load up default
        else {
            Users = new ArrayList<>();
            Users.add(new User("user", "test1234", "user@test.com"));
            for (int i = 0; i < Users.size(); i++){
                Log.d(TAG, "onCreate: user: " + Users.get(i).getUsername());
            }
        }

    }

    public void goHome(View homeView) {
        Intent homeIntent = new Intent(Login.this, MainActivity.class);
        startActivity(homeIntent);
    }

    public void resetPassword(View resetPasswordView) {
        UsersTemp = new ArrayList<>();
        UsersPasswords = new ArrayList<>();
        UsersEmail = new ArrayList<>();
        for (int i = 0; i < Users.size(); i++){
            UsersTemp.add(Users.get(i).getUsername());
        }

        for (int i = 0; i < Users.size(); i++){
            UsersEmail.add(Users.get(i).getEmailAddress());
        }

        for (int i = 0; i < Users.size(); i++){
            UsersPasswords.add(Users.get(i).getPassword());
        }
        //send 3 array lists to check if the user exists to be able to reset their password
        Intent resetIntent = new Intent(Login.this, Reset_Password.class);
        resetIntent.putStringArrayListExtra("email", UsersEmail);
        resetIntent.putStringArrayListExtra("usernames", UsersTemp);
        resetIntent.putStringArrayListExtra("passwords", UsersPasswords);
        startActivity(resetIntent);
    }

    public void goRegister(View registerView) {
        Intent registerIntent = new Intent(Login.this, Sign_Up.class);
        startActivity(registerIntent);
    }

    public void signedIn(View signedInView) {
        EditText email = (EditText)findViewById(R.id.editEmailAddress);
        String emailText = email.getText().toString().trim();
        EditText password = (EditText)findViewById(R.id.enterPassword);
        String passwordText = password.getText().toString().trim();

        //login system
        for (int i = 0; i < Users.size(); i++){
            if (Users.get(i).getEmailAddress().equals(emailText) && Users.get(i).getPassword().equals(passwordText)){
                String username = Users.get(i).getUsername();
                Intent signedInIntent = new Intent(Login.this, My_Account.class);
                Toast.makeText(this, "Welcome " + username, Toast.LENGTH_SHORT).show();
                startActivity(signedInIntent);
                break;
            }
            else if (emailText.equals("") || passwordText.equals("")){
                Toast.makeText(this, "You need to enter some details to log in!", Toast.LENGTH_SHORT).show();
            }
            else if(Users.get(i).getEmailAddress().equals(emailText) && !Users.get(i).getPassword().equals(passwordText)){
                Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
            }
            else if (!Users.get(i).getEmailAddress().equals(emailText) && Users.get(i).getPassword().equals(passwordText)){
                Toast.makeText(this, "Incorrect Email Address", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Incorrect Details", Toast.LENGTH_SHORT).show();
            }
        }
    }
}