package com.frostrocket.doordash.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.frostrocket.doordash.AppSharedPreferences;
import com.frostrocket.doordash.R;
import com.frostrocket.doordash.api.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ResultsViewHolder> {
    private List<Restaurant> restaurants;
    private Context context;

    public RestaurantsAdapter(Context context) {
        this.restaurants = new ArrayList<>();
        this.context = context;
    }

    class ResultsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.linearlayout_row_restaurant) LinearLayout layout;
        @BindView(R.id.text_restaurant_name) TextView name;
        @BindView(R.id.text_restaurant_description) TextView description;
        @BindView(R.id.image_restaurant_favorite) ImageView favorite;

        ResultsViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.image_restaurant_favorite)
        void onClick() {
            Restaurant restaurant = restaurants.get(getAdapterPosition());

            if(AppSharedPreferences.doesRestaurantIdExist(restaurant.getId())) {
                AppSharedPreferences.removeRestaurantId(restaurant.getId());

                String toast = String.format("Removed %s from favorites.", restaurant.getName());
                Toast.makeText(context, toast, Toast.LENGTH_LONG).show();
            } else {
                AppSharedPreferences.addRestaurantId(restaurant.getId());

                String toast = String.format("Added %s to favorites!", restaurant.getName());
                Toast.makeText(context, toast, Toast.LENGTH_LONG).show();
            }

            notifyItemChanged(getAdapterPosition());
        }
    }

    @Override
    public ResultsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_restaurant, parent, false);

        return new ResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ResultsViewHolder viewHolder, int position) {
        final Restaurant restaurant = restaurants.get(position);

        viewHolder.name.setText(restaurant.getName());
        viewHolder.description.setText(restaurant.getDescription());

        if(AppSharedPreferences.doesRestaurantIdExist(restaurant.getId())) {
            viewHolder.favorite.setImageResource(R.drawable.heart_filled);
        } else {
            viewHolder.favorite.setImageResource(R.drawable.heart_empty);
        }
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public void add(Restaurant restaurant) {
        add(restaurants.size(), restaurant);
    }

    public void add(int position, Restaurant restaurant) {
        restaurants.add(position, restaurant);

        notifyItemInserted(position);
    }

    public void removeAll() {
        restaurants.clear();
    }
}