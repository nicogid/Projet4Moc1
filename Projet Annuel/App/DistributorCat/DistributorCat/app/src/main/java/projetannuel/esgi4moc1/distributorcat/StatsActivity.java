package projetannuel.esgi4moc1.distributorcat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatsActivity extends AppCompatActivity {

    private static final String API_URL = "http://10.0.2.2:8000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        CalendarView simpleCalendarView = (CalendarView) findViewById(R.id.calendarView); // get the reference of CalendarView
        final long selectedDate = simpleCalendarView.getDate();

        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                final Date data = new Date(year - 1900, month, dayOfMonth);

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
                            Call<List<Sensor>> call = myService.sensors();

                            // Fetch and print a list of the Sensor to the library.
                            List<Sensor> sensors = null;
                            try {
                                sensors = call.execute().body();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            int cpt = 0;
                            ArrayList<String> tab = new ArrayList<String>();

                            for (Sensor sensor : sensors) {
                                SimpleDateFormat sdd = new SimpleDateFormat("'Le' dd/MM/yyyy 'à' HH:mm:ss");


                                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                                if (sdf.format(data).equals(sdf.format(sensor.getDate())) && (sensor.getName().equals("PIR"))) {
                                    tab.add(sdd.format(sensor.getDate()));
                                    cpt++;

                                }
                            }

                            String[] stockArr = new String[tab.size()];
                            stockArr = tab.toArray(stockArr);
                            final String[] tableau = stockArr;

                            runOnUiThread(new Runnable() {
                                public void run() {
                                    ListView maListView = (ListView) findViewById(R.id.mListView);
                                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(StatsActivity.this, android.R.layout.activity_list_item,
                                            android.R.id.text1, tableau);
                                    maListView.setAdapter(adapter);
                                }
                            });


                            if (cpt == 0) {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(getBaseContext(), "Aucun passage de votre minou ce jour là !", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            } else {
                                final int compteurFinal = cpt;
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
                                        Toast.makeText(getBaseContext(), "Votre chat a mangé " + compteurFinal
                                                + " fois , le : " + sdf.format(data), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

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