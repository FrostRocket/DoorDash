package com.frostrocket.doordash.pages;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.v7.widget.RecyclerView;

import com.frostrocket.doordash.R;
import com.frostrocket.doordash.adapters.RestaurantsAdapter;
import com.frostrocket.doordash.api.model.Restaurant;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.frostrocket.doordash.matchers.CustomMatchers.withRecyclerView;
import static org.hamcrest.core.AllOf.allOf;

public abstract class BaseRestaurantsPage extends BasePage implements RestaurantsPage {

    @Override
    public ViewInteraction getRecyclerView() {
        return onView(withId(getRecyclerViewId()));
    }

    @Override
    public ViewInteraction getLoadingSpinner() {
        return new ProgressBarPage().getLoadingSpinner();
    }

    @Override
    public int getAdapterPosition(final String restaurantId, final RecyclerView recyclerView) throws IOException {
        List<Restaurant> restaurants = getRestaurantsFromAdapter(recyclerView);
        Restaurant restaurant = getRestaurantFromList(restaurantId, restaurants);
        return restaurants.indexOf(restaurant);
    }

    @Override
    public ViewInteraction getRow(final int adapterPosition) throws IOException {
        scrollToPosition(adapterPosition);
        return onView(withRecyclerView(getRecyclerViewId()).atPosition(adapterPosition));
    }

    @Override
    public Map<String, ViewInteraction> getRows(final List<String> restaurantIds, final RecyclerView recyclerView) throws IOException {
        Map<String, ViewInteraction> rows = new HashMap<>();
        for (String restaurantId : restaurantIds) {
            int adapterPosition = getAdapterPosition(restaurantId, recyclerView);
            rows.put(restaurantId, getRow(adapterPosition));
        }
        return rows;
    }

    @Override
    public void clickFavorite(final String restaurantId, final RecyclerView recyclerView) throws IOException {
        int adapterPosition = getAdapterPosition(restaurantId, recyclerView);
        scrollToPosition(adapterPosition);
        List<Restaurant> restaurants = getRestaurantsFromAdapter(recyclerView);
        Restaurant restaurant = getRestaurantFromList(restaurantId, restaurants);
        onView(allOf(withId(R.id.image_restaurant_favorite), hasSibling(withText(restaurant.getName())))).perform(click());
    }

    private Restaurant getRestaurantFromList(String restaurantId, List<Restaurant> restaurants) {
        for (Restaurant actualRestaurant : restaurants) {
            if (restaurantId.equals(actualRestaurant.getId().toString())) {
                return actualRestaurant;
            }
        }
        throw new IllegalArgumentException("Restaurant id: " + restaurantId + " does not exist, restaurants size: " + restaurants.size());
    }

    private List<Restaurant> getRestaurantsFromAdapter(RecyclerView recyclerView) {
        RestaurantsAdapter restaurantsAdapter = (RestaurantsAdapter) recyclerView.getAdapter();
        return restaurantsAdapter.getRestaurants();
    }

    private void scrollToPosition(int adapterPosition) {
        onView(withId(getRecyclerViewId())).perform(RecyclerViewActions.scrollToPosition(adapterPosition));
    }
}
