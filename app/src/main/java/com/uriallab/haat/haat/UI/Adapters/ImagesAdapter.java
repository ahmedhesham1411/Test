package com.uriallab.haat.haat.UI.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uriallab.haat.haat.Interfaces.DeleteImage;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.databinding.ItemMenuImageBinding;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Mahmoud on 4/19/2020.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder> {

    private Activity activity;
    private List<Bitmap> incomingList;
    private DeleteImage deleteImage;


    public ImagesAdapter(Activity activity, List<Bitmap> incomingList, DeleteImage deleteImage) {
        this.activity = activity;
        this.incomingList = incomingList;
        this.deleteImage = deleteImage;
    }

    @Override
    public ImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMenuImageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_menu_image, parent, false);
        return new ImagesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ImagesViewHolder holder, final int position) {

        holder.binding.menuImage.setImageBitmap(incomingList.get(position));

        holder.binding.delete.setVisibility(View.VISIBLE);

        holder.binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(position);
                deleteImage.deleteImage(position);
            }
        });
    }

    private void removeItem(int pos){
        incomingList.remove(pos);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return incomingList.size();
    }

    public class ImagesViewHolder extends RecyclerView.ViewHolder {

        private ItemMenuImageBinding binding;

        private ImagesViewHolder(ItemMenuImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}