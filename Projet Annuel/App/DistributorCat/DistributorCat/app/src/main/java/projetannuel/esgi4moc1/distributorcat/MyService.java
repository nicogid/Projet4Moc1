package projetannuel.esgi4moc1.distributorcat;

/**
 * Created by inas on 08/07/2017.
 */

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by gidon on 04/07/2017.
 */

public interface MyService {


    @GET("/api/sensors")
    Call<List<Sensor>> sensors();

    @POST("login/")
    Call<User> basicLogin();

    @DELETE("/api/sensors/{id}/")
    Call<Void> deleteSensor(@Path("id") int id);
}
