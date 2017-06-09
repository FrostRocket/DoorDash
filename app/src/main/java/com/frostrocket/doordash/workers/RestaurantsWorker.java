package com.frostrocket.doordash.workers;

import android.widget.ProgressBar;

import com.frostrocket.doordash.adapters.RestaurantsAdapter;

public interface RestaurantsWorker {

    void retrieveAndSetRestaurants(final double latitude, final double longitude, final RestaurantsAdapter restaurantsAdapter, final ProgressBar progressBar);
}
