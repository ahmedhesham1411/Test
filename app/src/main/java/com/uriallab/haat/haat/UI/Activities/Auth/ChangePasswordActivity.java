package com.uriallab.haat.haat.UI.Activities.Auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.databinding.ActivityChangePasswordBinding;
import com.uriallab.haat.haat.viewModels.ChangePasswordViewModel;

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityChangePasswordBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);

        binding.setChangePasswordVM(new ChangePasswordViewModel(this));
    }
}