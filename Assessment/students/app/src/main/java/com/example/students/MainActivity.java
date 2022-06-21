package com.example.students;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //sign in
    public void btnSignIn(View loginView) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    //register
    public void btnRegister(View registerView) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}