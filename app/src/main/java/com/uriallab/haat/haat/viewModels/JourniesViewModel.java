package com.uriallab.haat.haat.viewModels;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.IsActiveModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Fragments.ActiveJourneyFragment;
import com.uriallab.haat.haat.UI.Fragments.JourneyFragment;
import com.uriallab.haat.haat.UI.Fragments.NewJourneyFragment;
import com.uriallab.haat.haat.Utilities.Dialogs;
import com.uriallab.haat.haat.Utilities.LoadingDialog;
import com.uriallab.haat.haat.Utilities.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import androidx.databinding.ObservableBoolean;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.uriallab.haat.haat.Utilities.GlobalVariables.ACTIVE_JOURNEY_FRAGMENT_ID;
import static com.uriallab.haat.haat.Utilities.GlobalVariables.ACTIVE_ORDERS_FRAGMENT_TAG;
import static com.uriallab.haat.haat.Utilities.GlobalVariables.NEW_JOURNEY_FRAGMENT_ID;
import static com.uriallab.haat.haat.Utilities.GlobalVariables.NEW_JOURNEY_FRAGMENT_TAG;

public class JourniesViewModel {

    public ObservableBoolean isFinished = new ObservableBoolean(false);

    private String selectedFragmentTag;

    private JourneyFragment fragment;
    private Activity activity;
    private FragmentManager fragmentManager;

    public JourniesViewModel(JourneyFragment activity, final FragmentManager fragmentManager) {
        fragment = activity;
        this.activity = activity.getActivity();
        this.fragmentManager = fragmentManager;

        getIsActive();

        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                selectedFragmentTag = fragmentManager.getFragments().get(fragmentManager.getFragments().size() - 1).getTag();
                Log.e("backstack fragmentTag", selectedFragmentTag);
                Bundle bundle = fragmentManager.getFragments().get(fragmentManager.getFragments().size() - 1).getArguments();
            }
        });

        pushFragment(NEW_JOURNEY_FRAGMENT_ID);
    }

    public void orderTabClick(int type) {
        if (type == 1) {
            pushFragment(NEW_JOURNEY_FRAGMENT_ID);
            isFinished.set(false);
        } else {
            pushFragment(ACTIVE_JOURNEY_FRAGMENT_ID);
            isFinished.set(true);
        }
    }

    private void pushFragment(int fragmentId) {
        String tag;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment;

        switch (fragmentId) {
            case NEW_JOURNEY_FRAGMENT_ID:
                tag = NEW_JOURNEY_FRAGMENT_TAG;
                fragment = new NewJourneyFragment();

                int count = fragmentManager.getBackStackEntryCount();
                for (int i = 0; i < count; ++i)
                    fragmentManager.popBackStackImmediate();
                break;
            case ACTIVE_JOURNEY_FRAGMENT_ID:
                tag = ACTIVE_ORDERS_FRAGMENT_TAG;
                fragment = new ActiveJourneyFragment();
                break;
            default:
                try {
                    throw new Exception("Invalid fragment id :: " + fragmentId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
        }

        selectedFragmentTag = tag;

        fragmentTransaction.replace(R.id.container, fragment, tag).commit();
    }

    public void back() {
        activity.onBackPressed();
    }


    private void getIsActive() {
        final LoadingDialog loadingDialog = new LoadingDialog();
        APIModel.getMethod(activity, "Driver/GetIsActive", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                getIsActive();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                Type dataType = new TypeToken<IsActiveModel>() {
                }.getType();
                IsActiveModel data = new Gson().fromJson(responseString, dataType);

                if (data.getResult().isIsActive())
                    fragment.binding.switchView.setChecked(true);
                else
                    fragment.binding.switchView.setChecked(false);
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

    public void activeDriver(final boolean isActive) {
        final LoadingDialog loadingDialog = new LoadingDialog();

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("IsActive", isActive);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIModel.postMethod(activity, "Driver/PutIsActive", jsonParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    case 400:
                        try {
                            JSONObject jsonObject = new JSONObject(responseString);
                            if (jsonObject.has("error"))
                                Utilities.toastyError(activity, jsonObject.getJSONObject("error").getString("message"));
                            else
                                Utilities.toastyError(activity, responseString + "    ");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 500:
                        try {
                            JSONObject jsonObject = new JSONObject(responseString);
                            if (jsonObject.has("error"))
                                Utilities.toastyError(activity, jsonObject.getJSONObject("error").getString("message"));
                            else
                                Utilities.toastyError(activity, responseString + "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                activeDriver(isActive);
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                fragment.startService();
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
}