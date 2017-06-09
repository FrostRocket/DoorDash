package com.frostrocket.doordash.matchers;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Modified and improved version.
 *
 * Source: https://spin.atomicobject.com/2016/04/15/espresso-testing-recyclerviews/
 * Fork of the RecyclerViewMatcher from https://github.com/dannyroa/espresso-samples
 * License: MIT (https://tldrlegal.com/license/mit-license)
 */
public class RecyclerViewMatcher {

    private final int recyclerViewId;

    public RecyclerViewMatcher(final int recyclerViewId) {
        this.recyclerViewId = recyclerViewId;
    }

    public Matcher<View> atPosition(final int position) {
        return atPositionOnView(position, -1);
    }

    public Matcher<View> atPositionOnView(final int position, final int targetViewId) {
        return new TypeSafeMatcher<View>() {
            Resources resources;
            View itemView;

            @Override
            public boolean matchesSafely(View view) {
                resources = view.getResources();

                RecyclerView recyclerView = (RecyclerView) view.getRootView().findViewById(recyclerViewId);
                if (null == recyclerView || recyclerViewId != recyclerView.getId()) {
                    return false;
                }

                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                if (null == itemView && null != viewHolder) {
                    itemView = viewHolder.itemView;
                }

                if (targetViewId == -1) {
                    return view == itemView;
                }

                if (null == itemView) {
                    return false;
                }

                View targetView = itemView.findViewById(targetViewId);
                return view == targetView;
            }

            @Override
            public void describeTo(Description description) {
                String resourceName = Integer.toString(recyclerViewId);
                if (null != resources) {
                    try {
                        resourceName = resources.getResourceName(recyclerViewId);
                    } catch (Resources.NotFoundException ex) {
                        resourceName = String.format("%s (resource name not found)", recyclerViewId);
                    }
                }

                description.appendText("RecyclerView with id: " + resourceName + " at position: " + position);
            }
        };
    }
}
