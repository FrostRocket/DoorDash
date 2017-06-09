package com.frostrocket.doordash.activities;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.frostrocket.doordash.DoorDash;
import com.frostrocket.doordash.R;
import com.frostrocket.doordash.adapters.RestaurantsAdapter;
import com.frostrocket.doordash.workers.RestaurantsWorker;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.frostrocket.doordash.utils.ColorUtils.getThemeAccentColor;

public class ResultsActivity extends AppCompatActivity  {
    @BindView(R.id.recycler_view_results) RecyclerView resultsRecyclerView;
    @BindView(R.id.loading_spinner) ProgressBar progressBar;

    private RestaurantsAdapter restaurantsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_results);

        ButterKnife.bind(this);

        progressBar.getIndeterminateDrawable().setColorFilter(getThemeAccentColor(this), PorterDuff.Mode.MULTIPLY);

        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        resultsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        restaurantsAdapter = new RestaurantsAdapter(this);
        resultsRecyclerView.setAdapter(restaurantsAdapter);

        double latitude = getIntent().getDoubleExtra("LATITUDE", 37.422740);
        double longitude = getIntent().getDoubleExtra("LONGITUDE", -122.139956);

        RestaurantsWorker restaurantsWorker = ((DoorDash) getApplicationContext()).getRestaurantsWorker();
        restaurantsWorker.retrieveAndSetRestaurants(latitude, longitude, restaurantsAdapter, progressBar);
    }
}
