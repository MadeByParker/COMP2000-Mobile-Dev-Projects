package com.example.students;

import static com.example.students.Login.LoggedInUser;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.students.swipe.SwipeSelectionActive;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Create_Projects extends AppCompatActivity {

    private RequestQueue _Queue;
    private static final String TAG = Create_Projects.class.getSimpleName();

    public ArrayList<String> studentIDs;
    public String studentID = String.valueOf(LoggedInUser);


    private EditText titleEdit, DescriptionEdit, yearEdit, firstNameEdit, lastNameEdit;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_projects);

        _Queue = Volley.newRequestQueue(this);

        titleEdit = findViewById(R.id.editTitle);
        yearEdit = findViewById(R.id.editYear);
        DescriptionEdit = findViewById(R.id.editDescription);
        firstNameEdit = findViewById(R.id.etFirstName);
        lastNameEdit = findViewById(R.id.etLastName);

        Button buttonCreate = findViewById(R.id.btnProjectUpdate);

        //CREATE  PROJECT BUTTON
        buttonCreate.setOnClickListener(v -> {
            //IF ANY ARE NULL THEN RETRY
            if (titleEdit.getText().toString().isEmpty() && DescriptionEdit.getText().toString().isEmpty() && yearEdit.getText().toString().isEmpty() && firstNameEdit.getText().toString().isEmpty() && lastNameEdit.getText().toString().isEmpty()) {
                Toast.makeText(Create_Projects.this, "Please enter all needed values", Toast.LENGTH_SHORT).show();
                return;
            }
            //ELSE GO AHEAD
            createProject(studentID, titleEdit.getText().toString(), DescriptionEdit.getText().toString(), yearEdit.getText().toString(), firstNameEdit.getText().toString(), lastNameEdit.getText().toString());
            Intent intent = new Intent(Create_Projects.this, Manage_Projects.class);
            startActivity(intent);
        });
    }

    //BACK BUTTON FOR IOS
    public void goBack(View homeView) {
        Intent homeIntent = new Intent(Create_Projects.this, Manage_Projects.class);
        startActivity(homeIntent);
    }

    //CREATE PROJECT
    private void createProject(String studentID, String title, String description, String year, String firstName, String lastName) {
        try {
            String url = "http://web.socem.plymouth.ac.uk/COMP2000/api/students/";
            //put inputs into a json object
            JSONObject projectItems = new JSONObject();
            projectItems.put("StudentID", Integer.valueOf(studentID));
            projectItems.put("Title", title);
            projectItems.put("Description", description);
            projectItems.put("Year", Integer.valueOf(year));
            projectItems.put("First_Name", firstName);
            projectItems.put("Second_Name", lastName);
            final String postBody = projectItems.toString();

            //post request
            StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                //on success
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                    Toast.makeText(Create_Projects.this, "Project created", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                //error
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                    Toast.makeText(Create_Projects.this, "Project could not be created", Toast.LENGTH_SHORT).show();
                }
            }) {
                //specifies body content type
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                //fills in body
                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return postBody == null ? null : postBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee){
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", postBody, "utf-8");
                        return null;
                    }
                }

                //response message
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if(response != null){
                        responseString = String.valueOf(response.statusCode);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            postRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            _Queue.add(postRequest);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}