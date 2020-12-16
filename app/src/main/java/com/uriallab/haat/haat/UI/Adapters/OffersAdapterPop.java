package com.uriallab.haat.haat.UI.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.uriallab.haat.haat.DataModels.OffersModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.databinding.ItemOffersBinding;

import java.util.List;

public class OffersAdapterPop extends RecyclerView.Adapter<OffersAdapterPop.OffersViewHolder1> {

    private Activity activity;
    private List<OffersModel.ResultBean.OffersBean> incomingList;

    public OffersAdapterPop(Activity activity, List<OffersModel.ResultBean.OffersBean> incomingList) {
        this.activity = activity;
        this.incomingList = incomingList;
    }

    @NonNull
    @Override
    public OffersViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOffersBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_offers, parent, false);
        return new OffersViewHolder1(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersViewHolder1 holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class OffersViewHolder1 extends RecyclerView.ViewHolder {

        private ItemOffersBinding binding;

        private OffersViewHolder1(ItemOffersBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
