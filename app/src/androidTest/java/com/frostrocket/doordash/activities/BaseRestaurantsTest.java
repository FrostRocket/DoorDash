package com.frostrocket.doordash.activities;

import android.support.test.espresso.ViewInteraction;

import com.frostrocket.doordash.AppSharedPreferences;
import com.frostrocket.doordash.R;
import com.frostrocket.doordash.api.RestaurantClient;
import com.frostrocket.doordash.api.model.Restaurant;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.frostrocket.doordash.matchers.CustomMatchers.withDrawable;
import static org.hamcrest.core.Is.is;

public abstract class BaseRestaurantsTest {

    protected static final int DEFAULT_NUMBER = 5;

    protected List<String> favoriteRestaurantIds;

    protected void clearSharedPreferencesFavorites() {
        List<String> favoriteRestaurantIds = AppSharedPreferences.getRestaurantIds();
        for (String restaurantId : favoriteRestaurantIds) {
            AppSharedPreferences.removeRestaurantId(Integer.valueOf(restaurantId));
        }
    }

    protected void verifyRows(final Map<String, ViewInteraction> rows, final List<String> restaurantIds) throws Exception {
        assertThat(rows.size(), is(restaurantIds.size()));

        for (String restaurantId : restaurantIds) {
            Restaurant restaurant = RestaurantClient.getRestaurant(restaurantId);
            ViewInteraction row = rows.get(restaurantId);
            row.check(matches(hasDescendant(withText(restaurant.getName()))));
            row.check(matches(hasDescendant(withText(restaurant.getDescription()))));
            verifyRowFavorite(row, restaurantId);
        }
    }

    protected void verifyRowFavorite(final ViewInteraction row, final String restaurantId) {
        if (favoriteRestaurantIds.indexOf(restaurantId) == -1) {
            row.check(matches(hasDescendant(withDrawable(R.drawable.ic_heart_empty))));
        } else {
            row.check(matches(hasDescendant(withDrawable(R.drawable.ic_heart_filled))));
        }
    }

    protected int getDefaultRandomNumber() {
        return new Random().nextInt(DEFAULT_NUMBER);
    }
}
