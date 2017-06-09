package com.frostrocket.doordash.matchers;

public class CustomMatchers {

    public static DrawableMatcher withDrawable(final int resourceId) {
        return new DrawableMatcher(resourceId);
    }

    public static DrawableMatcher noDrawable() {
        return new DrawableMatcher(-1);
    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}
