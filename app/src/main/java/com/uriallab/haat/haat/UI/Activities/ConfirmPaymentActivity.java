package com.uriallab.haat.haat.UI.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.databinding.ActivityConfirmPaymentBinding;

public class ConfirmPaymentActivity extends AppCompatActivity {

    private String lang = "ar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityConfirmPaymentBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm_payment);

        Bundle bundle = getIntent().getBundleExtra("data");

        if (ConfigurationFile.getCurrentLanguage(this).equals("ar"))
            lang = "ar";
        else
            lang = "en";

        String url = bundle.getString("url");

        FrameLayout backFrame = (FrameLayout) findViewById(R.id.frame_back);
        ImageView backImg = (ImageView) findViewById(R.id.back_img);

        try {

            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.web_view_2);

            WebView mainWebView = new WebView(getApplicationContext());

            mainWebView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            mainWebView.setWebViewClient(new WebViewClient());
            mainWebView.getSettings().setJavaScriptEnabled(true);
            mainWebView.getSettings().setLoadWithOverviewMode(true);
            mainWebView.getSettings().setUseWideViewPort(true);
            mainWebView.getSettings().setBuiltInZoomControls(true);
            mainWebView.loadUrl(url);
            mainWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    Log.e("WEB_URL", mainWebView.getUrl());// TODO: 7/29/2020  
//                    if (view.getUrl().contains("haat://result")) {
//                        onBackPressed();
//                    }
                    super.onProgressChanged(view, newProgress);
                }
            });

            linearLayout.addView(mainWebView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ConfigurationFile.setCurrentLanguage(this, lang);

        if (ConfigurationFile.getCurrentLanguage(this).equals("ar"))
            backImg.setRotation(180);

        backFrame.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("sent", false);
        setResult(Activity.RESULT_OK, returnIntent);
        super.onBackPressed();
    }
}