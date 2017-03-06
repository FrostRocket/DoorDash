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
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import timber.log.Timber;

public class RestClient {
    private static RestClient instance;
    private ApiService apiService;

    public static synchronized RestClient getInstance(String baseUrl){
        if(instance == null){
            instance = new RestClient(baseUrl);
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
        Call<List<Restaurant>> getRestaurants(@Query("lat") String latitude,
                                     @Query("lng") String longitude,
                                     @QueryMap Map<String, String> parameters);
    }

    public ApiService getApiService() {
        return apiService;
    }

    public List<Restaurant> getRestaurants(double latitude, double longitude, Map<String, String> parameters) throws IOException {
        Call<List<Restaurant>> call = apiService.getRestaurants(Double.toString(latitude), Double.toString(longitude), parameters);
        Timber.d("Restaurant Request: %s", call.request().url().toString());

        return call.execute().body();
    }
}
