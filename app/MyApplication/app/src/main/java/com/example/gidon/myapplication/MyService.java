package com.example.gidon.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by gidon on 04/07/2017.
 */

public interface MyService {

    @GET("/api/sensors")
    Call<List<Sensor>> sensors();
}
