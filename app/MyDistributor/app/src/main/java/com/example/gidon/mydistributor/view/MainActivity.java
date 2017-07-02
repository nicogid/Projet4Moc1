package com.example.gidon.mydistributor.view;

import com.example.gidon.mydistributor.service.GithubService;

import android.app.Activity;
import android.os.Bundle;

import com.example.gidon.mydistributor.R;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GithubService githubService = new Retrofit.Builder()
                .baseUrl(GithubService.ENDPOINT)

                //convertie le json automatiquement
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubService.class);
    }
}
