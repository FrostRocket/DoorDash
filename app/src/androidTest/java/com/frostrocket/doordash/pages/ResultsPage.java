package com.frostrocket.doordash.pages;

import com.frostrocket.doordash.R;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

public class ResultsPage extends BaseRestaurantsPage {

    public static final String LATITUDE_KEY = "LATITUDE";
    public static final String LONGITUDE_KEY = "LONGITUDE";
    public static final double DEFAULT_LATITUDE = 37.422740;
    public static final double DEFAULT_LONGITUDE = -122.139956;

    @Override
    protected void verifyAtPage() {
        getRecyclerView().check(matches(isDisplayed()));
    }

    @Override
    public int getRecyclerViewId() {
        return R.id.recycler_view_results;
    }
}
