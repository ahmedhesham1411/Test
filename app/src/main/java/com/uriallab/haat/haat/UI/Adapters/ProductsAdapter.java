package com.uriallab.haat.haat.UI.Adapters;

import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.uriallab.haat.haat.DataModels.ProductsModel;
import com.uriallab.haat.haat.DataModels.StoreProductsModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Activities.makeOrder.StoreDetailsActivity;
import com.uriallab.haat.haat.UI.Fragments.ProductBottomSheet;
import com.uriallab.haat.haat.databinding.ItemProductsBinding;

import java.util.List;

/**
 * Created by Mahmoud on 4/19/2020.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MenuViewHolder> {

    private StoreDetailsActivity activity;
    private List<ProductsModel.ResultEntity.ProductsEntity> incomingList;

    private List<StoreProductsModel.ProductBean> productMenuModelList;

    public ProductsAdapter(StoreDetailsActivity activity, List<ProductsModel.ResultEntity.ProductsEntity> incomingList, List<StoreProductsModel.ProductBean> productMenuModelList) {
        this.activity = activity;
        this.productMenuModelList = productMenuModelList;
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
        holder.binding.productPrice.setText(incomingList.get(position).getProduct_price() + "");

        holder.binding.deleteQuantity.setImageResource(R.drawable.rubish);
        holder.binding.deleteQuantity.setColorFilter(activity.getResources().getColor(R.color.colorBlue), PorterDuff.Mode.SRC_ATOP);

        if (incomingList.get(position).isSelected()){
            holder.binding.deleteFrame.setVisibility(View.VISIBLE);
            holder.binding.quantity.setVisibility(View.VISIBLE);
        }else {
            holder.binding.deleteFrame.setVisibility(View.INVISIBLE);
            holder.binding.quantity.setVisibility(View.INVISIBLE);
        }

        holder.binding.deleteFrame.setOnClickListener(v -> {
            incomingList.get(position).setSelected(false);
            try {
                Log.e("list_size", productMenuModelList.size() + " before delete");
                productMenuModelList.remove(incomingList.get(position).getProductUID());// TODO: 8/18/2020
                Log.e("list_size", productMenuModelList.size() + " after delete");
            } catch (Exception e) {
                Log.e("list_size", "delete Exception" + e.getMessage());
                e.printStackTrace();
            }

            holder.binding.deleteFrame.setVisibility(View.INVISIBLE);
            holder.binding.quantity.setVisibility(View.INVISIBLE);
        });

        holder.itemView.setOnClickListener(v -> {
            ProductBottomSheet productBottomSheet = new ProductBottomSheet(activity, incomingList.get(position),
                    holder.binding.deleteFrame, holder.binding.quantity, productMenuModelList);
            productBottomSheet.show(activity.getSupportFragmentManager(), "tag");
        });
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