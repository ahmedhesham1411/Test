package com.uriallab.haat.haat.UI.Activities.Updates;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.uriallab.haat.haat.DataModels.HatServiceModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Adapters.HatOnlineStoreAdapter;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.databinding.ActivityHatOnlineStoreBinding;
import com.uriallab.haat.haat.viewModels.HatOnlineStoreViewModel;

import java.util.List;

public class HatOnlineStoreActivity extends AppCompatActivity {

    private ActivityHatOnlineStoreBinding binding;

    private HatOnlineStoreViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hat_online_store);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_hat_online_store);

        viewModel = new HatOnlineStoreViewModel(this);

        binding.setHatOnlineStoreVM(viewModel);
    }

    public void initRecycler(List<HatServiceModel.ResultEntity.HaatCardCategoryEntity> services) {
        HatOnlineStoreAdapter hatOnlineStoreAdapter = new HatOnlineStoreAdapter(this, services);
        binding.recyclerHat.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerHat.setAdapter(hatOnlineStoreAdapter);
        Utilities.runAnimation(binding.recyclerHat, 2);
    }
}