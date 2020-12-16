package com.uriallab.haat.haat.UI.Activities.makeOrder;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.uriallab.haat.haat.DataModels.OtherBranchesModel;
import com.uriallab.haat.haat.Interfaces.BranchClick;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Adapters.OtherBranchesAdapter;
import com.uriallab.haat.haat.Utilities.GlobalVariables;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.databinding.ActivityOtherBranchesBinding;
import com.uriallab.haat.haat.viewModels.OtherBranchesViewModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OtherBranchesActivity extends AppCompatActivity implements OnMapReadyCallback, BranchClick {

    private ActivityOtherBranchesBinding binding;

    private OtherBranchesModel storeDetailsModel;

    private GoogleApiClient mGoogleApiClient;
    Marker marker2;
    String address;
    Double lat,lng;

    private GoogleMap mMap;
    OtherBranchesAdapter otherBranchesAdapter;
    List<OtherBranchesModel.BranchBean> listMarkers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_other_branches);

        setUpMapIfNeeded();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        setMapLocation();

        Gson gson = new Gson();
        storeDetailsModel = gson.fromJson(getIntent().getStringExtra("myjson"), OtherBranchesModel.class);

        binding.setOtherBranchesVM(new OtherBranchesViewModel(this, storeDetailsModel));

        initRecycler(storeDetailsModel.getProductBeans());
    }

    public void initRecycler(List<OtherBranchesModel.BranchBean> branchesList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        otherBranchesAdapter = new OtherBranchesAdapter(this, branchesList, this,binding.goToNext,binding.recylerOther,layoutManager);
        binding.recylerOther.setLayoutManager(layoutManager);
        binding.recylerOther.setAdapter(otherBranchesAdapter);
        Utilities.runAnimation(binding.recylerOther, 2);
    }

    public void showToast(List<OtherBranchesModel.BranchBean> branchesList) {
        Toast.makeText(this, "ddd", Toast.LENGTH_SHORT).show();
    }


    private void setUpMapIfNeeded() {
        if (mMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
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

    public Marker createMarker(String position, String latitude, String longitude, String storeName) {

        if (!latitude.equals("") || !longitude.equals("")) {
            Double lat = Double.parseDouble(latitude);
            Double lang = Double.parseDouble(longitude);

            return mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lang))
                    .anchor(0.5f, 0.5f)
                    .snippet(position)
                    .title(storeName)
                    .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(position))));
        } else return null;
    }

    public void AddMarkerForLoop(List<OtherBranchesModel.BranchBean> list) {
        listMarkers.addAll(list);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                try {
                    String lat = String.valueOf(list.get(i).getLat());
                    String lng = String.valueOf(list.get(i).getLng());
                    createMarker(i + "", lat, lng, list.get(i).getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Bitmap getMarkerBitmapFromView(String position) {
        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_view_home_marker, null);

        LinearLayout title_lin = customMarkerView.findViewById(R.id.title_lin);
        ImageView icon_img = customMarkerView.findViewById(R.id.icon_img2);

        TextView textView = customMarkerView.findViewById(R.id.distance_by_km);
        double distance = Utilities.getKilometers(GlobalVariables.LOCATION_LAT,
                GlobalVariables.LOCATION_LNG,
                listMarkers.get(Integer.parseInt(position)).getLat(),
                listMarkers.get(Integer.parseInt(position)).getLng());

        textView.setText(Utilities.roundPrice(distance) + " " + getResources().getString(R.string.km));

        //textView.setText("a");
        icon_img.setImageResource(R.drawable.haat_store_2);

        title_lin.setVisibility(View.GONE);

        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);

        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        AddMarkerForLoop(storeDetailsModel.getProductBeans());

        if (mMap != null) {
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            mMap.getUiSettings().setZoomControlsEnabled(false);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setZoomGesturesEnabled(true);
            mMap.getUiSettings().setRotateGesturesEnabled(true);
            mMap.getUiSettings().setMapToolbarEnabled(false);
        }

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(storeDetailsModel.getProductBeans().get(0).getLat(), storeDetailsModel.getProductBeans().get(0).getLng())).zoom(11).build();

        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                View markerItemView = getLayoutInflater().inflate(R.layout.marker_info_window, null);  // 1

                CircleImageView store_img = markerItemView.findViewById(R.id.store_img);

                String markerPos = marker.getId();
                String marPosInt = markerPos.substring(1);

                Picasso.get().load(storeDetailsModel.getProductBeans().get(Integer.parseInt(marPosInt)).getImg()).into(store_img);
                TextView store_name = markerItemView.findViewById(R.id.store_name);
                TextView store_address = markerItemView.findViewById(R.id.store_address);
                store_name.setText(storeDetailsModel.getProductBeans().get(Integer.parseInt(marPosInt)).getName());
                store_address.setText(storeDetailsModel.getProductBeans().get(Integer.parseInt(marPosInt)).getAddress());

                address = storeDetailsModel.getProductBeans().get(Integer.valueOf(marker.getSnippet())).getAddress();
                lat = storeDetailsModel.getProductBeans().get(Integer.valueOf(marker.getSnippet())).getLat();
                lng =  storeDetailsModel.getProductBeans().get(Integer.valueOf(marker.getSnippet())).getLng();


                return markerItemView;  // 4
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });


        mMap.setOnInfoWindowClickListener(marker -> {
            marker.hideInfoWindow();

            address = storeDetailsModel.getProductBeans().get(Integer.valueOf(marker.getSnippet())).getAddress();
            lat = storeDetailsModel.getProductBeans().get(Integer.valueOf(marker.getSnippet())).getLat();
            lng =  storeDetailsModel.getProductBeans().get(Integer.valueOf(marker.getSnippet())).getLng();


        });

        binding.goToNextMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("address", address);
                returnIntent.putExtra("lat", lat);
                returnIntent.putExtra("lng", lng);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("address", "none");
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void infoBottomSheet(int position) {

        Intent returnIntent = new Intent();
        returnIntent.putExtra("address", storeDetailsModel.getProductBeans().get(position).getAddress());
        returnIntent.putExtra("lat", storeDetailsModel.getProductBeans().get(position).getLat());
        returnIntent.putExtra("lng", storeDetailsModel.getProductBeans().get(position).getLng());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();

    }

    @Override
    public void branchClick(String newAddress, LatLng latLng) {

        Intent returnIntent = new Intent();
        returnIntent.putExtra("address", newAddress);
        returnIntent.putExtra("lat", latLng.latitude);
        returnIntent.putExtra("lng", latLng.longitude);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}