package com.uriallab.haat.haat.UI.Adapters;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.OrdersModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.databinding.ItemOrdersBinding;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Mahmoud on 4/19/2020.
 */

public class FinishedOrderAdapter extends RecyclerView.Adapter<FinishedOrderAdapter.FinishedOrdersViewHolder> {

    private Activity activity;
    private List<OrdersModel.ResultBean.OrdersBean> incomingList;

    public FinishedOrderAdapter(Activity activity, List<OrdersModel.ResultBean.OrdersBean> incomingList) {
        this.activity = activity;
        this.incomingList = incomingList;
    }

    @Override
    public FinishedOrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemOrdersBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_orders, parent, false);
        return new FinishedOrdersViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final FinishedOrdersViewHolder holder, final int position) {

        try {
            Picasso.get().load(incomingList.get(position).getOrd_Shop_ImgUrl())
                    .placeholder(R.drawable.store_blue).into(holder.binding.orderImg);
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.binding.orderStateLin.setVisibility(View.GONE);
        holder.binding.orderFinishedStateImg.setVisibility(View.VISIBLE);

        holder.binding.orderNumber.setText(String.valueOf(incomingList.get(position).getOrderUID()));
        holder.binding.storeNameTxt.setText(incomingList.get(position).getOrd_Shop_Nm());

        if (incomingList.get(position).getOrd_Client_StatusID() == 4) {
            holder.binding.orderFinishedStateImg.setImageResource(R.drawable.done);
            holder.binding.orderFinishedStateImg.setColorFilter(activity.getResources().getColor(R.color.colorGreen), PorterDuff.Mode.SRC_ATOP);

            holder.binding.orderDetilsTxt.setTextColor(activity.getResources().getColor(R.color.colorGreen));
            holder.binding.orderDetilsTxt.setText(activity.getString(R.string.done));
        } else {
            holder.binding.orderFinishedStateImg.setImageResource(R.drawable.canceled);
            holder.binding.orderFinishedStateImg.setColorFilter(activity.getResources().getColor(R.color.colorRed), PorterDuff.Mode.SRC_ATOP);

            holder.binding.orderDetilsTxt.setTextColor(activity.getResources().getColor(R.color.colorRed));
            holder.binding.orderDetilsTxt.setText(activity.getString(R.string.cancelled));
        }

    }

    @Override
    public int getItemCount() {
        return incomingList.size();
    }

    public class FinishedOrdersViewHolder extends RecyclerView.ViewHolder {

        private ItemOrdersBinding binding;

        private FinishedOrdersViewHolder(ItemOrdersBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}