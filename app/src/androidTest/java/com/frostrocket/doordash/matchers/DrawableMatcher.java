package com.frostrocket.doordash.matchers;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Modified and improved version.
 *
 * Source: https://medium.com/@dbottillo/android-ui-test-espresso-matcher-for-imageview-1a28c832626f
 */
public class DrawableMatcher extends TypeSafeMatcher<View> {

    private final int drawableId;
    private Resources resources;

    public DrawableMatcher(int drawableId) {
        super(View.class);
        this.drawableId = drawableId;
    }

    @Override
    protected boolean matchesSafely(View view) {
        if (!(view instanceof ImageView)) {
            return false;
        }

        ImageView imageView = (ImageView) view;
        if (drawableId < 0) {
            return null == imageView.getDrawable();
        }

        resources = view.getResources();
        if (null == resources) {
            return false;
        }

        Drawable drawable = resources.getDrawable(drawableId, null);
        if (null == drawable) {
            return false;
        }

        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        Bitmap otherBitmap = ((BitmapDrawable) drawable).getBitmap();
        return bitmap.sameAs(otherBitmap);
    }

    @Override
    public void describeTo(Description description) {
        String resourceName = Integer.toString(drawableId);
        if (null != resources) {
            try {
                resourceName = resources.getResourceName(drawableId);
            } catch (Resources.NotFoundException ex) {
                resourceName = String.format("%s (resource name not found)", drawableId);
            }
        }

        description.appendText("Drawable with id: " + resourceName);
    }
}
