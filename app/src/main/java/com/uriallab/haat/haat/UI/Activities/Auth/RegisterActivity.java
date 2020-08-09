package com.uriallab.haat.haat.UI.Activities.Auth;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Activities.BaseActivity;
import com.uriallab.haat.haat.databinding.ActivityRegisterBinding;
import com.uriallab.haat.haat.viewModels.RegisterViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;

public class RegisterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityRegisterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        Bundle bundle = getIntent().getBundleExtra("data");

        final RegisterViewModel viewModel = new RegisterViewModel(this, bundle.getString("phone"));

        binding.setRegisterVM(viewModel);

        binding.spinnerArrow.setImageResource(R.drawable.arrow_left);
        binding.spinnerArrow.setColorFilter(getResources().getColor(R.color.colorTextHint), PorterDuff.Mode.SRC_ATOP);

        List<String> personTypeList = new ArrayList<>();
        personTypeList.add(getResources().getString(R.string.choose_gender));
        personTypeList.add(getResources().getString(R.string.male));
        personTypeList.add(getResources().getString(R.string.female));

        ArrayAdapter<String> adapterPersonType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, personTypeList);
        adapterPersonType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.genderSpinner.setAdapter(adapterPersonType);

        binding.genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.gender = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
}