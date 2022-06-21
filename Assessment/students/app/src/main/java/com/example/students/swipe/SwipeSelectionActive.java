package com.example.students.swipe;

import static com.example.students.Login.LoggedInUser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.students.Manage_Projects;
import com.example.students.Project;
import com.example.students.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SwipeSelectionActive extends AppCompatActivity {

    RecyclerView _RecyclerView;
    SwipeAdapter _adapter;
    ArrayList<Project> students = new ArrayList<>();
    private RequestQueue _Queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_selection_active);

        _Queue = Volley.newRequestQueue(this);

        //set up the layout
        _RecyclerView = findViewById(R.id.swipe_rv);
        _RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        _RecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        _adapter = new SwipeAdapter(this, students);
        _RecyclerView.setAdapter(_adapter);

        jsonParse();
    }

    //back button
    public void goBack(View homeView) {
        Intent homeIntent = new Intent(SwipeSelectionActive.this, Manage_Projects.class);
        startActivity(homeIntent);
    }

    //get method
    private void jsonParse() {

        ArrayList<Project> students = new ArrayList<>();
        String url = "http://web.socem.plymouth.ac.uk/COMP2000/api/students";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        Project studentProject = new Project();
                        JSONObject json = response.getJSONObject(i);
                        if(json.getInt("studentID") == LoggedInUser) {
                            studentProject.setProjectId(json.getInt("projectID"));
                            studentProject.setStudentId(json.getInt("studentID"));
                            studentProject.setYear(json.getInt("year"));
                            studentProject.setFirstName(json.getString("first_Name"));
                            studentProject.setLastName(json.getString("second_Name"));
                            studentProject.setTitle(json.getString("title"));
                            studentProject.setDescription(json.getString("description"));

                            students.add(studentProject);

                            Toast.makeText(SwipeSelectionActive.this, "Fetched", Toast.LENGTH_SHORT).show();
                            Log.d("Project ID", String.valueOf(studentProject.getProjectId()));
                        }
                        if(response.length() < 0){
                            Toast.makeText(SwipeSelectionActive.this, "No Projects found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                _adapter.setStudents(students);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SwipeSelectionActive .this, "Failed to fetch", Toast.LENGTH_SHORT).show();
            }
        });

        _Queue.add(request);

    }
}