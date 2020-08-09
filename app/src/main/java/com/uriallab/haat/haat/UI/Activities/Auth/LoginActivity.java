package com.uriallab.haat.haat.UI.Activities.Auth;

import android.os.Bundle;

import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Activities.BaseActivity;
import com.uriallab.haat.haat.databinding.ActivityLoginBinding;
import com.uriallab.haat.haat.viewModels.LoginViewModel;

import androidx.databinding.DataBindingUtil;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        final LoginViewModel viewModel = new LoginViewModel(this);

        binding.setLoginVM(viewModel);
    }
}