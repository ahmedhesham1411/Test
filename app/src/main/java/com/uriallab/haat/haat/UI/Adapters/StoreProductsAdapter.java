package com.uriallab.haat.haat.UI.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
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
    private ObservableField<String> selectedProducts;

    public StoreProductsAdapter(Activity activity, List<StoreProductsModel.ProductBean> incomingList, ObservableField<String> selectedProducts) {
        this.activity = activity;
        this.incomingList = incomingList;
        this.selectedProducts = selectedProducts;
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
        holder.binding.quantity.setText(incomingList.get(position).getQuantity() + "");
        holder.binding.productPrice.setText(( incomingList.get(position).getPrice())+ "");

        holder.binding.increaseQuant.setOnClickListener(v -> {
            int quant = incomingList.get(position).getQuantity() + 1;
            incomingList.get(position).setQuantity(quant);
            holder.binding.quantity.setText(quant + "");
            holder.binding.productPrice.setText(( incomingList.get(position).getPrice() * quant ) + "");

            String temp = activity.getString(R.string.orders_)+"\n";
            for (int i = 0; i < incomingList.size(); i++) {
                temp += ( incomingList.get(i).getName() + " " +
                        activity.getString(R.string.quantity) + " : " + incomingList.get(i).getQuantity()) + "\n";
            }
            selectedProducts.set(temp);
        });

        holder.binding.deleteFrame.setOnClickListener(v -> {
            if (incomingList.get(position).getQuantity() > 1) {
                int quant = incomingList.get(position).getQuantity() - 1;
                incomingList.get(position).setQuantity(quant);
                holder.binding.quantity.setText(quant + "");
                holder.binding.productPrice.setText(( incomingList.get(position).getPrice() * quant ) + "");

                String temp = activity.getString(R.string.orders_)+"\n";
                for (int i = 0; i < incomingList.size(); i++) {
                    temp += ( incomingList.get(i).getName() + " " +
                            activity.getString(R.string.quantity) + " : " + incomingList.get(i).getQuantity())+ "\n";
                }
                selectedProducts.set(temp);
            }
        });

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