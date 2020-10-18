package com.uriallab.haat.haat.viewModels;

import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableInt;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.OverviewPointModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.UI.Activities.TrackDriverActivity;
import com.uriallab.haat.haat.Utilities.GlobalVariables;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TrackDriverViewModel {

    public ObservableInt rotate = new ObservableInt(0);

    private TrackDriverActivity activity;

    private LatLng currentLatLng;

    public TrackDriverViewModel(TrackDriverActivity activity) {
        this.activity = activity;

        if (ConfigurationFile.getCurrentLanguage(activity).equals("ar"))
            rotate.set(180);
    }

    public void drawDirection() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // final LoadingDialog loadingDialog = new LoadingDialog();

            currentLatLng = new LatLng(GlobalVariables.LOCATION_LAT, GlobalVariables.LOCATION_LNG);

            String url = "/directions/json?origin=" +
                    activity.storeLatLng.latitude + "," + activity.storeLatLng.longitude +
                    "&destination=" + activity.clientLatLng.latitude + "," + activity.clientLatLng.longitude +
                    "&sensor=true&key=" +
                    activity.getResources().getString(R.string.api_key);

            APIModel.getMethodForGoogle2(activity, url, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.e("response", responseString + "Error");
                    switch (statusCode) {
                        default:
                            APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                                @Override
                                public void onRefresh() {
                                    drawDirection();
                                }
                            });
                            break;
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Log.e("response", responseString);
                    Type dataType = new TypeToken<OverviewPointModel>() {
                    }.getType();
                    OverviewPointModel data = new Gson().fromJson(responseString, dataType);

                    try {
                        drawDirectionToStop(data.getRoutes().get(0).getOverview_polyline().getPoints());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStart() {
                    super.onStart();
                    //   Dialogs.showLoading(activity, loadingDialog);
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    //  Dialogs.dismissLoading(loadingDialog);
                }
            });
        }
    }

    public void drawDirection2(LatLng latLngStart, LatLng latLngEnd) {
        String url = "/directions/json?origin=" +
                latLngStart.latitude + "," + latLngStart.longitude +
                "&destination=" + latLngEnd.latitude + "," + latLngEnd.longitude +
                "&sensor=true&key=" +
                activity.getResources().getString(R.string.api_key) +
                "&language=" + ConfigurationFile.getCurrentLanguage(activity);

        APIModel.getMethodForGoogle2(activity, url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                drawDirection();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("response", responseString);
                Type dataType = new TypeToken<OverviewPointModel>() {
                }.getType();
                OverviewPointModel data = new Gson().fromJson(responseString, dataType);

                try {
                    activity.setAnimation(decodeOverviewPolyLinePonts(data.getRoutes().get(0).getOverview_polyline().getPoints()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }

    private void drawDirectionToStop(String overviewPolyline) {
        if (overviewPolyline != null) {
            activity.mMap.clear();
            List<LatLng> polyz = decodeOverviewPolyLinePonts(overviewPolyline);
            if (polyz != null) {
                PolylineOptions lineOptions = new PolylineOptions();
                lineOptions.addAll(polyz);
                lineOptions.width(5);
                lineOptions.color(ContextCompat.getColor(activity, R.color.colorMoov));
                activity.mMap.addPolyline(lineOptions);
            }
        }

        activity.mMap.addMarker(new MarkerOptions()
                .position(new LatLng(activity.clientLatLng.latitude, activity.clientLatLng.longitude))
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromBitmap(activity.getMarkerBitmapFromView(true))));

        activity.mMap.addMarker(new MarkerOptions()
                .position(new LatLng(activity.storeLatLng.latitude, activity.storeLatLng.longitude))
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromBitmap(activity.getMarkerBitmapFromView(false))));

//        if (activity.marker != null)
//            activity.marker.remove();
//
//        activity.marker = activity.mMap.addMarker(new MarkerOptions()
//                .position(new LatLng(activity.driverLatLng.latitude, activity.driverLatLng.longitude))
//                .anchor(0.5f, 0.5f)
//                .icon(BitmapDescriptorFactory.fromBitmap(activity.getMarkerBitmapFromView())));
    }

    //This function is to parse the value of "points"
    public List<LatLng> decodeOverviewPolyLinePonts(String encoded) {
        List<LatLng> poly = new ArrayList<LatLng>();
        if (encoded != null && !encoded.isEmpty() && encoded.trim().length() > 0) {
            int index = 0, len = encoded.length();
            int lat = 0, lng = 0;

            while (index < len) {
                int b, shift = 0, result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                LatLng p = new LatLng((((double) lat / 1E5)),
                        (((double) lng / 1E5)));
                poly.add(p);
            }
        }
        return poly;
    }

    public void back() {
        activity.onBackPressed();
    }

}