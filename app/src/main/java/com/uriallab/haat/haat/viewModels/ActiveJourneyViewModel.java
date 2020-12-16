package com.uriallab.haat.haat.viewModels;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.FinishedJourneyModel;
import com.uriallab.haat.haat.UI.Fragments.ActiveJourneyFragment;
import com.uriallab.haat.haat.Utilities.Dialogs;
import com.uriallab.haat.haat.Utilities.LoadingDialog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ObservableBoolean;

public class ActiveJourneyViewModel {

    public ObservableBoolean isNoData = new ObservableBoolean(false);

    private List<FinishedJourneyModel.ResultBean.OrdersBean> orderFinishedList = new ArrayList<>();

    private Activity activity;
    private ActiveJourneyFragment fragment;

    public ActiveJourneyViewModel(ActiveJourneyFragment fragment) {
        this.fragment = fragment;
        activity = fragment.getActivity();

        getAcceptedOrders();
    }

    private void getAcceptedOrders() {
        final LoadingDialog loadingDialog = new LoadingDialog();
        APIModel.getMethod(activity, "Driver/GetMyOrders", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                getAcceptedOrders();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);
                Dialogs.dismissLoading(loadingDialog);
                Type dataType = new TypeToken<FinishedJourneyModel>() {
                }.getType();
                FinishedJourneyModel data = new Gson().fromJson(responseString, dataType);

                orderFinishedList.clear();
                orderFinishedList.addAll(data.getResult().getOrders());
                fragment.updateFinishedList(orderFinishedList);

            }

            @Override
            public void onStart() {
                super.onStart();
                Dialogs.showLoading(activity, loadingDialog);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                //Dialogs.dismissLoading(loadingDialog);
            }
        });
    }

    public void getAcceptedOrders2() {
        APIModel.getMethod(activity, "Driver/GetMyOrders", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                getAcceptedOrders();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                Type dataType = new TypeToken<FinishedJourneyModel>() {
                }.getType();
                FinishedJourneyModel data = new Gson().fromJson(responseString, dataType);

                orderFinishedList.clear();
                orderFinishedList.addAll(data.getResult().getOrders());
                fragment.updateFinishedList(orderFinishedList);

            }

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }
}