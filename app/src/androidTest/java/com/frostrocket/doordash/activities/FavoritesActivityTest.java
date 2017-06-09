package com.frostrocket.doordash.activities;

import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.frostrocket.doordash.AppSharedPreferences;
import com.frostrocket.doordash.api.RestaurantClient;
import com.frostrocket.doordash.api.model.Restaurant;
import com.frostrocket.doordash.pages.FavoritesPage;
import com.frostrocket.doordash.pages.ResultsPage;

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
public class FavoritesActivityTest extends BaseRestaurantsTest {

    @Rule
    public ActivityTestRule<FavoritesActivity> favoritesActivityRule = new ActivityTestRule(FavoritesActivity.class, true, false);
    private List<String> originalFavoriteRestaurantIds;
    private RecyclerView favoritesRecyclerView;
    private FavoritesPage favoritesPage;

    @Before
    public void setUp() throws Exception {
        clearSharedPreferencesFavorites();
        retrieveRestaurantsAndAddToFavorites(5);
        launchActivityWithIntent();
        favoritesRecyclerView = favoritesActivityRule.getActivity().favoritesRecyclerView;
        favoritesPage = new FavoritesPage();
    }

    @Test
    public void loadComponents() {
        // recycler view
        favoritesPage.getRecyclerView().check(matches(isDisplayed()));

        // loading spinner
        favoritesPage.getLoadingSpinner().check(matches(not(isDisplayed())));
    }

    @Test
    public void showFavorites() throws Exception {
        verifyFavorites();
    }

    @Test
    public void removeFavorites() throws Exception {
        // given
        List<String> idsToRemove = new ArrayList<>();
        int numToRemove = getDefaultRandomNumber();
        for (int i = 0; i < numToRemove; i++) {
            String restaurantId = favoriteRestaurantIds.get(i);
            if (!idsToRemove.contains(restaurantId)) {
                idsToRemove.add(restaurantId);
            }
        }

        // when
        for (String idToRemove : idsToRemove) {
            favoritesPage.clickFavorite(idToRemove, favoritesRecyclerView);
            favoriteRestaurantIds.remove(idToRemove);
        }

        // then
        verifyFavorites();
    }

    @Test
    public void removeFavoriteThenAddBack() throws Exception {
        // given
        int index = getDefaultRandomNumber();
        String restaurantId = favoriteRestaurantIds.get(index);
        favoritesPage.clickFavorite(restaurantId, favoritesRecyclerView);

        // when
        favoritesPage.clickFavorite(restaurantId, favoritesRecyclerView);

        // then
        verifyFavorites();
    }

    private void retrieveRestaurantsAndAddToFavorites(int exclusiveIndex) throws IOException {
        List<Restaurant> restaurants = RestaurantClient.getRestaurants(ResultsPage.DEFAULT_LATITUDE, ResultsPage.DEFAULT_LONGITUDE).subList(0, exclusiveIndex);
        for (Restaurant restaurant : restaurants) {
            AppSharedPreferences.addRestaurantId(restaurant.getId());
        }
        favoriteRestaurantIds = new ArrayList<>();
        favoriteRestaurantIds.addAll(AppSharedPreferences.getRestaurantIds());
        originalFavoriteRestaurantIds = new ArrayList<>();
        originalFavoriteRestaurantIds.addAll(AppSharedPreferences.getRestaurantIds());
    }

    private void launchActivityWithIntent() {
        Intent intent = new Intent();
        favoritesActivityRule.launchActivity(intent);
    }

    private void verifyFavorites() throws Exception {
        assertAt(FavoritesPage.class);
        Map<String, ViewInteraction> rows = favoritesPage.getRows(originalFavoriteRestaurantIds, favoritesRecyclerView);
        verifyRows(rows, originalFavoriteRestaurantIds);
    }
}
