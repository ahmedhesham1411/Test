package com.uriallab.haat.haat.UI.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.uriallab.haat.haat.DataModels.ProductsModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.databinding.ItemProductsBinding;

import java.util.List;

/**
 * Created by Mahmoud on 4/19/2020.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MenuViewHolder> {

    private Activity activity;
    private List<ProductsModel.ResultEntity.ProductsEntity> incomingList;

    public ProductsAdapter(Activity activity, List<ProductsModel.ResultEntity.ProductsEntity> incomingList) {
        this.activity = activity;
        this.incomingList = incomingList;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemProductsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_products, parent, false);
        return new MenuViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MenuViewHolder holder, final int position) {
        Picasso.get().load(incomingList.get(position).getProduct_icon()).into(holder.binding.productImg);

        holder.binding.productName.setText(incomingList.get(position).getProduct_name());
        holder.binding.productDesc.setText(incomingList.get(position).getProduct_description());
        holder.binding.productPrice.setText(incomingList.get(position).getProduct_price()+"");
    }

    @Override
    public int getItemCount() {
        return incomingList.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        private ItemProductsBinding binding;

        private MenuViewHolder(ItemProductsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}