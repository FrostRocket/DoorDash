package com.frostrocket.doordash;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import timber.log.Timber;

public class AppSharedPreferences {
    private static final String RESTAURANT_IDS = "restaurant_ids";

    private static final SharedPreferences sharedPreferences = DoorDash.getAppSharedPreferences();

    public static List<String> getRestaurantIds() {
        String restaurantIds = sharedPreferences.getString(RESTAURANT_IDS, "");

        if(!TextUtils.isEmpty(restaurantIds)) {
            return Arrays.asList(restaurantIds.split(","));
        } else {
            return new ArrayList<>();
        }
    }

    public static void setRestaurantIds(List<String> restaurantIds) {
        String idsCommaDelimited = "";

        for(String id : restaurantIds) {
            idsCommaDelimited += id + ",";
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(RESTAURANT_IDS, idsCommaDelimited);
        editor.apply();
    }

    public static void addRestaurantId(int id) {
        List<String> restaurantIds = getRestaurantIds();

        restaurantIds.add(Integer.toString(id));

        setRestaurantIds(restaurantIds);
    }

    public static void removeRestaurantId(int id) {
        List<String> restaurantIds = getRestaurantIds();

        restaurantIds.remove(Integer.toString(id));

        setRestaurantIds(restaurantIds);
    }

    public static boolean doesRestaurantIdExist(int id) {
        List<String> restaurantIds = getRestaurantIds();

        return restaurantIds.contains(Integer.toString(id));
    }
}
