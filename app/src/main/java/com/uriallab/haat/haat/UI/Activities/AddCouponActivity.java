package com.uriallab.haat.haat.UI.Activities;

import android.os.Bundle;

import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.databinding.ActivityAddCouponBinding;
import com.uriallab.haat.haat.viewModels.AddCouponViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class AddCouponActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAddCouponBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_add_coupon);

        binding.setAddCouponVM(new AddCouponViewModel(this));
    }
}