package com.frostrocket.doordash.dagger;

import com.frostrocket.doordash.DoorDash;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        RestaurantsWorkerModule.class
})
public interface ResultsComponent {

    void inject(DoorDash doorDash);
}
