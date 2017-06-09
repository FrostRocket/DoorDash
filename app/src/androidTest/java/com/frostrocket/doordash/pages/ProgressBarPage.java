package com.frostrocket.doordash.pages;

import android.support.test.espresso.ViewInteraction;

import com.frostrocket.doordash.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class ProgressBarPage extends BasePage {

    @Override
    protected void verifyAtPage() {
        // do nothing
    }

    public ViewInteraction getLoadingSpinner() {
        return onView(withId(R.id.loading_spinner));
    }
}
