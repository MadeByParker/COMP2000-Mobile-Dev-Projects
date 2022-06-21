package com.example.students;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Register extends AppCompatActivity {

    private static ArrayList<StudentUser> NewUsers;
    private ArrayList<String> strUser;

    private static final String TAG = "Sign_Up";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    //back button
    public void goBack(View homeView) {
        Intent homeIntent = new Intent(Register.this, Login.class);
        startActivity(homeIntent);
    }

    //register account
    public void btnRegisterAccount(View loginView) {
        EditText email = (EditText) findViewById(R.id.etRegEmail);
        String emailText = email.getText().toString().trim();
        EditText password = (EditText) findViewById(R.id.etRegPassword);
        String passwordText = password.getText().toString().trim();
        EditText confPassword = (EditText) findViewById(R.id.etConfirmPassword);
        String confPasswordText = confPassword.getText().toString().trim();
        EditText id = (EditText) findViewById(R.id.etStudentIDReg);
        String idText = id.getText().toString().trim();
        boolean passEmpty = passwordText.isEmpty();
        boolean confPassEmpty = confPasswordText.isEmpty();

        //if either passwords are empty and they don't match then retry
        if (passwordText.equals(confPasswordText) && !passEmpty && !confPassEmpty) {
            NewUsers = new ArrayList<>();
            NewUsers.add(new StudentUser(idText, passwordText, emailText));
            strUser = new ArrayList<>();
            for (int i = 0; i < NewUsers.size(); i++) {
                Log.d(TAG, "onCreate: user id: " + NewUsers.get(i).getStudentId());
                strUser.add(NewUsers.get(i).getStudentId());
                strUser.add(NewUsers.get(i).getPassword());
                strUser.add(NewUsers.get(i).getEmailAddress());
            }


            Intent loginIntent = new Intent(Register.this, Login.class);
            loginIntent.putStringArrayListExtra("user", strUser);
            startActivity(loginIntent);

            Toast.makeText(this, "User Account Created!", Toast.LENGTH_SHORT).show();
        } else if (idText.equals("") || emailText.equals("") || passwordText.equals("") || confPasswordText.equals("")) {
            Toast.makeText(this, "Some details are empty! Please enter details!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Passwords don't match, try again!", Toast.LENGTH_SHORT).show();
        }
    }
}