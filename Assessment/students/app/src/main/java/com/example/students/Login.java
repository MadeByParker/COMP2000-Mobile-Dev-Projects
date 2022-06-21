package com.example.students;

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
    public static ArrayList<StudentUser> Users;
    public static ArrayList<String> UsersTemp;
    public static ArrayList<String> UsersEmail;
    public static int LoggedInUser;
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
            Users.add(new StudentUser("13785", "test1234", "user@students.com"));
            for (int i = 0; i < Users.size(); i++){
                Log.d(TAG, "onCreate: user with ID: " + Users.get(i).getStudentId());
            }
            NewUsers = intent.getStringArrayListExtra("user");
            for (int i = 0; i < NewUsers.size(); i++) {
                Users.add(new StudentUser(NewUsers.get(0), NewUsers.get(1), NewUsers.get(2)));
                Log.d(TAG, "New User: " + NewUsers.get(0) + "Password: " + NewUsers.get(1) + "Email: " + NewUsers.get(2));
            }
        }
        //else if the user just reset their password then update the user with the username of that who just updated
        else if(intent.hasExtra("passwordReset")){
            Users = new ArrayList<>();
            Users.add(new StudentUser("13785", "test1234", "user@students.com"));
            for (int i = 0; i < Users.size(); i++){
                Log.d(TAG, "onCreate: user: " + Users.get(i).getStudentId());
            }
            NewUsers = intent.getStringArrayListExtra("passwordReset");
            for (int i = 0; i < NewUsers.size(); i++) {
                Users.add(new StudentUser(NewUsers.get(0), NewUsers.get(1), NewUsers.get(2)));
                Log.d(TAG, "Updated Users: " + Users.get(i).getStudentId() + ", Password: " + Users.get(i).getPassword());
            }

            for (int p = 0; p < Users.size(); p++)
            {
                UserNames.add(Users.get(p).getStudentId());
                for (String ignored : UserNames) {
                    if(!NewUsers.get(2).equals(Users.get(p).getPassword())){
                        Users.get(p).setPassword(NewUsers.get(1));
                    }
                    else {
                        break;
                    }
                }
                Log.d(TAG, "Updated Users: " + Users.get(p).getStudentId() + ", Password: " + Users.get(p).getPassword());
            }
        }
        //else if coming from home page just load up default
        else {
            Users = new ArrayList<>();
            Users.add(new StudentUser("13785", "test1234", "user@students.com"));
            for (int i = 0; i < Users.size(); i++){
                Log.d(TAG, "onCreate: user id: " + Users.get(i).getStudentId());
            }
        }

    }

    //back button
    public void goBack(View homeView) {
        Intent homeIntent = new Intent(Login.this, MainActivity.class);
        startActivity(homeIntent);
    }

    //reset password
    public void btnResetPassword(View resetPasswordView) {
        UsersTemp = new ArrayList<>();
        UsersPasswords = new ArrayList<>();
        UsersEmail = new ArrayList<>();
        for (int i = 0; i < Users.size(); i++){
            UsersTemp.add(Users.get(i).getStudentId());
            UsersEmail.add(Users.get(i).getEmailAddress());
            UsersPasswords.add(Users.get(i).getPassword());
        }
        //send 3 array lists to check if the user exists to be able to reset their password
        Intent resetIntent = new Intent(Login.this, Password_Reset.class);
        resetIntent.putStringArrayListExtra("email", UsersEmail);
        resetIntent.putStringArrayListExtra("studentIDs", UsersTemp);
        resetIntent.putStringArrayListExtra("passwords", UsersPasswords);
        startActivity(resetIntent);
    }

    //register button
    public void btnRegister(View registerView) {
        Intent registerIntent = new Intent(Login.this, Register.class);
        startActivity(registerIntent);
    }

    //login
    public void btnLogin(View signedInView) {
        EditText etStudentID = (EditText)findViewById(R.id.etStudentID1);
        String studentIDText = etStudentID.getText().toString().trim();
        EditText password = (EditText)findViewById(R.id.etSignInPassword);
        String passwordText = password.getText().toString().trim();
        LoggedInUser = Integer.parseInt(studentIDText);
        //login system
        for (int i = 0; i < Users.size(); i++){
            if (Users.get(i).getStudentId().equals(studentIDText) && Users.get(i).getPassword().equals(passwordText)){
                Intent signedInIntent = new Intent(Login.this, Manage_Projects.class);
                Toast.makeText(this, "Welcome student", Toast.LENGTH_SHORT).show();
                startActivity(signedInIntent);
                break;
            }
            else if (studentIDText.equals("") || passwordText.equals("")){
                Toast.makeText(this, "You need to enter some details to log in!", Toast.LENGTH_SHORT).show();
            }
            else if(Users.get(i).getStudentId().equals(studentIDText) && !Users.get(i).getPassword().equals(passwordText)){
                Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
            }
            else if (!Users.get(i).getStudentId().equals(studentIDText) && Users.get(i).getPassword().equals(passwordText)){
                Toast.makeText(this, "Incorrect Student ID", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Incorrect Details", Toast.LENGTH_SHORT).show();
            }
        }
    }
}