package com.uriallab.haat.haat.UI.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.FavModel;
import com.uriallab.haat.haat.Interfaces.FavClick;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.Utilities.Dialogs;
import com.uriallab.haat.haat.Utilities.LoadingDialog;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.databinding.ItemFavouritesBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Mahmoud on 4/19/2020.
 */

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder> {

    private Activity activity;
    private List<FavModel.ResultBean.FavoritelocationsBean> incomingList;
    private FavClick favClick;

    public FavouritesAdapter(Activity activity, List<FavModel.ResultBean.FavoritelocationsBean> incomingList, FavClick favClick) {
        this.activity = activity;
        this.incomingList = incomingList;
        this.favClick = favClick;
    }

    @Override
    public FavouritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemFavouritesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_favourites, parent, false);
        return new FavouritesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final FavouritesViewHolder holder, final int position) {

        try {
            holder.binding.addressTxt.setText(incomingList.get(position).getLocation_Nm());
            holder.binding.addressTxtAdd.setText(incomingList.get(position).getFavorite_Nm());
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.binding.deleteFav.setImageResource(R.drawable.rubish);
        holder.binding.deleteFav.setColorFilter(activity.getResources().getColor(R.color.colorMoov), PorterDuff.Mode.SRC_ATOP);

        holder.binding.clickLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    favClick.favClick(incomingList.get(position).getLocation_Nm(),
                            new LatLng(Double.parseDouble(incomingList.get(position).getLocation_Lat()),
                                    Double.parseDouble(incomingList.get(position).getLocation_Lng())));
                } catch (Exception e) {
                    Utilities.toastyError(activity, activity.getString(R.string.error_on_location));
                    e.printStackTrace();
                }
            }
        });

        holder.binding.deleteFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDeleteFav(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return incomingList.size();
    }

    public class FavouritesViewHolder extends RecyclerView.ViewHolder {

        private ItemFavouritesBinding binding;

        private FavouritesViewHolder(ItemFavouritesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void removeItem(int pos) {
        incomingList.remove(pos);
        notifyDataSetChanged();
    }

    private void dialogDeleteFav(final int pos) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_alert_delete_fav);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView acept = dialog.findViewById(R.id.acept);
        TextView cancel = dialog.findViewById(R.id.cancel);

        acept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delFav(incomingList.get(pos).getLocationUID(), pos);
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void delFav(final int favId, final int pos) {
        final LoadingDialog loadingDialog = new LoadingDialog();
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("LocationUID", favId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        APIModel.postMethod(activity, "Client/DeleteFavoriteLocations", jsonParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                delFav(favId, pos);
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                Utilities.toastySuccess(activity, activity.getString(R.string.deleted_successfully));

                removeItem(pos);
            }

            @Override
            public void onStart() {
                super.onStart();
                Dialogs.showLoading(activity, loadingDialog);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                Dialogs.dismissLoading(loadingDialog);
            }
        });
    }
}