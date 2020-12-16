package com.uriallab.haat.haat.UI.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.eugeneek.smilebar.SmileBar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;
import com.uriallab.haat.haat.API.APIModel;
import com.uriallab.haat.haat.DataModels.StoreDetailsModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;

import de.hdodenhof.circleimageview.CircleImageView;

public class TimeBottomSheet extends BottomSheetDialogFragment {
    private Activity activity;
    StoreDetailsModel storeDetailsModel;

    public TimeBottomSheet(Activity activity, StoreDetailsModel storeDetailsModel) {
        this.activity = activity;
        this.storeDetailsModel = storeDetailsModel;
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(activity, R.layout.custom_alert_working_hours, null);
        dialog.setContentView(contentView);

        dialog.setOnShowListener(dialog1 -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog1;

            FrameLayout bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);

            TextView text_value = d.findViewById(R.id.text_value);
            StringBuilder weekdayHours = new StringBuilder();
            for (int i = 0; i < storeDetailsModel.getResult().getOpening_hours().getWeekday_text().size(); i++)
                weekdayHours.append(storeDetailsModel.getResult().getOpening_hours().getWeekday_text().get(i)).append("\n\n");
            text_value.setText(weekdayHours.toString());

        });
    }
}