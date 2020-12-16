package com.uriallab.haat.haat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.core.app.ActivityCompat;
import androidx.databinding.ObservableBoolean;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.oppwa.mobile.connect.exception.PaymentException;
import com.oppwa.mobile.connect.payment.PaymentParams;
import com.oppwa.mobile.connect.payment.card.CardPaymentParams;
import com.oppwa.mobile.connect.provider.Transaction;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.ComplainReason;
import com.uriallab.haat.haat.Interfaces.DeleteImage;
import com.uriallab.haat.haat.UI.Adapters.ImagesAdapter;
import com.uriallab.haat.haat.UI.MainActivity;
import com.uriallab.haat.haat.Utilities.Dialogs;
import com.uriallab.haat.haat.Utilities.LoadingDialog;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.Utilities.camera.Camera;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class PayBottomSheet extends BottomSheetDialogFragment{
    Transaction transaction = null;
    private String cardType = "STC_PAY";

    Boolean isSTCPAY = true;
    Boolean isVISA = false;
    Boolean isMADA = false;
    Boolean isMASTER = false;
    ImageView img_stc,img_stc1,img_visa,img_visa1,img_master,img_master1,img_mada,img_mada1,card_payment_type;
    RelativeLayout line_stc,line_visa,line_master,line_mada,relative_layout_click;
    private Activity activity;
    String phoneee,num_card,month_num,year_num,code_num,nameee,checkoutId;
    private EditText phone,card_num,month,year,secret_code,card_name;

    private int positionSpinner = 0;

    private RecyclerView recyclerAttachment;

    private String orderId, reportReason = "", img, userToID;
    LinearLayout second_lin,line_1;
    Double price;
    String id;
    int type;
    double money;
    int hatCardId ;
    int hatCardQuantity;

    private List<String> personTypeList = new ArrayList<>();
    private List<Integer> personTypeListId = new ArrayList<>();
    private ArrayAdapter<String> adapterPersonType;

    public PayBottomSheet() {
    }

    @SuppressLint("ValidFragment")
    public PayBottomSheet(Activity activity,Double price,String id,int type) {
        this.activity = activity;
        this.price=price;
        this.id=id;
        this.type=type;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(activity, R.layout.bottom_sheet_pay, null);
        dialog.setContentView(contentView);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog d = (BottomSheetDialog) dialog;

                FrameLayout bottomSheet = (FrameLayout) d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        relative_layout_click = dialog.findViewById(R.id.relative_layout_click);
        img_stc = dialog.findViewById(R.id.img_stc);
        img_master = dialog.findViewById(R.id.img_master);
        img_visa = dialog.findViewById(R.id.img_visa);
        img_mada = dialog.findViewById(R.id.img_mada);
        img_stc1 = dialog.findViewById(R.id.img_stc1);
        img_master1 = dialog.findViewById(R.id.img_master1);
        img_visa1 = dialog.findViewById(R.id.img_visa1);
        img_mada1 = dialog.findViewById(R.id.img_mada1);
        line_stc = dialog.findViewById(R.id.line_stc);
        line_visa = dialog.findViewById(R.id.line_visa);
        line_master = dialog.findViewById(R.id.line_master);
        line_mada = dialog.findViewById(R.id.line_mada);
        line_1 = dialog.findViewById(R.id.phone_qr_lin);
        second_lin = dialog.findViewById(R.id.second_lin);
        card_payment_type = dialog.findViewById(R.id.card_payment_type);
        phone = dialog.findViewById(R.id.phone);
        month = dialog.findViewById(R.id.expiry_month);
        year = dialog.findViewById(R.id.expiry_year);
        secret_code = dialog.findViewById(R.id.cvv);
        card_name = dialog.findViewById(R.id.holder_name);

        //

        img_stc.setVisibility(View.VISIBLE);
        img_mada.setVisibility(View.GONE);
        img_master.setVisibility(View.GONE);
        img_visa.setVisibility(View.GONE);
        img_stc1.setVisibility(View.VISIBLE);
        img_mada1.setVisibility(View.GONE);
        img_master1.setVisibility(View.GONE);
        img_visa1.setVisibility(View.GONE);
        line_1.setVisibility(View.VISIBLE);
        line_stc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.hideKeyboard(activity);

                isSTCPAY = true;
                isVISA=false;
                isMADA=false;
                isMASTER=false;
                img_stc.setVisibility(View.VISIBLE);
                img_mada.setVisibility(View.GONE);
                img_master.setVisibility(View.GONE);
                img_visa.setVisibility(View.GONE);
                img_stc1.setVisibility(View.VISIBLE);
                img_mada1.setVisibility(View.GONE);
                img_master1.setVisibility(View.GONE);
                img_visa1.setVisibility(View.GONE);
                second_lin.setVisibility(View.GONE);
                line_1.setVisibility(View.VISIBLE);
            }
        });

        line_visa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.hideKeyboard(activity);

                isSTCPAY = false;
                isVISA=true;
                isMADA=false;
                isMASTER=false;
                card_payment_type.setImageResource(R.drawable.visa);
                img_stc.setVisibility(View.GONE);
                img_mada.setVisibility(View.GONE);
                img_master.setVisibility(View.GONE);
                img_visa.setVisibility(View.VISIBLE);
                img_stc1.setVisibility(View.GONE);
                img_mada1.setVisibility(View.GONE);
                img_master1.setVisibility(View.GONE);
                img_visa1.setVisibility(View.VISIBLE);
                second_lin.setVisibility(View.VISIBLE);
                line_1.setVisibility(View.GONE);

            }
        });

        line_master.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.hideKeyboard(activity);

                isSTCPAY = false;
                isVISA=false;
                isMADA=false;
                isMASTER=true;
                card_payment_type.setImageResource(R.drawable.master);
                img_stc.setVisibility(View.GONE);
                img_mada.setVisibility(View.GONE);
                img_master.setVisibility(View.VISIBLE);
                img_visa.setVisibility(View.GONE);
                img_stc1.setVisibility(View.GONE);
                img_mada1.setVisibility(View.GONE);
                img_master1.setVisibility(View.VISIBLE);
                img_visa1.setVisibility(View.GONE);
                second_lin.setVisibility(View.VISIBLE);
                line_1.setVisibility(View.GONE);


            }
        });

        line_mada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.hideKeyboard(activity);

                isSTCPAY = false;
                isVISA=false;
                isMADA=true;
                isMASTER=false;
                card_payment_type.setImageResource(R.drawable.mada);
                img_stc.setVisibility(View.GONE);
                img_mada.setVisibility(View.VISIBLE);
                img_master.setVisibility(View.GONE);
                img_visa.setVisibility(View.GONE);
                img_stc1.setVisibility(View.GONE);
                img_mada1.setVisibility(View.VISIBLE);
                img_master1.setVisibility(View.GONE);
                img_visa1.setVisibility(View.GONE);
                second_lin.setVisibility(View.VISIBLE);
                line_1.setVisibility(View.GONE);

            }
        });

        relative_layout_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.hideKeyboard(activity);
                if (isSTCPAY==true){
                    phoneee = phone.getText().toString();
                    if (phoneee.isEmpty()){
                        Utilities.toastyError(activity, activity.getString(R.string.enter_phone_number));
                    }else {
                        createCheckoutId(true);
                    }
                }else {
                    num_card = card_num.getText().toString();
                    year_num = year.getText().toString();
                    month_num=month.getText().toString();
                    code_num = secret_code.getText().toString();
                    nameee = card_name.getText().toString();
                    if (num_card.isEmpty() || year_num.isEmpty() || month_num.isEmpty() || code_num.isEmpty() || nameee.isEmpty()){
                        Utilities.toastyError(activity, activity.getString(R.string.complete_data));
                    }else {
                        createCheckoutId(false);
                    }
                }
            }
        });
    }


    public void createCheckoutId(final boolean isSTC) {
        final LoadingDialog loadingDialog = new LoadingDialog();

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("amount", Utilities.roundPrice(money));
            jsonParams.put("orderUID", orderId);
            jsonParams.put("card_id", hatCardId);
            jsonParams.put("card_quantity", hatCardQuantity);
            if (isSTC) {
                    jsonParams.put("mode", "mobile");
                    jsonParams.put("mobile", phoneee);
            } else
                jsonParams.put("method_type", type);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIModel.postMethod(activity, "Payment/Checkout", jsonParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "Error");
                switch (statusCode) {
                    case 400:
                        try {
                            JSONObject jsonObject = new JSONObject(responseString);
                            if (jsonObject.getJSONObject("error").has("Message"))
                                Utilities.toastyError(activity, jsonObject.getJSONObject("error").getString("Message"));
                            else
                                Utilities.toastyError(activity, responseString + "    ");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 500:
                        try {
                            JSONObject jsonObject = new JSONObject(responseString);
                            if (jsonObject.getJSONObject("error").has("Message"))
                                Utilities.toastyError(activity, jsonObject.getJSONObject("error").getString("Message"));
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
                                createCheckoutId(isSTC);
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("response", responseString);
                try {
                    JSONObject myJson = new JSONObject(responseString);
                    checkoutId = myJson.getJSONObject("result").getString("message");
                    PaymentParams paymentParams = null;
                    try {
                        if (isSTC) {
                            paymentParams = new PaymentParams(
                                    checkoutId,
                                    "STC_PAY"
                            );
                            Log.e("paymentParams", checkoutId + "\n" + "STC_PAY");
                        } else {
                            String expireYear = "20" + year_num;
                            String cardFinalNumber = num_card.replace("-", "");
                            Log.e("CARD_NUMBER", cardFinalNumber + "\t" + num_card);
                            paymentParams = new CardPaymentParams(
                                    checkoutId,
                                    cardType,
                                    cardFinalNumber,
                                    nameee,
                                    month_num,
                                    year_num,
                                    code_num
                            );
                            Log.e("paymentParams", checkoutId + "\n" + type + "\t" + cardFinalNumber + "\n" + nameee + "\t" + month_num + "\t" + expireYear);
                        }

                        // Set shopper result URL
                        paymentParams.setShopperResultUrl("haat://result");

                    } catch (PaymentException e) {
                        e.printStackTrace();
                    }

                    try {
                        transaction = new Transaction(paymentParams);
                        //activity.binder.submitTransaction(transaction);
                    } catch (PaymentException ee) {
                        /* error occurred */
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    public void checkPayStatus() {
        final LoadingDialog loadingDialog = new LoadingDialog();
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("id", checkoutId);
            if (type != 2) {
                jsonParams.put("orderUID", orderId);
                jsonParams.put("method_type", cardType);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "Payment/OrderStatus";
        if (type == 2) {
            try {
                jsonParams.put("amount", Utilities.roundPrice(money));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            url = "Payment/CreditStatus";
        } else if (type == 3) {
            url = "Payment/HatCardStatus";
            try {
                jsonParams.put("card_id", hatCardId);
                jsonParams.put("card_quantity", hatCardQuantity);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        APIModel.postMethod(activity, url, jsonParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("response", responseString + "\t" + statusCode + " Error");
                switch (statusCode) {
                    case 400:
                        try {
                            JSONObject jsonObject = new JSONObject(responseString);
                            if (jsonObject.getJSONObject("error").has("Message"))
                                Utilities.toastyError(activity, jsonObject.getJSONObject("error").getString("Message"));
                            else
                                Utilities.toastyError(activity, responseString + "    ");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 500:
                        try {
                            JSONObject jsonObject = new JSONObject(responseString);
                            if (jsonObject.getJSONObject("error").has("Message"))
                                Utilities.toastyError(activity, jsonObject.getJSONObject("error").getString("Message"));
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
                                checkPayStatus();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("response", responseString);
                try {
                    JSONObject myJson = new JSONObject(responseString);

                    Log.e("response", "true");
                    Utilities.toastySuccess(activity, myJson.getJSONObject("result").getString("Message"));

                    if (type == 3) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("key", "13");
                        activity.startActivity(intent);
                    } else {
                        Utilities.hideKeyboard(activity);
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("isPayed", true);
                        activity.setResult(Activity.RESULT_OK, returnIntent);
                        activity.finish();
                    }

                } catch (
                        JSONException e) {
                    e.printStackTrace();
                }
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

    public void choosePaymentMethod(int type) {
        if (type == 1) {
            cardType = "STC_PAY";
            isSTCPAY=(true);
            isMADA=(false);
            isMASTER=(false);
            isVISA=(false);
        }else if (type == 2) {
            cardType = "VISA";
            isSTCPAY=(false);
            isMADA=(false);
            isMASTER=(false);
            isVISA=(true);
            //activity.binding.cardPaymentType.setBackgroundResource(R.drawable.visa);

        } else if (type == 3){
            cardType = "MASTER";
            isSTCPAY=(false);
            isMADA=(false);
            isMASTER=(true);
            isVISA=(false);
            //activity.binding.cardPaymentType.setBackgroundResource(R.drawable.mastercard);
            //activity.binding.cardPaymentType.setBackgroundResource(R.drawable.mada);
            //activity.binding.paymentType2.setBackgroundResource(R.drawable.mada);
        }else if (type == 4){
            cardType = "MADA";
            isSTCPAY=(false);
            isMADA=(true);
            isMASTER=(false);
            isVISA=(false);
            //activity=cardPaymentType.setBackgroundResource(R.drawable.mada);
            //activity.binding.cardPaymentType.setBackgroundResource(R.drawable.mada);
            //activity.binding.paymentType2.setBackgroundResource(R.drawable.mada);
        }

    }

    public void chooseCardPaymentMethod(int type) {
        if (type == 1) {
            cardType = "VISA";
            //activity.binding.cardPaymentType.setBackgroundResource(R.drawable.visa);
            //activity.binding.paymentType2.setBackgroundResource(R.drawable.visa);
        } else if (type == 2) {
            cardType = "MASTER";
            //activity.binding.cardPaymentType.setBackgroundResource(R.drawable.mastercard);
            //activity.binding.paymentType2.setBackgroundResource(R.drawable.mastercard);
        } else {
            cardType = "MADA";
            //activity.binding.cardPaymentType.setBackgroundResource(R.drawable.mada);
            //activity.binding.paymentType2.setBackgroundResource(R.drawable.mada);
        }
    }


}