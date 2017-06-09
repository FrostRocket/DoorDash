package com.frostrocket.doordash.workers;

import android.support.test.espresso.idling.CountingIdlingResource;
import android.view.View;
import android.widget.ProgressBar;

import com.frostrocket.doordash.adapters.RestaurantsAdapter;
import com.frostrocket.doordash.api.RestClient;
import com.frostrocket.doordash.api.model.Restaurant;

import java.util.List;
import java.util.concurrent.Callable;

import rx.Single;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class TestRestaurantsWorker implements RestaurantsWorker {

    private CountingIdlingResource countingIdlingResource;

    public TestRestaurantsWorker() {
        countingIdlingResource = new CountingIdlingResource("ResultsReady");
    }

    @Override
    public void retrieveAndSetRestaurants(final double latitude, final double longitude, final RestaurantsAdapter restaurantsAdapter, final ProgressBar progressBar) {
        Single<List<Restaurant>> restaurantSingle = Single.fromCallable(new Callable<List<Restaurant>>() {
            @Override
            public List<Restaurant> call() throws Exception {
                progressBar.setVisibility(View.VISIBLE);

                return RestClient.getInstance().getRestaurants(latitude, longitude);
            }
        });

        countingIdlingResource.increment();
        restaurantSingle.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleSubscriber<List<Restaurant>>() {
            @Override
            public void onSuccess(List<Restaurant> restaurants) {
                progressBar.setVisibility(View.INVISIBLE);

                for(Restaurant restaurant : restaurants) {
                    restaurantsAdapter.add(restaurant);
                }

                countingIdlingResource.decrement();
            }

            @Override
            public void onError(Throwable error) {
                Timber.e(error.toString());

                countingIdlingResource.decrement();
            }
        });
    }

    public CountingIdlingResource getCountingIdlingResource() {
        return countingIdlingResource;
    }
}
