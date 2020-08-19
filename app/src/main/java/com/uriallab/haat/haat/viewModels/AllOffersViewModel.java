package com.uriallab.haat.haat.viewModels;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.OffersModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.SharedPreferences.LoginSession;
import com.uriallab.haat.haat.UI.Activities.AllOffersActivity;
import com.uriallab.haat.haat.Utilities.Dialogs;
import com.uriallab.haat.haat.Utilities.IntentClass;
import com.uriallab.haat.haat.Utilities.LoadingDialog;
import com.uriallab.haat.haat.Utilities.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

public class AllOffersViewModel {

    public ObservableField<String> orderIdObservable = new ObservableField<>();

    public ObservableBoolean isNewObservable = new ObservableBoolean(false);

    public ObservableInt rotation = new ObservableInt(0);

    private String orderId;

    private AllOffersActivity activity;

    public AllOffersViewModel(AllOffersActivity activity, boolean isNewOrder, String orderId) {
        this.activity = activity;
        this.orderId = orderId;

        orderIdObservable.set(orderId);

        if (ConfigurationFile.getCurrentLanguage(activity).equals("ar"))
            rotation.set(180);

        if (isNewOrder)
            isNewObservable.set(true);
        else
            getOffers(orderId);
    }

    private void getOffers(final String orderId) {
        final LoadingDialog loadingDialog = new LoadingDialog();
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("OrderUID", orderId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        APIModel.postMethod(activity, "Client/GetOrderOffers", jsonParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                getOffers(orderId);
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                Type dataType = new TypeToken<OffersModel>() {
                }.getType();
                OffersModel data = new Gson().fromJson(responseString, dataType);

                if (data.getResult().getOffers().size() > 0)
                    activity.initRecycler(data.getResult().getOffers());

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

    public void getOffers2() {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("OrderUID", orderId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        APIModel.postMethod(activity, "Client/GetOrderOffers", jsonParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                getOffers(orderId);
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                Type dataType = new TypeToken<OffersModel>() {
                }.getType();
                OffersModel data = new Gson().fromJson(responseString, dataType);

                activity.initRecycler(data.getResult().getOffers());

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

    private void delOrder(final String orderId) {
        final LoadingDialog loadingDialog = new LoadingDialog();

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("OrderUID", orderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIModel.postMethod(activity, "Client/RemoveOrder", jsonParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                delOrder(orderId);
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                Utilities.toastySuccess(activity, activity.getString(R.string.deleted_successfully));

                activity.finish();
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

    public void cancelOrder() {
        delOrder(orderIdObservable.get());
    }

    public void back() {
        activity.finish();
    }

    private void deleteDialog(){
        Dialog dialogCall = new Dialog(activity);
        dialogCall.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCall.setContentView(R.layout.custom_alert_dialog_cancel_order);
        dialogCall.setCanceledOnTouchOutside(false);
        dialogCall.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView alert_text = dialogCall.findViewById(R.id.alert_text);
        TextView refuseClick = dialogCall.findViewById(R.id.no_id);
        TextView confirmClick = dialogCall.findViewById(R.id.yes_id);

        refuseClick.setText(activity.getString(R.string.cancel));
        confirmClick.setText(activity.getString(R.string.deleted));

        alert_text.setText(activity.getString(R.string.call_instructions));

        refuseClick.setOnClickListener(v -> dialogCall.dismiss());
        confirmClick.setOnClickListener(v -> {
            delOrder(orderIdObservable.get());
            dialogCall.dismiss();
        });
        dialogCall.show();
    }
}