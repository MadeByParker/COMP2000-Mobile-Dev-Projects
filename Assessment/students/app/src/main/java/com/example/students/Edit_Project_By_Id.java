package com.example.students;


import static com.example.students.swipe.SwipeAdapter.SelectedProjectId;
import static com.example.students.swipe.SwipeAdapter.SelectedProjects;
import static com.example.students.swipe.SwipeAdapter.selectedImageURL;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class Edit_Project_By_Id extends AppCompatActivity {

    private static final String TAG = Edit_Project_By_Id.class.getSimpleName();
    private static final int SELECT_PICTURE = 9544;

    String imageAbsolutePath = "";
    ImageView imageThumbnail;
    private RequestQueue _Queue;


    // Permissions for accessing the storage
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private TextView projectTextView;
    private EditText title, description, year;

    private Project updatePhotoURL;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project_by_id);

        _Queue = Volley.newRequestQueue(this);

        imageThumbnail = findViewById(R.id.imagePreview);
        projectTextView = findViewById(R.id.textProjectTitleEdit);
        title = findViewById(R.id.editTitle);
        description = findViewById(R.id.editDescription);
        year = findViewById(R.id.editYear);

        Button selectImage = findViewById(R.id.btnSelect);
        Button uploadImage = findViewById(R.id.btnUpload);
        Button updateProject = findViewById(R.id.btnProjectUpdate);

        Log.d(TAG, String.valueOf(SelectedProjects.get(0)));
        projectTextView.setText(SelectedProjects.get(1));
        title.setText(String.valueOf(SelectedProjects.get(1)));
        description.setText(String.valueOf(SelectedProjects.get(2)));
        year.setText(String.valueOf(SelectedProjects.get(3)));

        Log.d(TAG, "selectedImageURL: " + selectedImageURL);


        //show current image of the project
        try  {
            byte[] image = Base64.getDecoder().decode(selectedImageURL);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            Log.d(TAG, bitmap.toString());
            imageThumbnail.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //select image button
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //worker thread to open gallery
                new Thread(new Runnable() {
                    public void run() {
                        pick(v);
                    }
                }).start();
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //file path

                Log.d("URL", imageAbsolutePath);
                File file = new File(imageAbsolutePath);
                Retrofit retrofit = RetrofitNetworkClient.getRetrofit();

                //submit image as a multipart form data
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part parts = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

                RequestBody someData = RequestBody.create(MediaType.parse("text/plain"), "This is a new Image");

                updatePhotoURL.setURL(someData.toString());

                //call the api
                UploadImage uploadApis = retrofit.create(UploadImage.class);
                Call call = uploadApis.uploadImage(String.valueOf(SelectedProjectId), parts, someData);
                call.enqueue(new Callback() {
                    @Override
                    //success
                    public void onResponse(Call call, retrofit2.Response response) {
                        Toast.makeText(Edit_Project_By_Id.this, "Uploaded Image", Toast.LENGTH_SHORT).show();
                        Log.d("Image Upload", response.toString());
                    }

                    @Override
                    //error
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(Edit_Project_By_Id.this, "Upload Image Failed", Toast.LENGTH_SHORT).show();
                        Log.e("Image error", t.toString());
                    }
                });

            }
        });

        //update whole project
        updateProject.setOnClickListener(v -> updateProject(title.getText().toString(), description.getText().toString(), year.getText().toString()));
    }


    //pick image
    public void pick(View view) {
        verifyStoragePermissions(Edit_Project_By_Id.this);
        // create an instance of the
        // intent of the type image
        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    //permissions
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    // Method to get the absolute path of the selected image from its URI
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                Log.d("uri", selectedImageUri.toString());
                //if (data.getDataString().toLowerCase().endsWith("jpg")){
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImageUri,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Log.w("path of image", picturePath+"");
                imageAbsolutePath = picturePath;
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    imageThumbnail.setImageURI(selectedImageUri);
                }
            }
        }
    }

    //back button
    public void goBack(View homeView) {
        Intent homeIntent = new Intent(Edit_Project_By_Id.this, SwipeSelectionActive.class);
        startActivity(homeIntent);
    }

    //update project
    public void updateProject(String inputTitle, String inputDescription, String inputYear) {
        String url = "http://web.socem.plymouth.ac.uk/COMP2000/api/students/";
        url = url + SelectedProjectId;

        //submit details as json object
        JSONObject updateProjectItems = new JSONObject();
        try {
            updateProjectItems.put("projectID", Integer.valueOf(SelectedProjectId));
            updateProjectItems.put("studentID", Integer.valueOf(SelectedProjects.get(6)));
            updateProjectItems.put("title", inputTitle);
            updateProjectItems.put("description", inputDescription);
            updateProjectItems.put("year", Integer.valueOf(inputYear));
            updateProjectItems.put("first_Name", SelectedProjects.get(4));
            updateProjectItems.put("second_Name", SelectedProjects.get(5));
            final String putRequestBody = updateProjectItems.toString();

            Log.d(TAG, "here works");

            //put request
            StringRequest putRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(Edit_Project_By_Id.this, "Updated Project", Toast.LENGTH_SHORT).show();
                    Log.i("VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Edit_Project_By_Id.this, "Update Project Failed", Toast.LENGTH_SHORT).show();
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return putRequestBody == null ? null : putRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", putRequestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            _Queue.add(putRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //one updated go back to refresh
        Intent intent = new Intent(Edit_Project_By_Id.this, Manage_Projects.class);
        startActivity(intent);
    }



}