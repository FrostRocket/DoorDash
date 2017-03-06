package com.frostrocket.doordash.utils;

import android.content.Context;
import android.util.TypedValue;

import com.frostrocket.doordash.R;

public class ColorUtils {
    public static int getThemeAccentColor(final Context context) {
        final TypedValue value = new TypedValue ();
        context.getTheme ().resolveAttribute (R.attr.colorPrimary, value, true);

        return value.data;
    }
}
