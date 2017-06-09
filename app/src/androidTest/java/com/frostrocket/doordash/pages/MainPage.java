package com.frostrocket.doordash.pages;

import android.support.test.espresso.ViewInteraction;

import com.frostrocket.doordash.R;
import com.frostrocket.doordash.utils.PermissionGranter;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class MainPage extends BasePage {

    @Override
    protected void verifyAtPage() {
        PermissionGranter.allowPermissionsIfNeeded(ACCESS_FINE_LOCATION);
        getMapView().check(matches(isDisplayed()));
    }

    public ViewInteraction getToolbar() {
        return onView(withId(R.id.toolbar));
    }

    public ViewInteraction getFavoritesMenuOption() {
        return new MainMenuPage().getFavoritesMenuOption();
    }

    public ViewInteraction getMapView() {
        return onView(withId(R.id.map_view));
    }

    public ViewInteraction getMapMarker() {
        return onView(withId(R.id.map_marker));
    }

    public ViewInteraction getSearchButton() {
        return onView(withId(R.id.fab));
    }

    public void clickFavoritesMenuOption() {
        new MainMenuPage().clickFavoritesMenuOption();
    }

    public void clickSearchButton() {
        getSearchButton().perform(click());
    }
}
