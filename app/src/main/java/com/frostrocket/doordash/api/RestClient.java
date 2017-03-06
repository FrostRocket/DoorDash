package com.frostrocket.doordash.api;

import com.frostrocket.doordash.api.model.Restaurant;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import timber.log.Timber;

public class RestClient {
    private static RestClient instance;
    private ApiService apiService;

    public static synchronized RestClient getInstance(){
        if(instance == null){
            instance = new RestClient("https://api.doordash.com");
        }

        return instance;
    }

    private RestClient(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    private interface ApiService {
        @GET("v2/restaurant/")
        Call<List<Restaurant>> getRestaurants(@Query("lat") String latitude, @Query("lng") String longitude);

        @GET("v2/restaurant/{id}/")
        Call<Restaurant> getRestaurant(@Path("id") String id);
    }

    public ApiService getApiService() {
        return apiService;
    }

    public List<Restaurant> getRestaurants(double latitude, double longitude) throws IOException {
        Call<List<Restaurant>> call = apiService.getRestaurants(Double.toString(latitude), Double.toString(longitude));
        Timber.d("getRestaurants Request: %s", call.request().url().toString());

        return call.execute().body();
    }

    public Restaurant getRestaurant(int id) throws IOException {
        Call<Restaurant> call = apiService.getRestaurant(Integer.toString(id));
        Timber.d("getRestaurant Request: %s", call.request().url().toString());

        return call.execute().body();
    }
}
