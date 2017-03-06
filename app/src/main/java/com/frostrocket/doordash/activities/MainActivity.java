package com.frostrocket.doordash.activities;

import android.Manifest;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.frostrocket.doordash.R;
import com.frostrocket.doordash.api.RestClient;
import com.frostrocket.doordash.api.model.Restaurant;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Single;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static android.provider.Contacts.SettingsColumns.KEY;

public class MainActivity extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback, GoogleMap.OnCameraMoveListener {
    private static final long POLLING_INTERVAL = 3000;
    private static final float LATITUDE_DELTA = 0.00001f;
    private static final float LONGITUDE_DELTA = 0.00001f;

    private static final int PERMISSIONS_REQUEST_FINE_LOCATION = 1;

    private GoogleMap map;

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

    private Location currentLocation;

    @BindView(R.id.map_view) MapView mapView;
    @BindView(R.id.map_marker) ImageView marker;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        MapsInitializer.initialize(this);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(POLLING_INTERVAL);

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        setSupportActionBar(toolbar);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

        if (googleApiClient != null && !googleApiClient.isConnected()) {
            googleApiClient.connect();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();

        if (googleApiClient != null && googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_show_favorites) {
            Intent intent = new Intent(this, FavoritesActivity.class);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setOnCameraMoveListener(this);
    }

    @Override
    public void onCameraMove() {
        if(currentLocation != null) {
            if ((Math.abs(map.getCameraPosition().target.latitude - currentLocation.getLatitude()) < LATITUDE_DELTA) ||
                    (Math.abs(map.getCameraPosition().target.longitude - currentLocation.getLongitude()) < LONGITUDE_DELTA)) {
                marker.setVisibility(View.INVISIBLE);
            } else {
                marker.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Timber.i("Location services connected.");

        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION
        };

        if(EasyPermissions.hasPermissions(this, permissions)) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);

                currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                map.setMyLocationEnabled(true);
            }
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.location_permission_request),
                    PERMISSIONS_REQUEST_FINE_LOCATION, permissions);
        }

        if(currentLocation != null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 17));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Timber.i("Location services suspended.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Timber.e("Failed to connect to location services.");
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
    }

    @OnClick(R.id.fab)
    public void find(View view) {
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra("LATITUDE", map.getCameraPosition().target.latitude);
        intent.putExtra("LONGITUDE", map.getCameraPosition().target.longitude);

        startActivity(intent);
    }
}
