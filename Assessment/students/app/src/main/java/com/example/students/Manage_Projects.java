package com.example.students;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.students.swipe.SwipeSelectionActive;

import java.util.ArrayList;

public class Manage_Projects extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_projects);

    }

    //view/edit projects
    public void btnGoEdit(View editView) {
        Intent intent = new Intent(this, SwipeSelectionActive.class);
        startActivity(intent);
    }

    //create project
    public void btnGoCreate(View createView) {
        Intent intent = new Intent(this, Create_Projects.class);
        startActivity(intent);
    }
}