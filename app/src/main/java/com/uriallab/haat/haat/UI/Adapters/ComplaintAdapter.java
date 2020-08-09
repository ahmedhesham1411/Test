package com.uriallab.haat.haat.UI.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.uriallab.haat.haat.DataModels.ComplaintModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.UI.Activities.utilsAndAccountInfo.ComplaintDetailsActivity;
import com.uriallab.haat.haat.databinding.ItemComplaintBinding;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Mahmoud on 4/19/2020.
 */

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.StoresViewHolder> {

    private Activity activity;
    private List<ComplaintModel.ResultBean.ComplaintsBean> incomingList;

    public ComplaintAdapter(Activity activity, List<ComplaintModel.ResultBean.ComplaintsBean> incomingList) {
        this.activity = activity;
        this.incomingList = incomingList;
    }

    @Override
    public StoresViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemComplaintBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_complaint, parent, false);
        return new StoresViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final StoresViewHolder holder, final int position) {

        Picasso.get().load(incomingList.get(position).getOrd_Shop_ImgUrl()).into(holder.binding.orderImg);

        holder.binding.storeName.setText(incomingList.get(position).getOrd_Shop_Nm()+"");
        holder.binding.complaintDetails.setText(incomingList.get(position).getComplaint_Desc()+"");
        holder.binding.complaintNumer.setText(incomingList.get(position).getComplaintUID()+"");

        if (incomingList.get(position).getComplaint_Status_ID() == 1) {
            holder.binding.complaintStatus.setText(activity.getString(R.string.waiting_processed));
            holder.binding.complaintStatus.setTextColor(activity.getResources().getColor(R.color.colorMoov));
        } else {
            holder.binding.complaintStatus.setText(activity.getString(R.string.proessed));
            holder.binding.complaintStatus.setTextColor(activity.getResources().getColor(R.color.colorBlue));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ComplaintDetailsActivity.class);
                Gson gson = new Gson();
                String myJson = gson.toJson(incomingList.get(position));
                intent.putExtra("myjson", myJson);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return incomingList.size();
    }

    public class StoresViewHolder extends RecyclerView.ViewHolder {

        private ItemComplaintBinding binding;

        private StoresViewHolder(ItemComplaintBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}