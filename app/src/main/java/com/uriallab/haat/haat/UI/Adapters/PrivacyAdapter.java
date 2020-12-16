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

public class PrivacyAdapter extends RecyclerView.Adapter<PrivacyAdapter.MyViewHolder> {

    List<StoreDetailsModel.ResultBean.PhotosBean> photosBeans;
    List<String> strings;
    String key;
    Context context;
    int aaaaa;

/*
    public ImagesRecyclerAdapter(Context context,List<StoreDetailsModel.ResultBean.PhotosBean> photosBeans)
*/

    public PrivacyAdapter(Context context, List<String> strings,String key) {
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
    public PrivacyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        /*ImgsRecyclerItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.imgs_recycler_item, parent, false);
        return new MyViewHolder(binding);*/
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.policyy, parent, false);
        return new PrivacyAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PrivacyAdapter.MyViewHolder viewHolder, int position) {

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