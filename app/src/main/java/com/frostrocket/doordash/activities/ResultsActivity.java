package com.frostrocket.doordash.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.frostrocket.doordash.R;
import com.frostrocket.doordash.adapters.RestaurantsAdapter;
import com.frostrocket.doordash.api.RestClient;
import com.frostrocket.doordash.api.model.Restaurant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Single;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class ResultsActivity extends AppCompatActivity  {
    @BindView(R.id.recycler_view_results) RecyclerView resultsRecyclerView;

    private RestaurantsAdapter restaurantsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_results);

        ButterKnife.bind(this);

        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        resultsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        restaurantsAdapter = new RestaurantsAdapter(this);
        resultsRecyclerView.setAdapter(restaurantsAdapter);

        getRestaurants(getIntent().getDoubleExtra("LATITUDE", 37.422740), getIntent().getDoubleExtra("LONGITUDE", -122.139956));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void getRestaurants(final double latitude, final double longitude) {
        Single<List<Restaurant>> restaurantSingle = Single.fromCallable(new Callable<List<Restaurant>>() {
            @Override
            public List<Restaurant> call() throws Exception {
                RestClient client = RestClient.getInstance("https://api.doordash.com");

                return client.getRestaurants(latitude, longitude);
            }
        });

        restaurantSingle.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleSubscriber<List<Restaurant>>() {
            @Override
            public void onSuccess(List<Restaurant> restaurants) {
                for(Restaurant restaurant : restaurants) {
                    restaurantsAdapter.add(restaurant);
                }
            }

            @Override
            public void onError(Throwable error) {
                Timber.i("Something went terribly wrong");
            }
        });
    }
}
