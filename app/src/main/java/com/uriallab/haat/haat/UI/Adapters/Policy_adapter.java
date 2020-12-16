package com.uriallab.haat.haat.UI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.uriallab.haat.haat.DataModels.StoreDetailsModel;
import com.uriallab.haat.haat.R;

import java.util.List;

public class Policy_adapter extends RecyclerView.Adapter<Policy_adapter.MyViewHolder> {

    List<StoreDetailsModel.ResultBean.PhotosBean> photosBeans;
    List<String> strings;
    String key;
    Context context;
    int aaaaa;

/*
    public ImagesRecyclerAdapter(Context context,List<StoreDetailsModel.ResultBean.PhotosBean> photosBeans)
*/

    public Policy_adapter(Context context, List<String> strings,String key) {
        this.context = context;
        this.strings = strings;
        this.key=key;
    }

    @Override
    public int getItemCount() {
/*
        return photosBeans.size();
*/
        return strings.size();

    }

    @Override
    public Policy_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        /*ImgsRecyclerItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.imgs_recycler_item, parent, false);
        return new MyViewHolder(binding);*/
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_policy, parent, false);
        return new Policy_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Policy_adapter.MyViewHolder viewHolder, int position) {

        //Picasso.get().load(photosBeans.get(position).getPhoto_reference()).into(viewHolder.binding.storeImgRecycler);
        //viewHolder.roundedImageView.setText(items.get(i));

        String aa = strings.get(position);
        viewHolder.num.setText(String.valueOf(position+1));
        viewHolder.desc.setText(strings.get(position));

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView num,desc;
        FrameLayout frame;
        public MyViewHolder(View view) {
            super(view);

            num = view.findViewById(R.id.item_num);
            desc = view.findViewById(R.id.det_pol);
            frame = view.findViewById(R.id.frame);
        }


/*        ImgsRecyclerItemBinding binding;

        public MyViewHolder(ImgsRecyclerItemBinding binding) {
            super(view);
            this.binding = binding;
        }*/
    }
}