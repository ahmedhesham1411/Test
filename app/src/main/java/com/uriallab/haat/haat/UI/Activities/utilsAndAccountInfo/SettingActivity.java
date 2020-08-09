package com.uriallab.haat.haat.UI.Activities.utilsAndAccountInfo;

import android.graphics.PorterDuff;
import android.os.Bundle;

import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.databinding.ActivitySettingBinding;
import com.uriallab.haat.haat.viewModels.SettingViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class SettingActivity extends AppCompatActivity {

    private ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);

        iconColor();

        binding.setSettingVM(new SettingViewModel(this));
    }

    private void iconColor() {
        binding.profileArrow.setImageResource(R.drawable.arrow_left);
        binding.policyArrow.setImageResource(R.drawable.arrow_left);
        binding.privacyArrow.setImageResource(R.drawable.arrow_left);
        binding.rateArrow.setImageResource(R.drawable.arrow_left);
        binding.contactArrow.setImageResource(R.drawable.arrow_left);
        binding.langArrow.setImageResource(R.drawable.arrow_left);
        binding.addCoupon.setImageResource(R.drawable.coupon);
        binding.addCoupon.setColorFilter(getResources().getColor(R.color.colorBlue), PorterDuff.Mode.SRC_ATOP);
        binding.profileArrow.setColorFilter(getResources().getColor(R.color.colorTextHint), PorterDuff.Mode.SRC_ATOP);
        binding.policyArrow.setColorFilter(getResources().getColor(R.color.colorTextHint), PorterDuff.Mode.SRC_ATOP);
        binding.privacyArrow.setColorFilter(getResources().getColor(R.color.colorTextHint), PorterDuff.Mode.SRC_ATOP);
        binding.rateArrow.setColorFilter(getResources().getColor(R.color.colorTextHint), PorterDuff.Mode.SRC_ATOP);
        binding.contactArrow.setColorFilter(getResources().getColor(R.color.colorTextHint), PorterDuff.Mode.SRC_ATOP);
        binding.langArrow.setColorFilter(getResources().getColor(R.color.colorTextHint), PorterDuff.Mode.SRC_ATOP);
    }
}