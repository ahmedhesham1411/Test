package com.uriallab.haat.haat.UI.Activities.Auth;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;

import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Activities.BaseActivity;
import com.uriallab.haat.haat.databinding.ActivityCodeBinding;
import com.uriallab.haat.haat.viewModels.CodeViewModel;

import androidx.databinding.DataBindingUtil;

public class CodeActivity extends BaseActivity {

    public ActivityCodeBinding binding;

    private CodeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_code);

        binding.durationTxt.setPaintFlags(binding.durationTxt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Bundle bundle = getIntent().getBundleExtra("data");

        viewModel = new CodeViewModel(this, bundle.getString("code"), bundle.getString("phone"), bundle.getBoolean("isRegistered"));

        binding.setCodeVM(viewModel);

        binding.num1Edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                requestFocus(1);
            }
        });

        binding.num2Edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                requestFocus(2);
            }
        });

        binding.num3Edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                requestFocus(3);
            }
        });

        binding.num4Edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                requestFocus(4);
            }
        });
        binding.num5Edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                requestFocus(5);
            }
        });
    }

    public void requestFocus(int input) {
        String str = "";
        switch (input) {
            case 1:
                str = binding.num1Edt.getText().toString();
                if (!str.isEmpty())
                    binding.num2Edt.requestFocus();
                break;

            case 2:
                str = binding.num2Edt.getText().toString();
                if (!str.isEmpty())
                    binding.num3Edt.requestFocus();
                break;

            case 3:
                str = binding.num3Edt.getText().toString();
                if (!str.isEmpty())
                    binding.num4Edt.requestFocus();
                break;
            case 4:
                str = binding.num4Edt.getText().toString();
                if (!str.isEmpty())
                    binding.num5Edt.requestFocus();
                break;
            case 5:
                str = binding.num5Edt.getText().toString();
                if (!str.isEmpty()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(binding.num5Edt.getWindowToken(), 0);
                    viewModel.checkCodeAndGo();
                }
                break;
        }
    }
}