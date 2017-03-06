package com.frostrocket.doordash.activities;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

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

import static com.frostrocket.doordash.activities.ResultsActivity.getThemeAccentColor;

public class FavoritesActivity extends AppCompatActivity  {
    @BindView(R.id.recycler_view_favorites) RecyclerView favoritesRecyclerView;
    @BindView(R.id.loading_spinner) ProgressBar progressBar;

    private RestaurantsAdapter restaurantsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_favorites);

        ButterKnife.bind(this);

        progressBar.getIndeterminateDrawable().setColorFilter(getThemeAccentColor(this), PorterDuff.Mode.MULTIPLY);

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

    public void getRestaurant(final int id) {
        Single<Restaurant> restaurantSingle = Single.fromCallable(new Callable<Restaurant>() {
            @Override
            public Restaurant call() throws Exception {
                return RestClient.getInstance().getRestaurant(id);
            }
        });

        restaurantSingle.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleSubscriber<Restaurant>() {
            @Override
            public void onSuccess(Restaurant restaurant) {
                progressBar.setVisibility(View.INVISIBLE);

                restaurantsAdapter.add(restaurant);
            }

            @Override
            public void onError(Throwable error) {
                Timber.e(error.toString());
            }
        });
    }
}
