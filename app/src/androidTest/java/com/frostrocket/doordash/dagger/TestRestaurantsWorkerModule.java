package com.frostrocket.doordash.dagger;

import com.frostrocket.doordash.workers.RestaurantsWorker;
import com.frostrocket.doordash.workers.TestRestaurantsWorker;

public class TestRestaurantsWorkerModule extends RestaurantsWorkerModule {

    @Override
    RestaurantsWorker provideRestaurantsWorker() {
        return new TestRestaurantsWorker();
    }
}
