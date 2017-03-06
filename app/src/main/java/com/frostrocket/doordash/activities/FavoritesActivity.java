package com.frostrocket.doordash.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.frostrocket.doordash.AppSharedPreferences;
import com.frostrocket.doordash.R;
import com.frostrocket.doordash.adapters.RestaurantsAdapter;
import com.frostrocket.doordash.api.RestClient;
import com.frostrocket.doordash.api.model.Restaurant;

import java.util.List;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Single;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class FavoritesActivity extends AppCompatActivity  {
    @BindView(R.id.recycler_view_favorites) RecyclerView favoritesRecyclerView;

    private RestaurantsAdapter restaurantsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_favorites);

        ButterKnife.bind(this);

        favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        favoritesRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        favoritesRecyclerView.setItemAnimator(new DefaultItemAnimator());

        restaurantsAdapter = new RestaurantsAdapter(this);
        favoritesRecyclerView.setAdapter(restaurantsAdapter);

        List<String> restaurantIds = AppSharedPreferences.getRestaurantIds();

        for(String id : restaurantIds) {
            getRestaurant(Integer.valueOf(id));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void getRestaurant(final int id) {
        Single<Restaurant> restaurantSingle = Single.fromCallable(new Callable<Restaurant>() {
            @Override
            public Restaurant call() throws Exception {
                RestClient client = RestClient.getInstance("https://api.doordash.com");

                return client.getRestaurant(id);
            }
        });

        restaurantSingle.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleSubscriber<Restaurant>() {
            @Override
            public void onSuccess(Restaurant restaurant) {
                restaurantsAdapter.add(restaurant);
            }

            @Override
            public void onError(Throwable error) {
                Timber.i("Something went terribly wrong");
            }
        });
    }
}
