package com.uriallab.haat.haat.UI.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.ComplaintModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.UI.Activities.utilsAndAccountInfo.ComplaintDetailsActivity;
import com.uriallab.haat.haat.Utilities.Dialogs;
import com.uriallab.haat.haat.Utilities.LoadingDialog;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.databinding.ItemComplaintBinding;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Fade;
import androidx.transition.TransitionSet;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mahmoud on 4/19/2020.
 */

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.StoresViewHolder> {

    private Activity activity;
    private List<ComplaintModel.ResultBean.ComplaintsBean> incomingList;
    RecyclerView recyclerView;

    public ComplaintAdapter(Activity activity, List<ComplaintModel.ResultBean.ComplaintsBean> incomingList,RecyclerView recyclerView) {
        this.activity = activity;
        this.incomingList = incomingList;
        this.recyclerView=recyclerView;
    }

    @Override
    public StoresViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemComplaintBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_complaint, parent, false);
        return new StoresViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final StoresViewHolder holder, final int position) {

        Picasso.get().load(incomingList.get(position).getOrd_Shop_ImgUrl()).into(holder.binding.orderImg);

        holder.binding.storeName.setText(incomingList.get(position).getOrd_Shop_Nm()+"");
        holder.binding.complaintDetails.setText(incomingList.get(position).getComplaint_Desc()+"");
        holder.binding.complaintNumer.setText(incomingList.get(position).getComplaintUID()+"");

        if (incomingList.get(position).getComplaint_Status_ID() == 1) {
            holder.binding.complaintStatus.setText(activity.getString(R.string.waiting_processed));
            //holder.binding.complaintStatus.setTextColor(activity.getResources().getColor(R.color.colorMoov));
        } else {
            holder.binding.complaintStatus.setText(activity.getString(R.string.proessed));
            //holder.binding.complaintStatus.setTextColor(activity.getResources().getColor(R.color.colorBlue));
        }

        holder.binding.linee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.binding.clickToExpand.setOnClickListener(view -> {
            if (holder.binding.expanded.getVisibility()==View.GONE){

                holder.binding.expanded.setVisibility(View.VISIBLE);
                holder.binding.linee.setVisibility(View.GONE);
                holder.binding.line.setVisibility(View.GONE);
                AutoTransition autoTransition = new AutoTransition();
                //autoTransition.excludeChildren(recyclerView,true);
                TransitionManager.beginDelayedTransition(holder.binding.mainLay, autoTransition);

                holder.binding.userName.setText(incomingList.get(position).getFrom_Full_Name());
                Picasso.get().load(incomingList.get(position).getOrd_Shop_ImgUrl()).into(holder.binding.profileImgggg);
                holder.binding.notesId.setText(incomingList.get(position).getComplaint_Desc());
                holder.binding.complaintDetails.setText(incomingList.get(position).getComplaint_Desc());
                if (ConfigurationFile.getCurrentLanguage(activity).equals("ar"))
                    holder.binding.reasonId.setText(incomingList.get(position).getReason_Ar_Nm());
                else
                    holder.binding.reasonId.setText(incomingList.get(position).getReason_En_Nm());

                if (incomingList.get(position).getComplaint_Status_ID() == 1) {
                    holder.binding.aaaaa.setText(activity.getString(R.string.waiting_processed));
                    //holder.binding.complaintStatus.setTextColor(activity.getResources().getColor(R.color.colorMoov));
                } else {
                    holder.binding.aaaaa.setText(activity.getString(R.string.proessed));
                    //holder.binding.complaintStatus.setTextColor(activity.getResources().getColor(R.color.colorBlue));
                }

                if (incomingList.get(position).getComplaint_Status_ID()==2){
                    holder.binding.isss.setVisibility(View.VISIBLE);
                }else {
                    holder.binding.isss.setVisibility(View.GONE);
                }
                //arrow_details.setBackgroundResource(R.drawable.button_down);
            } else {
                //collapse(holder.binding.expanded);
                holder.binding.expanded.setVisibility(View.GONE);
                holder.binding.linee.setVisibility(View.VISIBLE);
                holder.binding.line.setVisibility(View.VISIBLE);


                holder.binding.rade.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final LoadingDialog loadingDialog = new LoadingDialog();

                        JSONObject jsonParams = new JSONObject();
                        try {
                            jsonParams.put("ComplaintUID", incomingList.get(position).getComplaintUID());
                            jsonParams.put("Client_Feadback_Status", 1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        APIModel.postMethod(activity, "Setting/ClientFeadbackComplaint", jsonParams, new TextHttpResponseHandler() {
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
                                                //complainAction(actionType);
                                            }
                                        });
                                        break;
                                }
                            }

                            @Override
                            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                                Log.e("response", responseString);
                                Utilities.toastySuccess(activity, activity.getString(R.string.sent_successfully));
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
                });
                holder.binding.mshRade.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final LoadingDialog loadingDialog = new LoadingDialog();

                        JSONObject jsonParams = new JSONObject();
                        try {
                            jsonParams.put("ComplaintUID", incomingList.get(position).getComplaintUID());
                            jsonParams.put("Client_Feadback_Status", 2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        APIModel.postMethod(activity, "Setting/ClientFeadbackComplaint", jsonParams, new TextHttpResponseHandler() {
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
                                                //complainAction(actionType);
                                            }
                                        });
                                        break;
                                }
                            }

                            @Override
                            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                                Log.e("response", responseString);
                                Utilities.toastySuccess(activity, activity.getString(R.string.sent_successfully));
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
                });

              /*  AutoTransition autoTransition = new AutoTransition();
                autoTransition.excludeChildren(recyclerView,true);
                TransitionManager.beginDelayedTransition(holder.binding.mainLay);
                holder.binding.expanded.setVisibility(View.GONE);
                //arrow_details.setBackgroundResource(R.drawable.button_down);*/
            }
        });
/*        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(activity, ComplaintDetailsActivity.class);
            Gson gson = new Gson();
            String myJson = gson.toJson(incomingList.get(position));
            intent.putExtra("myjson", myJson);
            activity.startActivity(intent);
        });*/
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Collapse speed of 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
    public static void expand(final View v) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Expansion speed of 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    @Override
    public int getItemCount() {
        return incomingList.size();
    }


    public class StoresViewHolder extends RecyclerView.ViewHolder {

        private ItemComplaintBinding binding;

        private StoresViewHolder(ItemComplaintBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}