package com.uriallab.haat.haat.UI.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.SharedPreferences.LoginSession;
import com.uriallab.haat.haat.UI.Activities.Auth.LoginActivity;
import com.uriallab.haat.haat.UI.MainActivity;
import com.uriallab.haat.haat.Utilities.IntentClass;
import com.uriallab.haat.haat.Utilities.MutedVideView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ConfigurationFile.setCurrentLanguage(this, ConfigurationFile.getCurrentLanguage(this));

        MutedVideView videoView = findViewById(R.id.video_view);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.logo_vid));
        videoView.requestFocus();
        videoView.start();

        try {
            if (getIntent().getExtras().getBoolean("notification")) {
                String type = getIntent().getExtras().getString("key");
                String orderID = getIntent().getExtras().getString("orderId");

                Log.e("Splash", orderID + "\t" + type);

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("key", type);
                intent.putExtra("orderID", orderID);
                startActivity(intent);
                return;

            } else {
                startActivity();
            }
        } catch (Exception e) {
            startActivity();
            e.printStackTrace();
        }
    }

    private void startActivity() {
        new Handler().postDelayed(() -> {

            if (LoginSession.isLoggedIn(SplashActivity.this)) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("key", "-1");
                Log.e("Splash", "key -1");
                startActivity(intent);
            } else {
                IntentClass.goToActivityAndClear(SplashActivity.this, LoginActivity.class, null, R.anim.fade_in_slow, R.anim.fade_out_slow);
            }

        }, 3000);
    }
}