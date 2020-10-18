package com.uriallab.haat.haat.UI.Activities.makeOrder;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.Utilities.GlobalVariables;
import com.uriallab.haat.haat.databinding.ActivityDelivieringLocationBinding;
import com.uriallab.haat.haat.viewModels.DeliveringLocationViewModel;

import java.util.List;
import java.util.Locale;

public class DelivieringLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    public ActivityDelivieringLocationBinding binding;

    public LatLng latLngTo;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_deliviering_location);

        binding.setDLocationVM(new DeliveringLocationViewModel(this));

        binding.locationTxt.setSelected(true);

        setUpMapIfNeeded();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        setMapLocation();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("address", "none");
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        99);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        99);
            }
        }
    }

    private void setMapLocation() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("RETURN", "return");
            return;
        }

        setUpMap();

        latLngTo = new LatLng(GlobalVariables.LOCATION_LAT, GlobalVariables.LOCATION_LNG);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLngTo).zoom(14).build();
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        mMap.setOnCameraIdleListener(() -> {

            latLngTo = mMap.getCameraPosition().target;

            getAddressFromLatLng(binding.locationTxt, latLngTo);
            Log.e("cameraMidLatLng", latLngTo.latitude + "\t" + latLngTo.longitude);
        });
    }

    private void setUpMap() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        String myMapStyle = "[{\"featureType\":\"all\",\"elementType\":\"labels.text.fill\",\"stylers\":[{\"color\":\"#7c93a3\"},{\"lightness\":\"-10\"}]},{\"featureType\":\"administrative.country\",\"elementType\":\"geometry\",\"stylers\":[{\"visibility\":\"on\"}]},{\"featureType\":\"administrative.country\",\"elementType\":\"geometry.stroke\",\"stylers\":[{\"color\":\"#c2d1d6\"}]},{\"featureType\":\"landscape\",\"elementType\":\"geometry.fill\",\"stylers\":[{\"color\":\"#dde3e3\"}]},{\"featureType\":\"road.highway\",\"elementType\":\"geometry.fill\",\"stylers\":[{\"color\":\"#c2d1d6\"}]},{\"featureType\":\"road.highway\",\"elementType\":\"geometry.stroke\",\"stylers\":[{\"color\":\"#a9b4b8\"},{\"lightness\":\"0\"}]},{\"featureType\":\"water\",\"elementType\":\"geometry.fill\",\"stylers\":[{\"color\":\"#a3c7df\"}]}]";
        MapStyleOptions style = new MapStyleOptions(myMapStyle);
        mMap.setMapStyle(style);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        latLngTo = new LatLng(GlobalVariables.LOCATION_LAT, GlobalVariables.LOCATION_LNG);
    }

    public void confirmLocation() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("address", binding.locationTxt.getText().toString());
        if (binding.additionalTxt.getText().toString().isEmpty())
            returnIntent.putExtra("add_address", "none");
        else
            returnIntent.putExtra("add_address", binding.additionalTxt.getText().toString());
        returnIntent.putExtra("lat", Double.valueOf(latLngTo.latitude));
        returnIntent.putExtra("lng", Double.valueOf(latLngTo.longitude));
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void getAddressFromLatLng(TextView textView, LatLng latLng) {
        try {

            Locale aLocale;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (ConfigurationFile.getCurrentLanguage(this).equals("ar"))
                    aLocale = new Locale.Builder().setLanguage("ar").build();
                else
                    aLocale = new Locale.Builder().setLanguage("en").build();
            } else {
                if (ConfigurationFile.getCurrentLanguage(this).equals("ar"))
                    aLocale = new Locale("ar");
                else
                    aLocale = new Locale("en");
            }

            Geocoder geo = new Geocoder(this, aLocale);
            List<Address> addresses = geo.getFromLocation(latLng.latitude, latLng.longitude, 2);
            if (addresses.isEmpty()) {
                textView.setText("Waiting for Location");
            } else {
                if (addresses.size() > 0) {
                    textView.setText(addresses.get(0).getAddressLine(0));
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }
    }
}