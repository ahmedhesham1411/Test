package com.uriallab.haat.haat.UI.Adapters;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.uriallab.haat.haat.DataModels.StoreProductsModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.databinding.ItemStoreProductBinding;

import java.util.List;

/**
 * Created by Mahmoud on 4/19/2020.
 */

public class StoreProductsAdapter extends RecyclerView.Adapter<StoreProductsAdapter.MenuViewHolder> {

    private Activity activity;
    private List<StoreProductsModel.ProductBean> incomingList;

    public StoreProductsAdapter(Activity activity, List<StoreProductsModel.ProductBean> incomingList) {
        this.activity = activity;
        this.incomingList = incomingList;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemStoreProductBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_store_product, parent, false);
        return new MenuViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MenuViewHolder holder, final int position) {

        holder.binding.name.setText(incomingList.get(position).getName());
        holder.binding.quantity.setText(incomingList.get(position).getQuantity()+"");
        holder.binding.productPrice.setText(incomingList.get(position).getPrice() + "");

    }

    @Override
    public int getItemCount() {
        return incomingList.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        private ItemStoreProductBinding binding;

        private MenuViewHolder(ItemStoreProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}