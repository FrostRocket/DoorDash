package com.frostrocket.doordash.pages;

import android.support.test.espresso.ViewInteraction;

import com.frostrocket.doordash.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class MainMenuPage extends BasePage {

    @Override
    protected void verifyAtPage() {
        getFavoritesMenuOption().check(matches(isDisplayed()));
    }

    public ViewInteraction getFavoritesMenuOption() {
        return onView(withId(R.id.action_show_favorites));
    }

    public void clickFavoritesMenuOption() {
        getFavoritesMenuOption().perform(click());
    }
}
