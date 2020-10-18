package com.uriallab.haat.haat.UI.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.HatServiceModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Activities.Updates.HatOnlineStoreActivity;
import com.uriallab.haat.haat.UI.Fragments.HatCardBottomSheet;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.databinding.ItemCardBinding;

import java.util.List;

/**
 * Created by Mahmoud on 4/19/2020.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private HatOnlineStoreActivity activity;
    private List<HatServiceModel.ResultEntity.HaatCardCategoryEntity.CardsEntity> incomingList;

    public CardAdapter(HatOnlineStoreActivity activity, List<HatServiceModel.ResultEntity.HaatCardCategoryEntity.CardsEntity> incomingList) {
        this.activity = activity;
        this.incomingList = incomingList;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_card, parent, false);
        return new CardViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, final int position) {

        holder.binding.cardName.setText(incomingList.get(position).getName());
        holder.binding.cardPrice.setText(Utilities.roundPrice(incomingList.get(position).getPrice())+" "+ activity.getString(R.string.currency));

        Picasso.get().load(incomingList.get(position).getUrl()).into(holder.binding.catImg);

        holder.itemView.setOnClickListener(v -> {
            HatCardBottomSheet hatCardBottomSheet = new HatCardBottomSheet(activity, incomingList.get(position));
            hatCardBottomSheet.show(activity.getSupportFragmentManager(), "tag");
        });

    }

    @Override
    public int getItemCount() {
        return incomingList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        private ItemCardBinding binding;

        private CardViewHolder(ItemCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}