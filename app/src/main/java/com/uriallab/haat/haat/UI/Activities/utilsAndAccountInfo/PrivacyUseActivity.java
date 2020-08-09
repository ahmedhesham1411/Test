package com.uriallab.haat.haat.UI.Activities.utilsAndAccountInfo;

import android.os.Bundle;

import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.databinding.ActivityPrivacyUseBinding;
import com.uriallab.haat.haat.viewModels.PrivacyUseViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class PrivacyUseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityPrivacyUseBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_privacy_use);

        Bundle bundle = getIntent().getBundleExtra("data");

        binding.setPrivacyVM(new PrivacyUseViewModel(this, bundle.getBoolean("isPolicyUse")));
    }
}