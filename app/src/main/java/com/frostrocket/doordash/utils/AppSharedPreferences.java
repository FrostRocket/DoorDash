package com.frostrocket.doordash.utils;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.frostrocket.doordash.DoorDash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppSharedPreferences {
    private static final String RESTAURANT_IDS = "restaurant_ids";

    private static final SharedPreferences sharedPreferences = DoorDash.getAppSharedPreferences();

    public static List<String> getRestaurantIds() {
        String restaurantIds = sharedPreferences.getString(RESTAURANT_IDS, "");

        if(!TextUtils.isEmpty(restaurantIds)) {
            return new ArrayList<>(Arrays.asList(restaurantIds.split(",")));
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

    public static boolean isRestaurantFavorited(int id) {
        List<String> restaurantIds = getRestaurantIds();

        return restaurantIds.contains(Integer.toString(id));
    }
}
