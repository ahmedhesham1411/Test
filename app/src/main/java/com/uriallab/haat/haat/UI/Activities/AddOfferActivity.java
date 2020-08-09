package com.uriallab.haat.haat.UI.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uriallab.haat.haat.DataModels.GoogleAddressModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.Utilities.GPSTracker;
import com.uriallab.haat.haat.Utilities.GlobalVariables;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.databinding.ActivityAddOfferBinding;
import com.uriallab.haat.haat.viewModels.AddOfferViewModel;

import java.lang.reflect.Type;

import cz.msebera.android.httpclient.Header;

public class AddOfferActivity extends AppCompatActivity implements OnMapReadyCallback {

    public ActivityAddOfferBinding binding;

    private AddOfferViewModel viewModel;

    private LatLng latLngTo;
    public GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;

    private LatLng storeLatLng, clientLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_offer);

        Bundle bundle = getIntent().getBundleExtra("data");

        GlobalVariables.FINISH_ACTIVITY_2 = false;

        storeLatLng = new LatLng(bundle.getDouble("lat"), bundle.getDouble("lng"));
        clientLatLng = new LatLng(bundle.getDouble("latClient"), bundle.getDouble("lngClient"));

        double appPercentage = bundle.getDouble("appPercentage");
        double tax = bundle.getDouble("tax");

        String clientId = bundle.getString("clientId");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            GPSTracker gpsTracker = new GPSTracker(this);

            double distanceFrom = Utilities.getKilometers(gpsTracker.getLocation().getLatitude(),
                    gpsTracker.getLocation().getLongitude(),
                    storeLatLng.latitude,
                    storeLatLng.longitude);

            double distanceTo = Utilities.getKilometers(gpsTracker.getLocation().getLatitude(),
                    gpsTracker.getLocation().getLongitude(),
                    bundle.getDouble("latClient"),
                    bundle.getDouble("lngClient"));

            binding.distanceFrom.setText(Utilities.roundPrice(distanceFrom) + " " + getString(R.string.km));
            binding.distanceTo.setText(Utilities.roundPrice(distanceTo) + " " + getString(R.string.km));
        }

        getAddressFromLatLng(this, binding.locationFrom, new LatLng(storeLatLng.latitude, storeLatLng.longitude));
        getAddressFromLatLng(this, binding.locationTo, new LatLng(bundle.getDouble("latClient"), bundle.getDouble("lngClient")));

        setUpMapIfNeeded();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        setMapLocation();

        viewModel = new AddOfferViewModel(this, bundle.getString("orderId"), clientId, appPercentage, tax, storeLatLng, clientLatLng);

        binding.setAddOfferVM(viewModel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (GlobalVariables.FINISH_ACTIVITY_2)
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

    private void getFusedLocation() {
        latLngTo = Utilities.getCurrentLocation(this);
        Log.e("Location", latLngTo.latitude + ", " + latLngTo.longitude);
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

        latLngTo = Utilities.getCurrentLocation(this);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLngTo).zoom(12).build();
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        Log.e("cameraMidLatLng", latLngTo.latitude + "\t" + latLngTo.longitude);
    }

    private void setUpMap() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        String myMapStyle = "[{\"featureType\":\"all\",\"elementType\":\"labels.text.fill\",\"stylers\":[{\"color\":\"#7c93a3\"},{\"lightness\":\"-10\"}]},{\"featureType\":\"administrative.country\",\"elementType\":\"geometry\",\"stylers\":[{\"visibility\":\"on\"}]},{\"featureType\":\"administrative.country\",\"elementType\":\"geometry.stroke\",\"stylers\":[{\"color\":\"#c2d1d6\"}]},{\"featureType\":\"landscape\",\"elementType\":\"geometry.fill\",\"stylers\":[{\"color\":\"#dde3e3\"}]},{\"featureType\":\"road.highway\",\"elementType\":\"geometry.fill\",\"stylers\":[{\"color\":\"#c2d1d6\"}]},{\"featureType\":\"road.highway\",\"elementType\":\"geometry.stroke\",\"stylers\":[{\"color\":\"#a9b4b8\"},{\"lightness\":\"0\"}]},{\"featureType\":\"water\",\"elementType\":\"geometry.fill\",\"stylers\":[{\"color\":\"#a3c7df\"}]}]";
        MapStyleOptions style = new MapStyleOptions(myMapStyle);
        mMap.setMapStyle(style);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        getFusedLocation();
    }

    public Bitmap getMarkerBitmapFromView(boolean isHome) {

        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_view_home_marker, null);

        LinearLayout title_lin = customMarkerView.findViewById(R.id.title_lin);
        ImageView icon_img = customMarkerView.findViewById(R.id.icon_img2);

        title_lin.setVisibility(View.GONE);
        if (isHome)
            icon_img.setImageResource(R.drawable.haat_home_2);
        else
            icon_img.setImageResource(R.drawable.haat_store_2);

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

    private void getAddressFromLatLng(final Activity activity, final TextView textView, final LatLng latLng) {
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(GlobalVariables.GET_ADDRESSES_FROM_LATLNG_URL + latLng.latitude + "," + latLng.longitude + "&key=AIzaSyAmD_A7N-SI2JbkhGh4xY_OFip7GtQRZfg&language=" + ConfigurationFile.getCurrentLanguage(activity), new TextHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("onFailure", responseString + "");

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("onSuccess", responseString);
                Type dataType = new TypeToken<GoogleAddressModel>() {
                }.getType();
                GoogleAddressModel data = new Gson().fromJson(responseString, dataType);
                if (data.status.equals("OK")) {
                    textView.setText(data.results.get(0).formatted_address);
                } else {
                    Log.e("address text :: ", data.status);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }
}