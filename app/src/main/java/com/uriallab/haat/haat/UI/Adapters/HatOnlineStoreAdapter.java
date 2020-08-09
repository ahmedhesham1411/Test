package com.uriallab.haat.haat.UI.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.uriallab.haat.haat.DataModels.HatServiceModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Activities.Updates.HatOnlineStoreActivity;
import com.uriallab.haat.haat.databinding.ItemContentBinding;
import com.uriallab.haat.haat.databinding.ItemHeaderBinding;

import java.util.List;

/**
 * Created by Mahmoud on 4/19/2020.
 */

public class HatOnlineStoreAdapter extends RecyclerView.Adapter<HatOnlineStoreAdapter.HatOnlineStoreViewHolder> {

    private static final int HEADER = 1;
    private static final int CONTENT = 2;

    private HatOnlineStoreActivity activity;
    private List<HatServiceModel.ResultEntity.HaatCardCategoryEntity> incomingList;

    public HatOnlineStoreAdapter(HatOnlineStoreActivity activity, List<HatServiceModel.ResultEntity.HaatCardCategoryEntity> incomingList) {
        this.activity = activity;
        this.incomingList = incomingList;
    }

    @Override
    public HatOnlineStoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding;
        switch (viewType) {
            case HEADER:
                binding = DataBindingUtil.inflate(inflater, R.layout.item_header, parent, false);
                return new HatOnlineStoreViewHolder((ItemHeaderBinding) binding);
            case CONTENT:
                binding = DataBindingUtil.inflate(inflater, R.layout.item_content, parent, false);
                return new HatOnlineStoreViewHolder((ItemContentBinding) binding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final HatOnlineStoreViewHolder holder, final int position) {

        switch (holder.getItemViewType()) {
            case HEADER:

                holder.headerBinding.headerTxt.setText(incomingList.get(position).getCategory_Title());

                CardAdapter cardAdapter = new CardAdapter(activity, incomingList.get(position).getCards());
                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                holder.headerBinding.recyclerContent.setLayoutManager(layoutManager);
                holder.headerBinding.recyclerContent.setAdapter(cardAdapter);

                break;
            case CONTENT:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return HEADER;
    }

    @Override
    public int getItemCount() {
        return incomingList.size();
    }

    public class HatOnlineStoreViewHolder extends RecyclerView.ViewHolder {

        private ItemHeaderBinding headerBinding;

        private ItemContentBinding contentBinding;

        private HatOnlineStoreViewHolder(ItemHeaderBinding headerBinding) {
            super(headerBinding.getRoot());
            this.headerBinding = headerBinding;
        }

        private HatOnlineStoreViewHolder(ItemContentBinding contentBinding) {
            super(contentBinding.getRoot());
            this.contentBinding = contentBinding;
        }
    }
}