package com.uriallab.haat.haat.UI.Activities.RegisterAsDriver;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.uriallab.haat.haat.DataModels.DriverRegisterModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.Utilities.RealPathUtil;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.Utilities.camera.Camera;
import com.uriallab.haat.haat.databinding.ActivityThirdStepBinding;
import com.uriallab.haat.haat.viewModels.DriverRegisterThirdStepViewModel;

import static com.uriallab.haat.haat.Utilities.camera.Camera.CAMERA_REQUEST;
import static com.uriallab.haat.haat.Utilities.camera.Camera.GALLERY_REQUEST;
import static com.uriallab.haat.haat.Utilities.camera.Camera.currentPhotoPath;

public class ThirdStepActivity extends AppCompatActivity {

    private DriverRegisterThirdStepViewModel viewModel;
    private ActivityThirdStepBinding binding;

    public ArrayAdapter<String> carTypeAdapter;
    public ArrayAdapter<String> carYearAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_third_step);

        Gson gson = new Gson();
        DriverRegisterModel driverRegisterModel = gson.fromJson(getIntent().getStringExtra("myjson"), DriverRegisterModel.class);

        viewModel = new DriverRegisterThirdStepViewModel(this, driverRegisterModel);

        binding.setThirdStepVM(viewModel);

        carTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, viewModel.typeList);
        carTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.carTypeSpinner.setAdapter(carTypeAdapter);
        binding.carTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.type = viewModel.typeIdList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        carYearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, viewModel.yearList);
        carYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.produceYearSpinner.setAdapter(carYearAdapter);
        binding.produceYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.year = viewModel.yearList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
                try {

                    Bitmap bitmapImage;

                    try {
                        bitmapImage = Camera.resizeBitmap(this, BitmapFactory.decodeFile(RealPathUtil.getRealPath(this, Uri.parse(currentPhotoPath))));
                    } catch (Exception e) {
                        bitmapImage = Camera.resizeBitmap(this, BitmapFactory.decodeFile(Camera.getRealPathFromURI(this, Uri.parse(currentPhotoPath))));
                        e.printStackTrace();
                    }

                    if (viewModel.imgType == 1) {
                        binding.profilePic.setImageBitmap(bitmapImage);
                        viewModel.profilePic = Camera.convertBitmapToBase64(bitmapImage);
                    } else if (viewModel.imgType == 2) {
                        binding.idNumberPic.setImageBitmap(bitmapImage);
                        viewModel.idPic = Camera.convertBitmapToBase64(bitmapImage);
                    } else {
                        binding.lysincePic.setImageBitmap(bitmapImage);
                        viewModel.licensePic = Camera.convertBitmapToBase64(bitmapImage);
                    }

                } catch (Exception e) {
                    Log.e("IMAGE", "CAMERA_REQUEST Exception: " + e.getMessage());
                    e.printStackTrace();
                }

            } else if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
                try {
                    Uri imageUri = data.getData();
                    Bitmap bitmapImage;

                    try {
                        bitmapImage = Camera.resizeBitmap(this, BitmapFactory.decodeFile(RealPathUtil.getRealPath(this, imageUri)));
                    } catch (Exception e) {
                        bitmapImage = Camera.resizeBitmap(this, BitmapFactory.decodeFile(Camera.getRealPathFromURI(this, imageUri)));
                        e.printStackTrace();
                    }

                    if (viewModel.imgType == 1) {
                        binding.profilePic.setImageBitmap(bitmapImage);
                        viewModel.profilePic = Camera.convertBitmapToBase64(bitmapImage);
                    } else if (viewModel.imgType == 2) {
                        binding.idNumberPic.setImageBitmap(bitmapImage);
                        viewModel.idPic = Camera.convertBitmapToBase64(bitmapImage);
                    } else {
                        binding.lysincePic.setImageBitmap(bitmapImage);
                        viewModel.licensePic = Camera.convertBitmapToBase64(bitmapImage);
                    }

                } catch (Exception e) {
                    Log.e("IMAGE", "GALLERY_REQUEST Exception: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Utilities.hideKeyboard(this);
        super.onBackPressed();
    }
}