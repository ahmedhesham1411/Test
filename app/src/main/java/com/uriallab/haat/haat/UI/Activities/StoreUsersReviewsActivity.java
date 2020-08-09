package com.uriallab.haat.haat.UI.Activities;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.uriallab.haat.haat.DataModels.StoreDetailsModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.UI.Adapters.ReviewsAdapter;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.databinding.ActivityStoreUsersReviewsBinding;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

public class StoreUsersReviewsActivity extends AppCompatActivity {

    private ActivityStoreUsersReviewsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_store_users_reviews);

        if (ConfigurationFile.getCurrentLanguage(this).equals("ar"))
            binding.backImg.setRotation(180);

        binding.frameBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Gson gson = new Gson();
        StoreDetailsModel storeDetailsModel = gson.fromJson(getIntent().getStringExtra("myjson"), StoreDetailsModel.class);

        initMenuRecycler(storeDetailsModel.getResult().getReviews());
    }

    public void initMenuRecycler(List<StoreDetailsModel.ResultBean.ReviewsBean> imagesList) {
        ReviewsAdapter reviewsAdapter = new ReviewsAdapter(this, imagesList);
        binding.recyclerRates.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerRates.setAdapter(reviewsAdapter);
        Utilities.runAnimation(binding.recyclerRates, 1);
    }
}