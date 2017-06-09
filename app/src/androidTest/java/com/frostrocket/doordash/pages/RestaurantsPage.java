package com.frostrocket.doordash.pages;

import android.support.test.espresso.ViewInteraction;
import android.support.v7.widget.RecyclerView;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface RestaurantsPage {

    int getRecyclerViewId();

    ViewInteraction getRecyclerView();

    ViewInteraction getLoadingSpinner();

    int getAdapterPosition(final String restaurantId, final RecyclerView recyclerView) throws IOException;

    ViewInteraction getRow(final int adapterPosition) throws IOException;

    Map<String, ViewInteraction> getRows(final List<String> restaurantIds, final RecyclerView recyclerView) throws IOException;

    void clickFavorite(final String restaurantId, final RecyclerView recyclerView) throws IOException;
}
