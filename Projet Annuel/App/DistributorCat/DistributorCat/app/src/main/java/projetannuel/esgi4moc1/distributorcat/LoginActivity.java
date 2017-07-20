package projetannuel.esgi4moc1.distributorcat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private static final String API_URL = "http://10.0.2.2:8000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Declaration des vues
        final EditText login = (EditText) findViewById(R.id.login);
        final EditText password = (EditText) findViewById(R.id.password);
        Button loginButton = (Button) findViewById(R.id.connexion);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                        try {

                            // Create a call instance for looking up Retrofit contributors.
                            Call<List<Sensor>> callSensors = myService.sensors();

                            // Fetch and print a list of the Sensor to the library.
                            List<Sensor> sensors = null;
                            try {
                                sensors = callSensors.execute().body();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            final List<Sensor> sensors2 = sensors;


                            MyService loginService =
                                    ServiceGenerator.createService(MyService.class, login.getText().toString(), password.getText().toString());
                            Call<User> call = loginService.basicLogin();
                            call.enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    if (response.isSuccessful()) {
                                        int trouve = 0;

                                        for (final Sensor sensor : sensors2) {
                                            if (sensor.getName().equals("Ultrason")) {
                                                trouve++;


                                                AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
                                                builder1.setMessage("ALERTE : Le reservoir de votre chat est vide !");
                                                builder1.setCancelable(true);

                                                builder1.setPositiveButton(
                                                        "Plus tard",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                Intent intent = new Intent(LoginActivity.this, StatsActivity.class);
                                                                startActivity(intent);
                                                                dialog.cancel();
                                                            }
                                                        });

                                                builder1.setNegativeButton(
                                                        "Je l'ai rempli",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {

                                                                myService.deleteSensor(sensor.getId()).enqueue(new Callback<Void>() {
                                                                    @Override
                                                                    public void onResponse(Call<Void> call, Response<Void> response) {

                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<Void> call, Throwable t) {

                                                                    }
                                                                });

                                                                Intent intent = new Intent(LoginActivity.this, StatsActivity.class);
                                                                startActivity(intent);
                                                                dialog.cancel();
                                                            }
                                                        });
                                                AlertDialog alert11 = builder1.create();
                                                alert11.show();


                                            }

                                        }
                                        if (trouve == 0) {
                                            Intent intent = new Intent(LoginActivity.this, StatsActivity.class);
                                            startActivity(intent);
                                        }


                                    } else {
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                Toast.makeText(getBaseContext(), "Identifiants incorrects", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    // something went completely south (like no internet connection)
                                    Log.d("Error connection", t.getMessage());
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                });

                thread.start();

            }


        });

    }

}

