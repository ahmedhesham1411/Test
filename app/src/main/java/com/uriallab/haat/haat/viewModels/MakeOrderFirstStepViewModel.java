package com.uriallab.haat.haat.viewModels;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.loopj.android.http.TextHttpResponseHandler;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.MakeOrderModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.UI.Activities.makeOrder.MakeOrderFirstStepActivity;
import com.uriallab.haat.haat.UI.Activities.makeOrder.MakeOrderStepTwoActivity;
import com.uriallab.haat.haat.Utilities.GlobalVariables;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.Utilities.camera.Camera;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MakeOrderFirstStepViewModel {

    public ObservableBoolean isCash = new ObservableBoolean(true);
    public ObservableBoolean isSTC = new ObservableBoolean(false);
    public ObservableBoolean isCreditCard = new ObservableBoolean(false);

    public ObservableBoolean isCoupon = new ObservableBoolean(false);

    public ObservableBoolean isValidCoupon = new ObservableBoolean(false);

    public ObservableBoolean isCheckingCoupon = new ObservableBoolean(false);

    public ObservableField<String> coupon = new ObservableField<>("");
    public ObservableField<String> details = new ObservableField<>("");
    public ObservableField<String> detailsError = new ObservableField<>();

    public ObservableInt rotate = new ObservableInt(0);

    private double lat, lng;

    private MakeOrderFirstStepActivity activity;

    private String storeName, shopImg;

    public List<String> listImg = new ArrayList<>();

    private boolean isService;

    public MakeOrderFirstStepViewModel(MakeOrderFirstStepActivity activity, double lat, double lng, String storeName, String shopImg, boolean isService) {
        this.activity = activity;
        this.lat = lat;
        this.lng = lng;
        this.storeName = storeName;
        this.shopImg = shopImg;
        this.isService = isService;

        if (ConfigurationFile.getCurrentLanguage(activity).equals("ar"))
            rotate.set(180);
    }

    public void nextStep() {
        Utilities.hideKeyboard(activity);
        detailsError.set(null);
        if (details.get().equals("")) {
            if (details.get().equals(""))
                detailsError.set(activity.getString(R.string.please_enter_order_details));
        } else {
            Intent intent = new Intent(activity, MakeOrderStepTwoActivity.class);
            //GlobalVariables.makeOrderModel = new MakeOrderModel();
            GlobalVariables.makeOrderModel.setImages(listImg);
            if (isValidCoupon.get())
                GlobalVariables.makeOrderModel.setCoupon(coupon.get());
            else
                GlobalVariables.makeOrderModel.setCoupon("");
            if (!isService) {
                GlobalVariables.makeOrderModel.setService(false);
                GlobalVariables.makeOrderModel.setShopImg(shopImg);
                GlobalVariables.makeOrderModel.setStoreName(storeName);
                GlobalVariables.makeOrderModel.setLat(lat);
                GlobalVariables.makeOrderModel.setLng(lng);
            } else
                GlobalVariables.makeOrderModel.setService(true);

            String detailsTxt = "";
            if (!activity.selectedProducts.get().equals(""))
                detailsTxt = activity.selectedProducts.get() + "\n" + details.get();
            else
                detailsTxt = details.get();

            GlobalVariables.makeOrderModel.setDetails(detailsTxt);
            activity.startActivity(intent);
        }
    }

    public void checkCoupon() {
        Utilities.hideKeyboard(activity);

        if (!coupon.get().equals(""))
            checkCouponRequest();
        else
            Utilities.toastyRequiredFieldCustom(activity, activity.getString(R.string.coupon));
    }

    public void addCoupon() {
        isCoupon.set(true);
    }

    private void checkCouponRequest() {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("Coupon_code", coupon.get());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        APIModel.postMethod(activity, "Order/CheckCoupon", jsonParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    case 400:
                        try {
                            JSONObject jsonObject = new JSONObject(responseString);
                            if (jsonObject.has("error"))
                                Utilities.toastyError(activity, jsonObject.getJSONObject("error").getString("Message"));
                            else
                                Utilities.toastyError(activity, responseString + "    ");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                checkCouponRequest();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    if (jsonObject.getJSONObject("result").has("cobonStatus")) {
                        if (jsonObject.getJSONObject("result").getBoolean("cobonStatus")) {
                            isValidCoupon.set(true);
                            Utilities.toastySuccess(activity, activity.getString(R.string.valid_coupon));
                        } else {
                            coupon.set("");
                            Utilities.toastyError(activity, activity.getString(R.string.invalid_coupon));
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStart() {
                super.onStart();
                isCheckingCoupon.set(true);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                isCheckingCoupon.set(false);
            }
        });
    }

    public void getPhoto() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(activity,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(activity,
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(activity,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, 3);
            } else {
                Camera.showGalleryMultiple(activity);
            }
        } else {
            Camera.showGalleryMultiple(activity);
        }
    }

    public void back() {
        activity.finish();
    }

    public void cashClick(){
        isCash.set(true);
        isCreditCard.set(false);
        isSTC.set(false);
    }
    public void stcClick(){
        isCash.set(false);
        isCreditCard.set(false);
        isSTC.set(true);

    }
    public void creditClick(){
        isCash.set(false);
        isCreditCard.set(true);
        isSTC.set(false);

    }
}