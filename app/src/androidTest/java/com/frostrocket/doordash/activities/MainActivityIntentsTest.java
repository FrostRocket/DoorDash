package com.frostrocket.doordash.activities;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.frostrocket.doordash.pages.MainPage;
import com.frostrocket.doordash.pages.ResultsPage;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.BundleMatchers.hasEntry;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityIntentsTest {

    @Rule
    public IntentsTestRule<MainActivity> mainActivityRule = new IntentsTestRule<MainActivity>(MainActivity.class);
    @Rule
    public UiThreadTestRule threadTestRule = new UiThreadTestRule();
    private CountingIdlingResource countingIdlingResource = new CountingIdlingResource("MapReady");
    private GoogleMap googleMap;
    private MainPage mainPage;

    @Before
    public void setUp() throws Throwable {
        initMapViewWithIdlingResources();
        mainPage = new MainPage();
    }

    @After
    public void tearDown() {
        // unregister counting idling resource
        Espresso.unregisterIdlingResources(countingIdlingResource);
    }

    @Test
    public void addNewMapMarker() throws Throwable {
        // given
        final Double[] latLong = new Double[2];
        threadTestRule.runOnUiThread(new Runnable() {
            @Override public void run() {
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(new LatLng(ResultsPage.DEFAULT_LATITUDE, ResultsPage.DEFAULT_LONGITUDE)));
                latLong[0] = googleMap.getCameraPosition().target.latitude;
                latLong[1] = googleMap.getCameraPosition().target.longitude;
            }
        });

        // when
        mainPage.clickSearchButton();

        // then
        intended(allOf(
                toPackage("com.frostrocket.doordash"),
                hasExtras(allOf(
                        hasEntry(equalTo(ResultsPage.LATITUDE_KEY), equalTo(latLong[0])),
                        hasEntry(equalTo(ResultsPage.LONGITUDE_KEY), equalTo(latLong[1]))
                ))
        ));
    }

    private void initMapViewWithIdlingResources() throws Throwable {
        // setup counting idling resource on map ready callback
        countingIdlingResource.increment();
        final MapView mapView = mainActivityRule.getActivity().mapView;
        final OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback() {
            @Override public void onMapReady(GoogleMap googleMap) {
                countingIdlingResource.decrement();
                MainActivityIntentsTest.this.googleMap = googleMap;
            }
        };

        // run get map async on map view
        threadTestRule.runOnUiThread(new Runnable() {
            @Override public void run() {
                mapView.getMapAsync(onMapReadyCallback);
            }
        });

        // register counting idling resource
        Espresso.registerIdlingResources(countingIdlingResource);
    }
}
