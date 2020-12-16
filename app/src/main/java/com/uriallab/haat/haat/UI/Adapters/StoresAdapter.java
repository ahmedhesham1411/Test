package com.uriallab.haat.haat.UI.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.budiyev.android.imageloader.ImageLoader;
import com.budiyev.android.imageloader.LoadCallback;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;
import com.uriallab.haat.haat.DataModels.GoogleStoresModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.UI.Activities.makeOrder.StoreDetailsActivity;
import com.uriallab.haat.haat.Utilities.GlobalVariables;
import com.uriallab.haat.haat.Utilities.IntentClass;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.databinding.ItemStoreBinding;

import java.util.List;
import java.util.Locale;

/**
 * Created by Mahmoud on 4/19/2020.
 */

public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.StoresViewHolder> {

    private Activity activity;
    private List<GoogleStoresModel.ResultsBean> incomingList;
    String  layout_name;
    private String photoUrl;

    public StoresAdapter(Activity activity, List<GoogleStoresModel.ResultsBean> incomingList,String layout_name) {
        this.activity = activity;
        this.incomingList = incomingList;
        this.layout_name = layout_name;
    }

    @Override
    public StoresViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemStoreBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_store, parent, false);
        return new StoresViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final StoresViewHolder holder, final int position) {


        if (layout_name.equals("ver")){
            holder.binding.layoutVertical.setVisibility(View.VISIBLE);
            holder.binding.layoutHorizontal.setVisibility(View.GONE);
        }else {
            holder.binding.layoutVertical.setVisibility(View.GONE);
            holder.binding.layoutHorizontal.setVisibility(View.VISIBLE);
        }
        holder.binding.storeName.setText(incomingList.get(position).getName());
        holder.binding.storeNameHori.setText(incomingList.get(position).getName());

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            double distance = Utilities.getKilometers(GlobalVariables.LOCATION_LAT,
                    GlobalVariables.LOCATION_LNG,
                    incomingList.get(position).getGeometry().getLocation().getLat(),
                    incomingList.get(position).getGeometry().getLocation().getLng());

            holder.binding.storeDistance.setText(Utilities.roundPrice(distance) + "");
            holder.binding.storeDistanceHori.setText(Utilities.roundPrice(distance) + "");

        }

        getAddressFromLatLng(activity, holder.binding.storeLocation, new LatLng(
                incomingList.get(position).getGeometry().getLocation().getLat(),
                incomingList.get(position).getGeometry().getLocation().getLng()));

        getAddressFromLatLng(activity, holder.binding.storeLocationHori, new LatLng(
                incomingList.get(position).getGeometry().getLocation().getLat(),
                incomingList.get(position).getGeometry().getLocation().getLng()));


     /*   try {
            photoUrl = "https://maps.googleapis.com/maps/api/place/photo?photoreference=" +
                    incomingList.get(position).getPhotos().get(0).getPhoto_reference()
                    + "&maxheight=400&maxwidth=400&key=AIzaSyAmD_A7N-SI2JbkhGh4xY_OFip7GtQRZfg";

            Picasso.get().load(photoUrl).fit().centerCrop().into(holder.binding.storeImg);
        } catch (Exception e) {
            photoUrl = incomingList.get(position).getIcon();
            e.printStackTrace();
        }

        //Picasso.get().load(photoUrl).fit().centerCrop().into(holder.binding.storeImg);
        Glide.with(activity)
                .load(photoUrl)
                .override(400, 400)
                .into(holder.binding.storeImg);
       Glide.with(activity)
                .load(photoUrl)
                .override(400, 400)
                .into(holder.binding.storeImgHori);*/


/*        ImageLoader.with(activity).from(photoUrl).onLoaded(new LoadCallback() {
            @Override
            public void onLoaded(@NonNull Bitmap image) {
                // Do something with image here
                //imageView.setImageBitmap(image);
            }
        }).load(holder.binding.storeImgHori)*/;


        //Picasso.get().load(photoUrl).fit().into(holder.binding.storeImgHori);

        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("placeId", incomingList.get(position).getPlace_id());
            bundle.putBoolean("isFromServer", false);
            IntentClass.goToActivity(activity, StoreDetailsActivity.class, bundle);
        });

        holder.binding.layoutHorizontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("placeId", incomingList.get(position).getPlace_id());
                bundle.putBoolean("isFromServer", false);
                IntentClass.goToActivity(activity, StoreDetailsActivity.class, bundle);
            }
        });

        holder.binding.layoutVertical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("placeId", incomingList.get(position).getPlace_id());
                bundle.putBoolean("isFromServer", false);
                IntentClass.goToActivity(activity, StoreDetailsActivity.class, bundle);
            }
        });
    }

    private void getAddressFromLatLng(final Activity activity, final TextView textView, final LatLng latLng) {
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

    @Override
    public int getItemCount() {
        return incomingList.size();
    }

    public class StoresViewHolder extends RecyclerView.ViewHolder {

        private ItemStoreBinding binding;

        private StoresViewHolder(ItemStoreBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}