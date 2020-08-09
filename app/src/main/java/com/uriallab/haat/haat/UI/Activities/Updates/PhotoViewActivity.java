package com.uriallab.haat.haat.UI.Activities.Updates;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.squareup.picasso.Picasso;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Activities.BaseActivity;
import com.uriallab.haat.haat.databinding.ActivityPhotoViewBinding;

public class PhotoViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPhotoViewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_view);

        Bundle bundle = getIntent().getBundleExtra("data");

        Picasso.get().load(bundle.getString("img")).into(binding.photoView);
    }
}