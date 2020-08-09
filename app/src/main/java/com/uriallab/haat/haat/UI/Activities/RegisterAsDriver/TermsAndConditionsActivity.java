package com.uriallab.haat.haat.UI.Activities.RegisterAsDriver;

import android.os.Bundle;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.databinding.ActivityTermsAndConditionsBinding;
import com.uriallab.haat.haat.viewModels.DriverTermsAndConditionsViewModel;

import static com.uriallab.haat.haat.Utilities.GlobalVariables.FINISH_ACTIVITY_REGISTER;

public class TermsAndConditionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityTermsAndConditionsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_terms_and_conditions);

        FINISH_ACTIVITY_REGISTER = false;

        DriverTermsAndConditionsViewModel viewModel = new DriverTermsAndConditionsViewModel(this);

        binding.setTermsVM(viewModel);

        binding.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.isChecked = isChecked;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (FINISH_ACTIVITY_REGISTER)
            finish();
    }
}