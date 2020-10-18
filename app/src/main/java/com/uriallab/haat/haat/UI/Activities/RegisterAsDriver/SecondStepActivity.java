package com.uriallab.haat.haat.UI.Activities.RegisterAsDriver;

import android.graphics.PorterDuff;
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
    public ArrayAdapter<String> cityAdapter;
    public ArrayAdapter<String> identityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySecondStepBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_second_step);

        binding.areaArrow.setImageResource(R.drawable.arrow_left);
        binding.nationalityArrow.setImageResource(R.drawable.arrow_left);
        binding.cityArrow.setImageResource(R.drawable.arrow_left);
        binding.identityArrow.setImageResource(R.drawable.arrow_left);
        binding.areaArrow.setColorFilter(getResources().getColor(R.color.colorTextHint), PorterDuff.Mode.SRC_ATOP);
        binding.nationalityArrow.setColorFilter(getResources().getColor(R.color.colorTextHint), PorterDuff.Mode.SRC_ATOP);
        binding.cityArrow.setColorFilter(getResources().getColor(R.color.colorTextHint), PorterDuff.Mode.SRC_ATOP);
        binding.identityArrow.setColorFilter(getResources().getColor(R.color.colorTextHint), PorterDuff.Mode.SRC_ATOP);

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

        areaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, viewModel.regionList);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.areaSpinner.setAdapter(areaAdapter);
        binding.areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.region = viewModel.regionIdList.get(position);
                viewModel.getCity();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, viewModel.cityList);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.citySpinner.setAdapter(cityAdapter);
        binding.citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.city = viewModel.cityIdList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        identityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, viewModel.identityList);
        identityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.identitySpinner.setAdapter(identityAdapter);
        binding.identitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.identity = viewModel.identityIdList.get(position);
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