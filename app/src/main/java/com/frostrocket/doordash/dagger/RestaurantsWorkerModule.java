package com.frostrocket.doordash.dagger;

import com.frostrocket.doordash.workers.DefaultRestaurantsWorker;
import com.frostrocket.doordash.workers.RestaurantsWorker;

import dagger.Module;
import dagger.Provides;

@Module
public class RestaurantsWorkerModule {

    @Provides
    RestaurantsWorker provideRestaurantsWorker() {
        return new DefaultRestaurantsWorker();
    }
}
