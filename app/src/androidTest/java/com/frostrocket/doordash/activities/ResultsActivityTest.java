package com.frostrocket.doordash.activities;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.frostrocket.doordash.DoorDash;
import com.frostrocket.doordash.api.RestaurantClient;
import com.frostrocket.doordash.api.model.Restaurant;
import com.frostrocket.doordash.dagger.DaggerResultsComponent;
import com.frostrocket.doordash.dagger.TestRestaurantsWorkerModule;
import com.frostrocket.doordash.pages.ResultsPage;
import com.frostrocket.doordash.workers.TestRestaurantsWorker;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static com.frostrocket.doordash.assertions.PageAssertion.assertAt;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ResultsActivityTest extends BaseRestaurantsTest {

    @Rule
    public ActivityTestRule<ResultsActivity> resultsActivityRule = new ActivityTestRule(ResultsActivity.class, true, false);
    private double latitude = ResultsPage.DEFAULT_LATITUDE;
    private double longitude = ResultsPage.DEFAULT_LONGITUDE;
    private List<String> favoriteRestaurantIds = new ArrayList<>();
    private List<String> resultRestaurantIds;
    private DoorDash doorDash;
    private CountingIdlingResource countingIdlingResource;
    private RecyclerView resultsRecyclerView;
    private ResultsPage resultsPage;

    @Before
    public void setUp() throws Exception {
        injectTestModulesToApp();
        clearSharedPreferencesFavorites();
        retrieveRestaurantsAndAddToResults();
        launchActivityWithIntent();
        initRestaurantsWorkerWithIdlingResources();
        resultsRecyclerView = resultsActivityRule.getActivity().resultsRecyclerView;
        resultsPage = new ResultsPage();
    }

    @After
    public void tearDown() {
        // unregister counting idling resource
        Espresso.unregisterIdlingResources(countingIdlingResource);
    }

    @Test
    public void loadComponents() {
        // recycler view
        resultsPage.getRecyclerView().check(matches(isDisplayed()));

        // loading spinner
        resultsPage.getLoadingSpinner().check(matches(not(isDisplayed())));
    }

    @Test
    public void showResults() throws Exception {
        verifyResults();
    }

    @Test
    public void addFavorites() throws Exception {
        // given
        List<String> idsToAdd = new ArrayList<>();
        int numToAdd = getDefaultRandomNumber();
        for (int i = 0; i < numToAdd; i++) {
            int index = getDefaultRandomNumber();
            String restaurantId = resultRestaurantIds.get(index);
            if (!idsToAdd.contains(restaurantId)) {
                idsToAdd.add(restaurantId);
            }
        }

        // when
        for (String idToAdd : idsToAdd) {
            resultsPage.clickFavorite(idToAdd, resultsRecyclerView);
            favoriteRestaurantIds.add(idToAdd);
        }

        // then
        verifyResults();
    }

    public void addFavoriteThenRemoveOut() throws Exception {
        // given
        int index = getDefaultRandomNumber();
        String restaurantId = resultRestaurantIds.get(index);
        resultsPage.clickFavorite(restaurantId, resultsRecyclerView);

        // when
        resultsPage.clickFavorite(restaurantId, resultsRecyclerView);

        // then
        verifyResults();
    }

    @Test
    public void removeFavorite() throws Exception {
        // given
        int index = getDefaultRandomNumber();
        String restaurantId = resultRestaurantIds.get(index);
        resultsPage.clickFavorite(restaurantId, resultsRecyclerView);

        // when
        resultsPage.clickFavorite(restaurantId, resultsRecyclerView);

        // then
        verifyResults();
    }

    private void injectTestModulesToApp() {
        doorDash = (DoorDash) InstrumentationRegistry.getTargetContext().getApplicationContext();
        DaggerResultsComponent.builder().restaurantsWorkerModule(new TestRestaurantsWorkerModule()).build().inject(doorDash);
    }

    private void retrieveRestaurantsAndAddToResults() throws IOException {
        List<Restaurant> restaurants = RestaurantClient.getRestaurants(latitude, longitude);
        resultRestaurantIds = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            resultRestaurantIds.add(restaurant.getId().toString());
        }
    }

    private void launchActivityWithIntent() {
        Intent intent = new Intent();
        intent.putExtra(ResultsPage.LATITUDE_KEY, latitude);
        intent.putExtra(ResultsPage.LONGITUDE_KEY, longitude);
        resultsActivityRule.launchActivity(intent);
    }

    private void initRestaurantsWorkerWithIdlingResources() {
        // set idling resource
        countingIdlingResource = ((TestRestaurantsWorker) doorDash.getRestaurantsWorker()).getCountingIdlingResource();
        assert null != countingIdlingResource;

        // register counting idling resource
        Espresso.registerIdlingResources(countingIdlingResource);
    }

    private void verifyResults() throws Exception {
        assertAt(ResultsPage.class);
        Map<String, ViewInteraction> rows = resultsPage.getRows(resultRestaurantIds, resultsRecyclerView);
        verifyRows(rows, resultRestaurantIds);
    }
}
