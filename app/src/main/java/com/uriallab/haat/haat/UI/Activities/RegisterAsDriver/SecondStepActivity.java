package com.uriallab.haat.haat.UI.Activities.RegisterAsDriver;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.uriallab.haat.haat.DataModels.DriverRegisterModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.databinding.ActivitySecondStepBinding;
import com.uriallab.haat.haat.viewModels.DriverRegisterSecondStepViewModel;

import static com.uriallab.haat.haat.Utilities.GlobalVariables.FINISH_ACTIVITY_REGISTER;

public class SecondStepActivity extends AppCompatActivity {

    private DriverRegisterSecondStepViewModel viewModel;

    public ArrayAdapter<String> nationalityAdapter;
    public ArrayAdapter<String> areaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySecondStepBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_second_step);

        Gson gson = new Gson();
        DriverRegisterModel driverRegisterModel = gson.fromJson(getIntent().getStringExtra("myjson"), DriverRegisterModel.class);

        viewModel = new DriverRegisterSecondStepViewModel(this, driverRegisterModel);

        binding.setSecondStepVM(viewModel);

        nationalityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, viewModel.nationalityList);
        nationalityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.nationalitySpinner.setAdapter(nationalityAdapter);
        binding.nationalitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.nationality = viewModel.nationalityIdList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        areaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, viewModel.areaList);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.areaSpinner.setAdapter(areaAdapter);
        binding.areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.area = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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