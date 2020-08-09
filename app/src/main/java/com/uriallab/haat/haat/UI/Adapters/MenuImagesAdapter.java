package com.uriallab.haat.haat.UI.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.uriallab.haat.haat.DataModels.StoreDetailsModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.databinding.ItemMenuImageBinding;

import java.io.File;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Mahmoud on 4/19/2020.
 */

public class MenuImagesAdapter extends RecyclerView.Adapter<MenuImagesAdapter.MenuViewHolder> {

    private Activity activity;
    private List<StoreDetailsModel.ResultBean.PhotosBean> incomingList;
    private List<File> incomingListLocal;

    private boolean isLocal = false;

    public MenuImagesAdapter(Activity activity, List<StoreDetailsModel.ResultBean.PhotosBean> incomingList) {
        this.activity = activity;
        this.incomingList = incomingList;
    }

    public MenuImagesAdapter(Activity activity, List<File> incomingListLocal, boolean isLocal) {
        this.activity = activity;
        this.incomingListLocal = incomingListLocal;
        this.isLocal = isLocal;
        Log.e("ListSize", incomingListLocal.size()+"\t"+ isLocal);
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMenuImageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_menu_image, parent, false);
        return new MenuViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MenuViewHolder holder, final int position) {

        if (isLocal){
            Log.e("PATH", incomingListLocal.get(position)+"");
            Picasso.get().load(incomingListLocal.get(position)).placeholder(R.drawable.logo).into(holder.binding.menuImage);
        }else {
//            String photoUrl = "https://maps.googleapis.com/maps/api/place/photo?photoreference="+
//                    incomingList.get(position).getPhoto_reference()
//                    +"&maxheight=200&maxwidth=200&key=AIzaSyAmD_A7N-SI2JbkhGh4xY_OFip7GtQRZfg";
//
//            Picasso.get().load(photoUrl).placeholder(R.drawable.logo).into(holder.binding.menuImage);
        }

    }

    @Override
    public int getItemCount() {
        if (isLocal)
            return incomingListLocal.size();
        else
            return incomingList.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        private ItemMenuImageBinding binding;

        private MenuViewHolder(ItemMenuImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}