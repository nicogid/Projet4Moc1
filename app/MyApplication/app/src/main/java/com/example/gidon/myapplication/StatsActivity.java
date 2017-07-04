package com.example.gidon.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatsActivity extends AppCompatActivity {

    private static final String API_URL = "http://192.168.1.23:8000" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our GitHub API interface.
        final MyService myService = retrofit.create(MyService.class);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    // Create a call instance for looking up Retrofit contributors.
                    Call<List<Sensor>> call = myService.sensors();

                    // Fetch and print a list of the Sensor to the library.
                    List<Sensor> sensors = null;
                    try {
                        sensors = call.execute().body();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (Sensor sensor : sensors) {
                        System.out.println("name : " + sensor.getName() + " ( id : " + sensor.getId() + ") date : " + sensor.getDate());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
