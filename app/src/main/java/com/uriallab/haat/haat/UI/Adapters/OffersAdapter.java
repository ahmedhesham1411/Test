package com.uriallab.haat.haat.UI.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.OffersModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.UI.Activities.ChatActivity;
import com.uriallab.haat.haat.Utilities.Dialogs;
import com.uriallab.haat.haat.Utilities.GPSTracker;
import com.uriallab.haat.haat.Utilities.GlobalVariables;
import com.uriallab.haat.haat.Utilities.IntentClass;
import com.uriallab.haat.haat.Utilities.LoadingDialog;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.databinding.ItemOffersBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Mahmoud on 4/19/2020.
 */

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OffersViewHolder> {

    private Activity activity;
    private List<OffersModel.ResultBean.OffersBean> incomingList;

    public OffersAdapter(Activity activity, List<OffersModel.ResultBean.OffersBean> incomingList) {
        this.activity = activity;
        this.incomingList = incomingList;
    }

    @Override
    public OffersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemOffersBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_offers, parent, false);
        return new OffersViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final OffersViewHolder holder, final int position) {

        if (incomingList.get(position).getOrdCoupon_Per() > 0) {
            holder.binding.priceAfterDiscount.setVisibility(View.VISIBLE);
            holder.binding.totalFee.setVisibility(View.GONE);
        } else {
            holder.binding.priceAfterDiscount.setVisibility(View.GONE);
            holder.binding.totalFee.setVisibility(View.VISIBLE);
        }


        Picasso.get().load(APIModel.BASE_URL + incomingList.get(position).getDriver_ImgUrl())
                .placeholder(R.drawable.store_blue).into(holder.binding.orderImg);

        if (ConfigurationFile.getCurrentLanguage(activity).equals("en"))
            holder.binding.starBar.setRotationY(180);

        holder.binding.driverName.setText(incomingList.get(position).getDriver_Full_Name());
        try {
            holder.binding.starBar.setRating(Integer.parseInt(String.valueOf(incomingList.get(position).getDriver_Rate())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.binding.ratesNumberVal.setText(incomingList.get(position).getDriver_Count_Rate() + "");
        if (ConfigurationFile.getCurrentLanguage(activity).equals("ar"))
            holder.binding.deliveryTimeVal.setText(incomingList.get(position).getDuration_Ar_Nm() + "");
        else
            holder.binding.deliveryTimeVal.setText(incomingList.get(position).getDuration_En_Nm() + "");
        holder.binding.deliveryFeeVal.setText(Utilities.round(incomingList.get(position).getFinal_Amt(), 2) + "  " + activity.getString(R.string.currency));

        try {

            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                GPSTracker gpsTracker = new GPSTracker(activity);

                double distance = Utilities.getKilometers(gpsTracker.getLocation().getLatitude(),
                        gpsTracker.getLocation().getLongitude(),
                        Double.parseDouble(incomingList.get(position).getDriver_Lat()),
                        Double.parseDouble(incomingList.get(position).getDriver_Long()));

                holder.binding.orderNumber.setText(Utilities.roundPrice(distance) + " " + activity.getString(R.string.km));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.binding.acceptOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptOrder(incomingList.get(position).getOrder_ID(),
                        incomingList.get(position).getDriver_ID(),
                        String.valueOf(incomingList.get(position).getOffer_Price()),
                        position);
            }
        });

        holder.binding.cancelOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelOrder(incomingList.get(position).getOrder_ID(),
                        incomingList.get(position).getDriver_ID(),
                        position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return incomingList.size();
    }

    public class OffersViewHolder extends RecyclerView.ViewHolder {

        private ItemOffersBinding binding;

        private OffersViewHolder(ItemOffersBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void removeItem(int pos) {
        incomingList.remove(pos);
        notifyDataSetChanged();
    }

    private void acceptOrder(final int orderId, final int driverID, final String price, final int pos) {
        final LoadingDialog loadingDialog = new LoadingDialog();

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("OrderUID", orderId);
            jsonParams.put("Ord_DriverID", driverID);
            jsonParams.put("Ord_Offer_Amt", price);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIModel.postMethod(activity, "Client/AcceptOrder", jsonParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    case 400:
                        try {
                            // Utilities.toastyError(activity, responseString + "    ");

                            JSONObject jsonObject = new JSONObject(responseString);
                            if (jsonObject.has("error"))
                                Utilities.toastyError(activity, jsonObject.getJSONObject("error").getString("Message"));
                            else
                                Utilities.toastyError(activity, responseString + "    ");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 500:
                        try {
                            JSONObject jsonObject = new JSONObject(responseString);
                            if (jsonObject.has("error"))
                                Utilities.toastyError(activity, jsonObject.getJSONObject("error").getString("Message"));
                            else
                                Utilities.toastyError(activity, responseString + "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                acceptOrder(orderId, driverID, price, pos);
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);
                Bundle bundle = new Bundle();
                bundle.putBoolean("isUser", true);
                bundle.putString("orderId", String.valueOf(orderId));
                IntentClass.goToActivity(activity, ChatActivity.class, bundle);
                GlobalVariables.FINISH_ACTIVITY = true;
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

    private void cancelOrder(final int orderId, final int driverID, final int pos) {
        final LoadingDialog loadingDialog = new LoadingDialog();

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("OrderUID", orderId);
            jsonParams.put("Ord_DriverID", driverID);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIModel.postMethod(activity, "Client/RejectOrder", jsonParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    case 400:
                        try {
                            // Utilities.toastyError(activity, responseString + "    ");

                            JSONObject jsonObject = new JSONObject(responseString);
                            if (jsonObject.has("error"))
                                Utilities.toastyError(activity, jsonObject.getJSONObject("error").getString("Message"));
                            else
                                Utilities.toastyError(activity, responseString + "    ");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 500:
                        try {
                            JSONObject jsonObject = new JSONObject(responseString);
                            if (jsonObject.has("error"))
                                Utilities.toastyError(activity, jsonObject.getJSONObject("error").getString("Message"));
                            else
                                Utilities.toastyError(activity, responseString + "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                cancelOrder(orderId, driverID, pos);
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                Utilities.toastySuccess(activity, activity.getString(R.string.canceled));
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