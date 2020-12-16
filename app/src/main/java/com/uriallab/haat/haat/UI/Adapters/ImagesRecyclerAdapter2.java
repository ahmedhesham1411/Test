package com.uriallab.haat.haat.UI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.uriallab.haat.haat.DataModels.StoreDetailsModel;
import com.uriallab.haat.haat.R;

import java.util.List;

/**
 * Created by Mahmoud on 4/19/2020.
 */

public class ImagesRecyclerAdapter2 extends RecyclerView.Adapter<ImagesRecyclerAdapter2.MyViewHolder> {

    List<StoreDetailsModel.ResultBean.PhotosBean> photosBeans;
    Context context;
    int aaaaa;

/*
    public ImagesRecyclerAdapter(Context context,List<StoreDetailsModel.ResultBean.PhotosBean> photosBeans)
*/

    public ImagesRecyclerAdapter2(Context context, int photosBeans) {
        this.context = context;
        this.aaaaa = photosBeans;
    }

    @Override
    public int getItemCount() {
/*
        return photosBeans.size();
*/
        return aaaaa;

    }

    @Override
    public ImagesRecyclerAdapter2.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        /*ImgsRecyclerItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.imgs_recycler_item, parent, false);
        return new MyViewHolder(binding);*/
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imgs_recycler_item2, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImagesRecyclerAdapter2.MyViewHolder viewHolder, int position) {

        //Picasso.get().load(photosBeans.get(position).getPhoto_reference()).into(viewHolder.binding.storeImgRecycler);
        //viewHolder.roundedImageView.setText(items.get(i));
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(View view) {
            super(view);
        }

/*        ImgsRecyclerItemBinding binding;

        public MyViewHolder(ImgsRecyclerItemBinding binding) {
            super(view);
            this.binding = binding;
        }*/
    }
}