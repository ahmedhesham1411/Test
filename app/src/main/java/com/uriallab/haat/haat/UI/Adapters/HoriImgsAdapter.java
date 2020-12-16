package com.uriallab.haat.haat.UI.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Activities.Updates.PhotoViewActivity;
import com.uriallab.haat.haat.Utilities.IntentClass;

import java.util.List;

public class HoriImgsAdapter extends RecyclerView.Adapter<HoriImgsAdapter.ViewHolder>{
    private MyListData[] listdata;
    Boolean check_show = false;
    Activity context;

    List<String> imgs;
    // RecyclerView recyclerView;
    public HoriImgsAdapter(List<String> imgs,Activity context) {
            this.imgs = imgs;
            this.context=context;
            }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.ahmed, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
            }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    //final MyListData myListData = listdata[position];
        //holder.progressBar.setVisibility(View.GONE);
        int i =position;
            if (i == 0){
                holder.imageView.setVisibility(View.GONE);
                holder.progressBar.setVisibility(View.GONE);
            }else {
                Picasso.get().load(APIModel.BASE_URL + imgs.get(position)).into(holder.imageView);
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("img", APIModel.BASE_URL + imgs.get(position));
                        IntentClass.goToActivity(context, PhotoViewActivity.class, bundle);
                    }
                });
        }
    }


    @Override
    public int getItemCount() {
        return imgs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        ProgressBar progressBar;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_chat);
            progressBar = itemView.findViewById(R.id.loaddd);

        }
}
}