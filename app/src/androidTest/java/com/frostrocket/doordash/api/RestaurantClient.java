package com.frostrocket.doordash.api;

import com.frostrocket.doordash.api.model.Restaurant;

import java.io.IOException;
import java.util.List;

public class RestaurantClient {

    public static Restaurant getRestaurant(final String restaurantId) throws IOException {
        return RestClient.getInstance().getRestaurant(Integer.valueOf(restaurantId));
    }

    public static List<Restaurant> getRestaurants(final double latitude, final double longitude) throws IOException {
        return RestClient.getInstance().getRestaurants(latitude, longitude);
    }
}
