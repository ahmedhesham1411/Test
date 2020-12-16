package com.uriallab.haat.haat.UI.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.Spinner;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.ComplainReason;
import com.uriallab.haat.haat.Interfaces.DeleteImage;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Adapters.ImagesAdapter;
import com.uriallab.haat.haat.Utilities.Dialogs;
import com.uriallab.haat.haat.Utilities.LoadingDialog;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.Utilities.camera.Camera;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Mahmoud on 26/02/2019.
 */

public class SendReportBottomSheet extends BottomSheetDialogFragment implements DeleteImage {

    private Activity activity;

    private EditText detailsEdt;

    private int positionSpinner = 0;

    private RecyclerView recyclerAttachment;

    private String orderId, reportReason = "", img, userToID;

    private List<String> personTypeList = new ArrayList<>();
    private List<Integer> personTypeListId = new ArrayList<>();
    private ArrayAdapter<String> adapterPersonType;

    public SendReportBottomSheet() {
    }

    @SuppressLint("ValidFragment")
    public SendReportBottomSheet(Activity activity, String orderId, String userToID) {
        this.activity = activity;
        this.orderId = orderId;
        this.userToID = userToID;

        getComplaintReason();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(activity, R.layout.bottom_sheet_send_report, null);
        dialog.setContentView(contentView);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog d = (BottomSheetDialog) dialog;

                FrameLayout bottomSheet = (FrameLayout) d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        Button sendComplaint = dialog.findViewById(R.id.send_complaint);
        Spinner complaintSpinner = dialog.findViewById(R.id.complaint_spinner);
        detailsEdt = dialog.findViewById(R.id.details_edt);
        ImageView addCoupon = dialog.findViewById(R.id.add_coupon);
        LinearLayout attachmentLin = dialog.findViewById(R.id.attachment_lin);



        attachmentLin.setOnClickListener(view -> {
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
                    Camera.showGalleryFromActivity(activity);
                }
            } else {
                Camera.showGalleryFromActivity(activity);
            }
        });

        recyclerAttachment = dialog.findViewById(R.id.recycler_attachment);

        addCoupon.setImageResource(R.drawable.coupon);
        addCoupon.setColorFilter(getResources().getColor(R.color.orange), PorterDuff.Mode.SRC_ATOP);

        sendComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (detailsEdt.getText().toString().isEmpty() || positionSpinner == 0) {
                    if (detailsEdt.getText().toString().isEmpty())
                        detailsEdt.setError(activity.getString(R.string.enter_complaint_details));

                    if (positionSpinner == 0)
                        Utilities.toastyRequiredFieldCustom(activity, activity.getString(R.string.enter_complaint_details));
                } else
                    sendReportRequest();
            }
        });


        personTypeList.add(getResources().getString(R.string.please_select_reason));
        personTypeListId.add(0);
        adapterPersonType = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, personTypeList);
        adapterPersonType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        complaintSpinner.setAdapter(adapterPersonType);

        complaintSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reportReason = parent.getItemAtPosition(position).toString();

                Log.e("reportReason", reportReason + " reason");
                positionSpinner = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void sendReportRequest() {
        final LoadingDialog loadingDialog = new LoadingDialog();
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("Complaint_Desc", detailsEdt.getText().toString());
            jsonParams.put("OrderID", orderId);
            jsonParams.put("User_To_ID", userToID);
            jsonParams.put("Complaint_Reason_ID", personTypeListId.get(positionSpinner));
            jsonParams.put("Complaint_ImgUrl", img);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIModel.postMethod(activity, "Setting/SetComplaint", jsonParams, new TextHttpResponseHandler() {
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
                    case 500:
                        try {
                            JSONObject jsonObject = new JSONObject(responseString);
                            if (jsonObject.has("error"))
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
                                sendReportRequest();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                Utilities.toastySuccess(activity, activity.getString(R.string.sent_successfully));

                dismiss();
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

    private void getComplaintReason() {
        final LoadingDialog loadingDialog = new LoadingDialog();
        APIModel.getMethod(activity, "Setting/GetComplaintReason", new TextHttpResponseHandler() {
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
                    case 500:
                        try {
                            JSONObject jsonObject = new JSONObject(responseString);
                            if (jsonObject.has("error"))
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
                                sendReportRequest();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                Log.e("response", responseString);

                Type dataType = new TypeToken<ComplainReason>() {
                }.getType();
                ComplainReason data = new Gson().fromJson(responseString, dataType);

                for (int i = 0; i < data.getResult().getComplaintreason().size(); i++) {
                    personTypeList.add(data.getResult().getComplaintreason().get(i).getReason_Nm());
                    personTypeListId.add(data.getResult().getComplaintreason().get(i).getComplaintReasonUID());
                }

                adapterPersonType.notifyDataSetChanged();
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

    public void initRecyclerLocalImg(List<Bitmap> imageList) {
        img = Camera.convertBitmapToBase64(imageList.get(0));
        ImagesAdapter imagesAdapter = new ImagesAdapter(activity, imageList, this);
        recyclerAttachment.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        recyclerAttachment.setAdapter(imagesAdapter);
    }

    @Override
    public void deleteImage(int pos) {
        img = "";
    }
}