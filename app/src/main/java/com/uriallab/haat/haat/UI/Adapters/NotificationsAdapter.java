package com.uriallab.haat.haat.UI.Adapters;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uriallab.haat.haat.DataModels.NotificationsModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.LoginSession;
import com.uriallab.haat.haat.UI.Activities.AllOffersActivity;
import com.uriallab.haat.haat.UI.Activities.ChatActivity;
import com.uriallab.haat.haat.UI.Activities.ChatDriverActivity;
import com.uriallab.haat.haat.UI.Activities.DriverNewOrderActivity;
import com.uriallab.haat.haat.Utilities.IntentClass;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.databinding.ItemNotificationsBinding;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Mahmoud on 4/19/2020.
 */

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder> {

    private Activity activity;
    private List<NotificationsModel.ResultBean.NotficationsBean> incomingList;

    public NotificationsAdapter(Activity activity, List<NotificationsModel.ResultBean.NotficationsBean> incomingList) {
        this.activity = activity;
        this.incomingList = incomingList;
    }

    @Override
    public NotificationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemNotificationsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_notifications, parent, false);
        return new NotificationsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final NotificationsViewHolder holder, final int position) {

        holder.binding.notificationText.setText(incomingList.get(position).getNotic_Mssge());

        String timeTxt = incomingList.get(position).getNotic_Date() + " " + incomingList.get(position).getNotic_Time();

        holder.binding.notificationTime.setText(Utilities.getTimeAgo(activity, timeTxt));

       // Log.e("time_notification", timeTxt+"\t"+Utilities.getTimeAgo(activity, timeTxt));

        holder.itemView.setOnClickListener(view -> {
            try {
                if (incomingList.get(position).getNotic_Type().equals("1")) {
                    if (LoginSession.getUserData(activity).getResult().getUserData().isIsDelivery()) {
                        Bundle bundle = new Bundle();
                        bundle.putString("orderId", incomingList.get(position).getOrderID());
                        IntentClass.goToActivity(activity, DriverNewOrderActivity.class, bundle);
                    }
                } else if (incomingList.get(position).getNotic_Type().equals("2")) {
                    if (LoginSession.getUserData(activity).getResult().getUserData().isIsDelivery()) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("orderId", incomingList.get(position).getOrderID());
                        IntentClass.goToActivity(activity, ChatDriverActivity.class, bundle1);
                    }
                } else if (incomingList.get(position).getNotic_Type().equals("5")) {
                    if (!LoginSession.getUserData(activity).getResult().getUserData().isIsDelivery()) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putBoolean("isNew", false);
                        bundle1.putString("orderId", incomingList.get(position).getOrderID());
                        IntentClass.goToActivity(activity, AllOffersActivity.class, bundle1);
                    }
                } else if (incomingList.get(position).getNotic_Type().equals("6") || incomingList.get(position).getNotic_Type().equals("7")) {
                    if (LoginSession.getUserData(activity).getResult().getUserData().isIsDelivery()) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("orderId", incomingList.get(position).getOrderID());
                        IntentClass.goToActivity(activity, ChatActivity.class, bundle1);
                    }
                } else if (incomingList.get(position).getNotic_Type().equals("8")) {
                    if (LoginSession.getUserData(activity).getResult().getUserData().isIsDelivery()) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("orderId", incomingList.get(position).getOrderID());
                        IntentClass.goToActivity(activity, ChatDriverActivity.class, bundle1);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return incomingList.size();
    }

    public class NotificationsViewHolder extends RecyclerView.ViewHolder {

        private ItemNotificationsBinding binding;

        private NotificationsViewHolder(ItemNotificationsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}