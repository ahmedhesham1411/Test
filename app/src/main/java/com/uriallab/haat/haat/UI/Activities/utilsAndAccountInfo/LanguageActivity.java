package com.uriallab.haat.haat.UI.Activities.utilsAndAccountInfo;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.UI.Activities.SplashActivity;
import com.uriallab.haat.haat.Utilities.IntentClass;
import com.uriallab.haat.haat.databinding.ActivityLanguageBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class LanguageActivity extends AppCompatActivity {

    private ActivityLanguageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_language);

        if (ConfigurationFile.getCurrentLanguage(this).equals("ar")){
            binding.backImg.setRotation(180);
            binding.arabicButton.setChecked(true);
            binding.arabicButton.setTextColor(getResources().getColor(R.color.colorBlue));
        }else {
            binding.englishButton.setChecked(true);
            binding.englishButton.setTextColor(getResources().getColor(R.color.colorBlue));
        }

        onClicks();
    }

    private void onClicks() {
        binding.frameBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.arabicButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ConfigurationFile.setCurrentLanguage(LanguageActivity.this, "ar");
                    IntentClass.goToActivityClearTop(LanguageActivity.this, SplashActivity.class, null);
                }
            }
        });

        binding.englishButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ConfigurationFile.setCurrentLanguage(LanguageActivity.this, "en");
                    IntentClass.goToActivityClearTop(LanguageActivity.this, SplashActivity.class, null);
                }
            }
        });
    }
}