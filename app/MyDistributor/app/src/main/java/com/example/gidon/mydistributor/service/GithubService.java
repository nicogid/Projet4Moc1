package com.example.gidon.mydistributor.service;

import com.example.gidon.mydistributor.models.Sensor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by gidon on 28/06/2017.
 */

public interface GithubService {

    public static final String ENDPOINT = "https://localhost";

    @GET("/api/sensors")
    Call<List<Sensor>> listcapteur(@Path("user") String user);

    @GET("/search/repositories")
    Call<List<Sensor>> searchRepos(@Query("q") String query);
}
