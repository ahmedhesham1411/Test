package com.uriallab.haat.haat.UI.Adapters;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.uriallab.haat.haat.DataModels.GoogleStoresModel;
import com.uriallab.haat.haat.DataModels.ServerStoresModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Activities.makeOrder.StoreDetailsActivity;
import com.uriallab.haat.haat.Utilities.IntentClass;
import com.uriallab.haat.haat.databinding.ItemFamousPlacesBinding;

import java.util.List;

/**
 * Created by Mahmoud on 4/19/2020.
 */

public class FamousPlacesAdapter extends RecyclerView.Adapter<FamousPlacesAdapter.StoresViewHolder> {

    private Activity activity;
    private List<ServerStoresModel.ResultEntity> incomingList;

    public FamousPlacesAdapter(Activity activity, List<ServerStoresModel.ResultEntity> incomingList) {
        this.activity = activity;
        this.incomingList = incomingList;
    }

    @Override
    public StoresViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemFamousPlacesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_famous_places, parent, false);
        return new StoresViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final StoresViewHolder holder, final int position) {

        Picasso.get().load(incomingList.get(position).getIcon()).placeholder(R.drawable.logo).into(holder.binding.famousImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("placeId", incomingList.get(position).getPlace_id());
                bundle.putBoolean("isFromServer", true);
                IntentClass.goToActivity(activity, StoreDetailsActivity.class, bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return incomingList.size();
    }

    public class StoresViewHolder extends RecyclerView.ViewHolder {

        private ItemFamousPlacesBinding binding;

        private StoresViewHolder(ItemFamousPlacesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}