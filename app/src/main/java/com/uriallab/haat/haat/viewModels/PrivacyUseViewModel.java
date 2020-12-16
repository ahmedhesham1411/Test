package com.uriallab.haat.haat.viewModels;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.TermsModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.UI.Adapters.Policy_adapter;
import com.uriallab.haat.haat.UI.Adapters.PrivacyAdapter;
import com.uriallab.haat.haat.Utilities.Dialogs;
import com.uriallab.haat.haat.Utilities.LoadingDialog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class PrivacyUseViewModel {

    public ObservableField<String> textObservable = new ObservableField<>();
    public ObservableField<String> titleObservable = new ObservableField<>();
    List<String> strings = new ArrayList<String>();

    public ObservableInt rotation = new ObservableInt(0);

    private Activity activity;

    public PrivacyUseViewModel(Activity activity, boolean isPolicyUse) {
        this.activity = activity;
        RecyclerView recyclerView = activity.findViewById(R.id.rec_policy);
        TextView textView = activity.findViewById(R.id.det_pol);

        if (isPolicyUse) {
            termsAndPrivacy("Setting/GetTerms");
            titleObservable.set(activity.getString(R.string.policy));
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);

        } else {
            termsAndPrivacy("Setting/GetPolicies");
            titleObservable.set(activity.getString(R.string.privacy));
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }

        if (ConfigurationFile.getCurrentLanguage(activity).equals("ar")) {
            rotation.set(180);
        }
    }

    private void termsAndPrivacy(final String url) {
        final LoadingDialog loadingDialog = new LoadingDialog();
        APIModel.getMethod(activity, url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                termsAndPrivacy(url);
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                Type dataType = new TypeToken<TermsModel>() {
                }.getType();
                TermsModel data = new Gson().fromJson(responseString, dataType);

                textObservable.set(data.getResult().getItmes());
                String aa = data.getResult().getItmes();

                strings.add(aa);
                initRecycler(strings);

            }

            void initRecycler(List<String> strings){
                TextView textView = activity.findViewById(R.id.det_pol);
                textView.setText(strings.get(0));


               RecyclerView recyclerView = activity.findViewById(R.id.rec_policy);
                PrivacyAdapter policy_adapter = new PrivacyAdapter(activity,strings,"policy");
                recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(policy_adapter);
            }

            @Override
            public void onStart() {
                super.onStart();
                Dialogs.showLoading(activity, loadingDialog);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                Dialogs.dismissLoading(loadingDialog);
            }
        });
    }

    public void back() {
        activity.finish();
    }
}