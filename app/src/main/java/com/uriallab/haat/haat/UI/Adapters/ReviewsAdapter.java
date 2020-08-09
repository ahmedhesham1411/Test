package com.uriallab.haat.haat.UI.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.uriallab.haat.haat.DataModels.StoreDetailsModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.databinding.ItemReviewsBinding;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Mahmoud on 4/19/2020.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    private Activity activity;
    private List<StoreDetailsModel.ResultBean.ReviewsBean> incomingList;

    public ReviewsAdapter(Activity activity, List<StoreDetailsModel.ResultBean.ReviewsBean> incomingList) {
        this.activity = activity;
        this.incomingList = incomingList;
    }

    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemReviewsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_reviews, parent, false);
        return new ReviewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ReviewsViewHolder holder, final int position) {

        if (ConfigurationFile.getCurrentLanguage(activity).equals("ar"))
            holder.binding.starBar.setRotationY(180);

        holder.binding.userName.setText(incomingList.get(position).getAuthor_name());

        holder.binding.commentTxt.setText(incomingList.get(position).getText());

        holder.binding.starBar.setRating(incomingList.get(position).getRating());

        Picasso.get().load(incomingList.get(position).getProfile_photo_url()).placeholder(R.drawable.logo).into(holder.binding.userImg);

    }

    @Override
    public int getItemCount() {
        return incomingList.size();
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder {

        private ItemReviewsBinding binding;

        private ReviewsViewHolder(ItemReviewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}