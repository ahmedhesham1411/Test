package com.uriallab.haat.haat.UI.Activities.RegisterAsDriver;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.LoginSession;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.viewModels.DriverRegisterFirstStepViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.uriallab.haat.haat.Utilities.GlobalVariables.FINISH_ACTIVITY_REGISTER;

public class FirstStepActivity extends AppCompatActivity {

    private DriverRegisterFirstStepViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.uriallab.haat.haat.databinding.ActivityFirstStepBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_first_step);

        viewModel = new DriverRegisterFirstStepViewModel(this);

        binding.setFirstStepVM(viewModel);

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

        if (LoginSession.getUserData(this).getResult().getUserData().getUser_GenderID() == 1)
            binding.genderSpinner.setSelection(1);
        else if (LoginSession.getUserData(this).getResult().getUserData().getUser_GenderID() == 2)
            binding.genderSpinner.setSelection(2);
        else
            binding.genderSpinner.setSelection(0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (FINISH_ACTIVITY_REGISTER)
            finish();
    }

    @Override
    public void onBackPressed() {
        Utilities.hideKeyboard(this);
        super.onBackPressed();
    }
}