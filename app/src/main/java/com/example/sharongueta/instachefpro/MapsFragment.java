package com.example.sharongueta.instachefpro;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sharongueta.instachefpro.Activities.RecipeDetailsActivity;
import com.example.sharongueta.instachefpro.Model.Recipe;
import com.example.sharongueta.instachefpro.Model.RecipeViewModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, LocationListener, View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    Button buttonGo;
    EditText whereToGo;
    RecipeViewModel recipeVm;

    GoogleMap mGoogleMap;
    GoogleApiClient mGoogleApiClient;
    LocationRequest locationRequest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.map_screen,container,false);
        recipeVm = ViewModelProviders.of(this).get(RecipeViewModel.class);

        bindWidgetsOfView(view);


        if (googleServicesAvailiable()) {
            Toast.makeText(getContext(), "you have a google play services and its perfect!!!", Toast.LENGTH_LONG).show();
            bindWidgetsOfView(view);
            setListeners();

            initMap();
        } else {
            //no Google Map Layout
        }

        return view;
    }

    private void bindWidgetsOfView(View view) {

        buttonGo = view.findViewById(R.id.map_screen_Go);
        whereToGo = view.findViewById(R.id.map_screen_whereToGo_plainTxt);

    }

    private void setListeners() {
        buttonGo.setOnClickListener(this);
    }

    private void initMap() {

        SupportMapFragment mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        mapFrag.getMapAsync(this);

    }

    public boolean googleServicesAvailiable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(getContext());
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this.getActivity(), isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(getContext(), "Cant to connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        goToLocation(32.053003, 34.777894, 10);
        getAllRecipe();

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();
    }

    private void goToLocation(double lat, double lng) {

        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
        mGoogleMap.moveCamera(update);
    }


    private void goToLocation(double lat, double lng, int zoom) {

        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mGoogleMap.moveCamera(update);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getContext().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && getContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        if (location == null) {
            Toast.makeText(getContext(), "Cant get current location", Toast.LENGTH_LONG).show();
        } else {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 15);
            mGoogleMap.animateCamera(update);
        }
    }

    public void geoLocate() throws IOException {

        String location = whereToGo.getText().toString().trim();

        Geocoder gc = new Geocoder(getContext());

        List<Address> list = gc.getFromLocationName(location, 1);
        Address address = list.get(0);
        String locality = address.getLocality();

        Toast.makeText(getContext(), locality, Toast.LENGTH_LONG).show();

        double lat = address.getLatitude();
        double lng = address.getLongitude();

        goToLocation(lat, lng, 15);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.map_screen_Go:
                try {
                    Toast.makeText(getContext(), "GO", Toast.LENGTH_LONG).show();
                    geoLocate();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void getAllRecipe(){

        recipeVm.getAllRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                recipeVm.setRecipesDetailsSnapshotList(recipes);
                setMarkers();

            }
        });
    }

    public void setMarkers() {

        List<Recipe> recipes = new ArrayList<>();
        recipes = recipeVm.getRecipesDetailsSnapshotList();

        for (int i = 0; i < recipes.size(); i++) {
            Recipe recipe = recipes.get(i);
            MarkerOptions options = new MarkerOptions()
                    .snippet(recipe.getRecipeId())
                    .title(recipe.getName())
                    .position(new LatLng(recipe.getLat(), recipe.getLon()));
            mGoogleMap.addMarker(options);

            mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    String key = marker.getSnippet();
                    //Toast.makeText(getContext(), "Clicked"+marker.getTitle(), Toast.LENGTH_SHORT).show();
                    if (!key.isEmpty()) {
                        Intent intent = new Intent(getContext(), RecipeDetailsActivity.class);
                        intent.putExtra("recipeId", key);


                        startActivity(intent);
                    }
                    return false;
                }
            });

        }
    }
}