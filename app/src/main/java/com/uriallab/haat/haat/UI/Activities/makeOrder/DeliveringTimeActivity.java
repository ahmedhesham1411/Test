package com.uriallab.haat.haat.UI.Activities.makeOrder;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
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
import com.uriallab.haat.haat.Utilities.GlobalVariables;
import com.uriallab.haat.haat.databinding.ActivityDeliveringTimeBinding;
import com.uriallab.haat.haat.viewModels.DeliveringTimeViewModel;

import java.util.ArrayList;

public class DeliveringTimeActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ActivityDeliveringTimeBinding binding;

    private LatLng latLngTo;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;

    private DeliveringTimeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_delivering_time);

        viewModel = new DeliveringTimeViewModel(this);

        binding.setDTimeVM(viewModel);


        setUpMapIfNeeded();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        setMapLocation();
/*
        Button button = findViewById(R.id.next_btn2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
     /*   Typeface face =  ResourcesCompat.getFont(getApplicationContext(), R.font.coconnextarabic_light);
                binding.scrollChoice.setTypeface(face);*/

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
    public void onBackPressed() {
        //  super.onBackPressed();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("time", "none");
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
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

    public void confirmTime() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("time", binding.scrollChoice.getCurrentSelection());
        returnIntent.putExtra("timeId", String.valueOf(viewModel.timeListId.get(binding.scrollChoice.getCurrentItemPosition())));
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void no(){
        finish();
    }

    public void wheelTime(ArrayList<String> wheelListArabic) {
        binding.scrollChoice.addItems(wheelListArabic, 0);
    }
}