package com.uriallab.haat.haat.UI.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uriallab.haat.haat.DataModels.GoogleAddressModel;
import com.uriallab.haat.haat.DataModels.GoogleStoresModel;
import com.uriallab.haat.haat.DataModels.OtherBranchesModel;
import com.uriallab.haat.haat.Interfaces.BranchClick;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.Utilities.GPSTracker;
import com.uriallab.haat.haat.Utilities.GlobalVariables;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.databinding.ItemOtherBranchesBinding;

import java.lang.reflect.Type;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Mahmoud on 4/19/2020.
 */

public class OtherBranchesAdapter extends RecyclerView.Adapter<OtherBranchesAdapter.StoresViewHolder> {

    private Activity activity;
    private List<OtherBranchesModel.BranchBean> incomingList;

    private BranchClick branchClick;

    public OtherBranchesAdapter(Activity activity, List<OtherBranchesModel.BranchBean> incomingList, BranchClick branchClick) {
        this.activity = activity;
        this.incomingList = incomingList;
        this.branchClick = branchClick;
    }

    @Override
    public StoresViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemOtherBranchesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_other_branches, parent, false);
        return new StoresViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final StoresViewHolder holder, final int position) {

        holder.binding.storeName.setText(incomingList.get(position).getName());

        holder.binding.storeAddress.setText(incomingList.get(position).getAddress());

//        getAddressFromLatLng(activity, holder.binding.storeAddress, new LatLng(
//                incomingList.get(position).getLat(),
//                incomingList.get(position).getLng()));

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            GPSTracker gpsTracker = new GPSTracker(activity);

            double distance = Utilities.getKilometers(gpsTracker.getLocation().getLatitude(),
                    gpsTracker.getLocation().getLongitude(),
                    incomingList.get(position).getLat(),
                    incomingList.get(position).getLng());

            holder.binding.storeDistance.setText(Utilities.roundPrice(distance) + "");
        }

        holder.itemView.setOnClickListener(view -> branchClick.branchClick(holder.binding.storeAddress.getText().toString(),
                new LatLng(incomingList.get(position).getLat(),
                        incomingList.get(position).getLng())));

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

    @Override
    public int getItemCount() {
        return incomingList.size();
    }

    public class StoresViewHolder extends RecyclerView.ViewHolder {

        private ItemOtherBranchesBinding binding;

        private StoresViewHolder(ItemOtherBranchesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}