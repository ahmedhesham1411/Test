package com.uriallab.haat.haat.UI.Activities;

import android.os.Bundle;


import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.Utilities.Utilities;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utilities.darkenStatusBar(this, getResources().getColor(R.color.colorWhite));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utilities.hideKeyboard(this);
    }

    @Override
    public void onBackPressed() {
        //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onBackPressed();
    }
}