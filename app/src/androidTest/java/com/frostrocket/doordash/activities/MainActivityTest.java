package com.frostrocket.doordash.activities;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.frostrocket.doordash.pages.FavoritesPage;
import com.frostrocket.doordash.pages.MainPage;
import com.frostrocket.doordash.pages.ResultsPage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static com.frostrocket.doordash.assertions.PageAssertion.assertAt;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule(MainActivity.class);
    private MainPage mainPage;

    @Before
    public void setUp() {
        mainPage = new MainPage();
    }

    @Test
    public void loadComponents() {
        // toolbar
        mainPage.getToolbar().check(matches(isDisplayed()));

        // favorites menu option
        mainPage.getFavoritesMenuOption().check(matches(isDisplayed()));

        // map view
        mainPage.getMapView().check(matches(isDisplayed()));

        // map marker
        mainPage.getMapMarker().check(matches(not(isDisplayed())));

        // search button
        mainPage.getSearchButton().check(matches(isDisplayed()));
    }

    @Test
    public void clickFavoritesMenuOption() throws Exception {
        // click favorites menu option
        mainPage.clickFavoritesMenuOption();

        // assert at favorites page
        assertAt(FavoritesPage.class);
    }

    @Test
    public void clickSearchButton() throws Exception {
        // click search button
        mainPage.clickSearchButton();

        // assert at results page
        assertAt(ResultsPage.class);
    }
}
