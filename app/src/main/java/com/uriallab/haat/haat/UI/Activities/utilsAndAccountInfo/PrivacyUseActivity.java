package com.uriallab.haat.haat.UI.Activities.utilsAndAccountInfo;

import android.os.Bundle;

import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Adapters.Policy_adapter;
import com.uriallab.haat.haat.databinding.ActivityPrivacyUseBinding;
import com.uriallab.haat.haat.viewModels.PrivacyUseViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PrivacyUseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityPrivacyUseBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_privacy_use);

        Bundle bundle = getIntent().getBundleExtra("data");

        binding.setPrivacyVM(new PrivacyUseViewModel(this, bundle.getBoolean("isPolicyUse")));
    }

    void initRecycler(List<String> strings){
        RecyclerView recyclerView = findViewById(R.id.rec_policy);
        Policy_adapter policy_adapter = new Policy_adapter(getApplicationContext(),strings,"privacy");
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(policy_adapter);
    }
}