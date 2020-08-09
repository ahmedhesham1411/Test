package com.uriallab.haat.haat.viewModels;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.AccountModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.SharedPreferences.LoginSession;
import com.uriallab.haat.haat.UI.Activities.PaymentGateActivity;
import com.uriallab.haat.haat.Utilities.Dialogs;
import com.uriallab.haat.haat.Utilities.IntentClass;
import com.uriallab.haat.haat.Utilities.LoadingDialog;
import com.uriallab.haat.haat.Utilities.Utilities;

import java.lang.reflect.Type;

public class AccountDetailsViewModel {

    public ObservableBoolean isActive = new ObservableBoolean(false);

    public ObservableField<String> driverStatus = new ObservableField<>();

    public ObservableField<String> orderNum = new ObservableField<>();
    public ObservableField<String> totalFee = new ObservableField<>();
    public ObservableField<String> myMoney = new ObservableField<>();
    public ObservableField<String> amountToPay = new ObservableField<>();

    public ObservableInt rotation = new ObservableInt(0);

    private double money;

    private Activity activity;

    public AccountDetailsViewModel(Activity activity) {
        this.activity = activity;

        isActive.set(LoginSession.getUserData(activity).getResult().getUserData().isIsEnabled());

        if (isActive.get())
            driverStatus.set(activity.getString(R.string.eligible));
        else
            driverStatus.set(activity.getString(R.string.stopped));

        if (ConfigurationFile.getCurrentLanguage(activity).equals("ar")) {
            rotation.set(180);
        }

        getAccountDetails();
    }

    private void getAccountDetails() {
        final LoadingDialog loadingDialog = new LoadingDialog();
        APIModel.getMethod(activity, "Setting/GetAccountInfo", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                getAccountDetails();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                Type dataType = new TypeToken<AccountModel>() {
                }.getType();
                AccountModel data = new Gson().fromJson(responseString, dataType);

                orderNum.set(data.getResult().getAccountInfo().getOrderscount() + " " + activity.getString(R.string.order));
                totalFee.set(Utilities.roundPrice(data.getResult().getAccountInfo().getDebitamt()) + " " + activity.getString(R.string.currency));

                money = Double.parseDouble(Utilities.roundPrice(data.getResult().getAccountInfo().getCridetamt()));

                myMoney.set(Utilities.roundPrice(data.getResult().getAccountInfo().getMyprofits()) + " " + activity.getString(R.string.currency));

                amountToPay.set(Utilities.roundPrice(data.getResult().getAccountInfo().getCridetamt()) + " " + activity.getString(R.string.currency));

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

    public void getCheckoutId() {
        if (money > 0) {
            Bundle bundle = new Bundle();
            bundle.putDouble("money", money);
            bundle.putInt("type", 2);
            IntentClass.goToActivity(activity, PaymentGateActivity.class, bundle);
        } else
            Utilities.toastySuccess(activity, activity.getString(R.string.no_money_to_pay));
    }

    public void back() {
        activity.finish();
    }
}