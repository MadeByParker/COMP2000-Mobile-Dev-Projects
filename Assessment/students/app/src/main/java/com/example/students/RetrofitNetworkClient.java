package com.example.students;

import static com.example.students.swipe.SwipeAdapter.SelectedProjectId;

import android.util.Log;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//retrofit client access to api
public class RetrofitNetworkClient {
    private static Retrofit retrofit;
    private static String BASE_URL = "http://web.socem.plymouth.ac.uk/COMP2000/api/students/" + String.valueOf(SelectedProjectId) + "/";

    public static Retrofit getRetrofit() {
        Log.d("testurl", BASE_URL);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build(); //build  url
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
                    addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();//convert image
        }
        return retrofit;
    }
}