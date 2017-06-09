package com.frostrocket.doordash.pages;

import com.frostrocket.doordash.R;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

public class FavoritesPage extends BaseRestaurantsPage {

    @Override
    protected void verifyAtPage() {
        getRecyclerView().check(matches(isDisplayed()));
    }

    @Override
    public int getRecyclerViewId() {
        return R.id.recycler_view_favorites;
    }
}
