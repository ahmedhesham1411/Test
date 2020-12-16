package com.uriallab.haat.haat.UI.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.CommentsModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.databinding.ItemCommentsBinding;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Mahmoud on 4/19/2020.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    private Activity activity;
    private List<CommentsModel.ResultBean.CommentsBean> incomingList;

    public CommentsAdapter(Activity activity, List<CommentsModel.ResultBean.CommentsBean> incomingList) {
        this.activity = activity;
        this.incomingList = incomingList;
    }

    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCommentsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_comments, parent, false);
        return new CommentsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final CommentsViewHolder holder, final int position) {

        holder.binding.userName.setText(incomingList.get(position).getFrom_Full_Name());

        Picasso.get().load(APIModel.BASE_URL+incomingList.get(position).getFrom_Img_Url())
                .placeholder(R.drawable.profile).into(holder.binding.userImg);

        holder.binding.desc.setText(incomingList.get(position).getComment_Desc());
        holder.binding.commentTxt.setText(incomingList.get(position).getComment_Date());

        holder.binding.starBar.setRating(incomingList.get(position).getComment_Rate());

        if (ConfigurationFile.getCurrentLanguage(activity).equals("ar"))
            holder.binding.starBar.setRotationY(180);

    }

    @Override
    public int getItemCount() {
        return incomingList.size();
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder {

        private ItemCommentsBinding binding;

        private CommentsViewHolder(ItemCommentsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}