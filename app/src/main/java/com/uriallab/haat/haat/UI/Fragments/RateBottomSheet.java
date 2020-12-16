package com.uriallab.haat.haat.UI.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eugeneek.smilebar.SmileBar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.loopj.android.http.TextHttpResponseHandler;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.CheckModel;
import com.uriallab.haat.haat.DataModels.ProductsModel;
import com.uriallab.haat.haat.DataModels.StoreProductsModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.UI.Activities.Updates.PhotoViewActivity;
import com.uriallab.haat.haat.UI.Activities.makeOrder.StoreDetailsActivity;
import com.uriallab.haat.haat.UI.Adapters.RecyclerCheckAdapter;
import com.uriallab.haat.haat.Utilities.Dialogs;
import com.uriallab.haat.haat.Utilities.IntentClass;
import com.uriallab.haat.haat.Utilities.LoadingDialog;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.hathat.Rate_done;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RateBottomSheet extends BottomSheetDialogFragment {
    private Activity activity;
    TextView user_name;
    SmileBar starBar;
    EditText comment_edt;
    Button rate_btn,not_now_btn;
    CircleImageView userImg;
    String name;
    String img;
    String comment,userId;
    int rate;

    public RateBottomSheet(Activity activity,String name,String img,String userId) {
        this.activity = activity;
        this.name = name;
        this.img = img;
        this.userId = userId;
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(activity, R.layout.custom_alert_dialog_rate, null);
        dialog.setContentView(contentView);

        dialog.setOnShowListener(dialog1 -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog1;

            FrameLayout bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);


            //TextView ahmedtxt = d.findViewById(R.id.ahmedtxt);
            //ahmedtxt.setText(name);
            String aaa = img;
            TextView user_name = d.findViewById(R.id.user_name);
            final SmileBar starBar = d.findViewById(R.id.starBar);
            final EditText comment_edt = d.findViewById(R.id.comment_edt);
            Button rate_btn = d.findViewById(R.id.rate_btn);
            Button not_now_btn = d.findViewById(R.id.not_now_btn);
            userImg = d.findViewById(R.id.user_imgaa);

            if (ConfigurationFile.getCurrentLanguage(activity).equals("ar"))
                starBar.setRotationY(180);

            user_name.setText(name);

            String dd = APIModel.BASE_URL + img;
            Picasso.get().load(img).placeholder(R.drawable.profile).into(userImg);
            //Picasso.get().load(APIModel.BASE_URL + img).placeholder(R.drawable.profile).into(userImg);


            rate_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendRate(comment_edt.getText().toString(), starBar.getRating());
                }
            });

            not_now_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    activity.finish();

                }
            });
        });

/*        user_name.setText(name);
        Picasso.get().load(APIModel.BASE_URL + img).placeholder(R.drawable.profile).into(userImg);
        rate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRate(comment_edt.getText().toString(), starBar.getRating());
            }
        });

        not_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });
        */

        ///



        //dialog.show();
    }

    private void sendRate(final String comment, final int rate) {
        final LoadingDialog loadingDialog = new LoadingDialog();

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("Comment_Desc", comment);
            jsonParams.put("Comment_Rate", rate);
            jsonParams.put("Rate_To_UserID", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIModel.postMethod(activity, "Setting/PostComment", jsonParams, new TextHttpResponseHandler() {
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
                    default:
                        APIModel.handleFailure(activity, statusCode, responseString, new APIModel.RefreshTokenListener() {
                            @Override
                            public void onRefresh() {
                                sendRate(comment, rate);
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                //Utilities.toastySuccess(activity, activity.getString(R.string.rated_successfully));
                dismiss();
                activity.finish();
                Intent intent = new Intent(activity, Rate_done.class);
                activity.startActivity(intent);
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

}