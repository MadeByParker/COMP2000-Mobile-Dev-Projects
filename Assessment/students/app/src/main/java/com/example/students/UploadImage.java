package com.example.students;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

//upload image via post
public interface UploadImage {
    @Multipart
    @POST("/COMP2000/api/students/{project}/image")
    //retrofit post call
    Call<Void> uploadImage(@Path("project") String project, @Part MultipartBody.Part part, @Part("file") RequestBody requestBody);
}
