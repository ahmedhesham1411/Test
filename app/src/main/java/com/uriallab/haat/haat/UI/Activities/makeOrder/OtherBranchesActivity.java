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
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.uriallab.haat.haat.DataModels.GoogleAddressModel;
import com.uriallab.haat.haat.DataModels.GoogleStoresModel;
import com.uriallab.haat.haat.Interfaces.BranchClick;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.UI.Adapters.OtherBranchesAdapter;
import com.uriallab.haat.haat.Utilities.GlobalVariables;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.databinding.ActivityOtherBranchesBinding;
import com.uriallab.haat.haat.viewModels.OtherBranchesViewModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class OtherBranchesActivity extends AppCompatActivity implements OnMapReadyCallback, BranchClick {

    private ActivityOtherBranchesBinding binding;

    private GoogleStoresModel storeDetailsModel;

    private GoogleApiClient mGoogleApiClient;

    private GoogleMap mMap;

    private String address = "none";

    private String storeAddress = "";

    List<GoogleStoresModel.ResultsBean> listMarkers = new ArrayList<>();

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
        storeDetailsModel = gson.fromJson(getIntent().getStringExtra("myjson"), GoogleStoresModel.class);

        binding.setOtherBranchesVM(new OtherBranchesViewModel(this, storeDetailsModel));

        initRecycler(storeDetailsModel.getResults());
    }

    public void initRecycler(List<GoogleStoresModel.ResultsBean> branchesList) {
        OtherBranchesAdapter otherBranchesAdapter = new OtherBranchesAdapter(this, branchesList, this);
        binding.recylerOther.setLayoutManager(new LinearLayoutManager(this));
        binding.recylerOther.setAdapter(otherBranchesAdapter);
        Utilities.runAnimation(binding.recylerOther, 2);
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
                    .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView())));
        } else return null;
    }

    public void AddMarkerForLoop(List<GoogleStoresModel.ResultsBean> list) {
        listMarkers.addAll(list);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                try {
                    String lat = String.valueOf(list.get(i).getGeometry().getLocation().getLat());
                    String lng = String.valueOf(list.get(i).getGeometry().getLocation().getLng());
                    createMarker(i + "", lat, lng, list.get(i).getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Bitmap getMarkerBitmapFromView() {
        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_view_home_marker, null);

        LinearLayout title_lin = customMarkerView.findViewById(R.id.title_lin);
        ImageView icon_img = customMarkerView.findViewById(R.id.icon_img2);

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

        AddMarkerForLoop(storeDetailsModel.getResults());

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
                .target(new LatLng(storeDetailsModel.getResults().get(0).getGeometry().getLocation().getLat(), storeDetailsModel.getResults().get(0).getGeometry().getLocation().getLng())).zoom(11).build();

        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                View markerItemView = getLayoutInflater().inflate(R.layout.marker_info_window, null);  // 1

                CircleImageView store_img = markerItemView.findViewById(R.id.store_img);

                String markerPos = marker.getId();

                String marPosInt = markerPos.substring(1);

                Log.e("POSITION_MARKER", marPosInt+"" );

                String photoUrl;
                try {
                    photoUrl = "https://maps.googleapis.com/maps/api/place/photo?photoreference=" +
                            storeDetailsModel.getResults().get(Integer.parseInt(marPosInt)).getPhotos().get(0).getPhoto_reference()
                            + "&maxheight=400&maxwidth=400&key=AIzaSyAmD_A7N-SI2JbkhGh4xY_OFip7GtQRZfg";
                } catch (Exception e) {
                    photoUrl = storeDetailsModel.getResults().get(Integer.parseInt(marPosInt)).getIcon();
                    e.printStackTrace();
                }

                Picasso.get().load(photoUrl).into(store_img);

                TextView store_name = markerItemView.findViewById(R.id.store_name);
                TextView store_address = markerItemView.findViewById(R.id.store_address);
                store_name.setText(storeDetailsModel.getResults().get(Integer.parseInt(marPosInt)).getName());
                getAddressFromLatLng2(OtherBranchesActivity.this,
                        new LatLng(
                                storeDetailsModel.getResults().get(Integer.parseInt(marPosInt)).getGeometry().getLocation().getLat(),
                                storeDetailsModel.getResults().get(Integer.parseInt(marPosInt)).getGeometry().getLocation().getLng()
                        ));
                store_address.setText(storeAddress);
                return markerItemView;  // 4
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.hideInfoWindow();

                infoBottomSheet(Integer.valueOf(marker.getSnippet()));
                // handle the clicked marker object
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

        getAddressFromLatLng(this, new LatLng(storeDetailsModel.getResults().get(position).getGeometry().getLocation().getLat(),
                storeDetailsModel.getResults().get(position).getGeometry().getLocation().getLng()));
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

    private void getAddressFromLatLng2(final Activity activity, final LatLng latLng) {
        try {

            Locale aLocale;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (ConfigurationFile.getCurrentLanguage(activity).equals("ar"))
                    aLocale = new Locale.Builder().setLanguage("ar").build();
                else
                    aLocale = new Locale.Builder().setLanguage("en").build();
            } else {
                if (ConfigurationFile.getCurrentLanguage(activity).equals("ar"))
                    aLocale = new Locale("ar");
                else
                    aLocale = new Locale("en");
            }

            Geocoder geo = new Geocoder(activity, aLocale);
            List<Address> addresses = geo.getFromLocation(latLng.latitude, latLng.longitude, 2);
            if (addresses.isEmpty()) {
                //textView.setText("Waiting for Location");
            } else {
                if (addresses.size() > 0) {
                    storeAddress = addresses.get(0).getAddressLine(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }
    }

    private void getAddressFromLatLng(final Activity activity, final LatLng latLng) {

        try {

            Locale aLocale;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (ConfigurationFile.getCurrentLanguage(activity).equals("ar"))
                    aLocale = new Locale.Builder().setLanguage("ar").build();
                else
                    aLocale = new Locale.Builder().setLanguage("en").build();
            } else {
                if (ConfigurationFile.getCurrentLanguage(activity).equals("ar"))
                    aLocale = new Locale("ar");
                else
                    aLocale = new Locale("en");
            }

            Geocoder geo = new Geocoder(activity, aLocale);
            List<Address> addresses = geo.getFromLocation(latLng.latitude, latLng.longitude, 2);
            if (addresses.isEmpty()) {
            } else {
                if (addresses.size() > 0) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("address", addresses.get(0).getAddressLine(0));
                    returnIntent.putExtra("lat", latLng.latitude);
                    returnIntent.putExtra("lng", latLng.longitude);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }
    }
}