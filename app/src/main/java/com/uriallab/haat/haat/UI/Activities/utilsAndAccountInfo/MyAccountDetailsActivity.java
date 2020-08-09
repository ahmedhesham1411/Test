package com.uriallab.haat.haat.UI.Activities.utilsAndAccountInfo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.databinding.ActivityMyAccountDetailsBinding;
import com.uriallab.haat.haat.viewModels.AccountDetailsViewModel;

public class MyAccountDetailsActivity extends AppCompatActivity {

    private AccountDetailsViewModel viewModel;

    private ActivityMyAccountDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_account_details);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel = new AccountDetailsViewModel(this);

        binding.setAccountDetailsVM(viewModel);
    }
}