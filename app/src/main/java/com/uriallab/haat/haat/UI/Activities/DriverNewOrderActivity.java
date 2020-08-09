package com.uriallab.haat.haat.UI.Activities;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Adapters.ChatAdapter;
import com.uriallab.haat.haat.UI.Fragments.SendReportBottomSheet;
import com.uriallab.haat.haat.Utilities.GlobalVariables;
import com.uriallab.haat.haat.Utilities.RealPathUtil;
import com.uriallab.haat.haat.Utilities.camera.Camera;
import com.uriallab.haat.haat.databinding.ActivityDriverNewOrderBinding;
import com.uriallab.haat.haat.viewModels.DriverNewOrderViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import static com.uriallab.haat.haat.Utilities.camera.Camera.CAMERA_REQUEST;
import static com.uriallab.haat.haat.Utilities.camera.Camera.GALLERY_REQUEST;
import static com.uriallab.haat.haat.Utilities.camera.Camera.currentPhotoPath;

public class DriverNewOrderActivity extends AppCompatActivity {

    public ActivityDriverNewOrderBinding binding;

    private String orderId;

    private List<Bitmap> imageList = new ArrayList<>();

    private SendReportBottomSheet sendReportBottomSheet;

    private DriverNewOrderViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_driver_new_order);

        GlobalVariables.FINISH_ACTIVITY_3 = false;

        Bundle bundle = getIntent().getBundleExtra("data");

        orderId = bundle.getString("orderId");

        viewModel = new DriverNewOrderViewModel(this, orderId);

        binding.setChatVM(viewModel);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (GlobalVariables.FINISH_ACTIVITY_3)
            finish();
    }

    public void initRecycler(List<String> chatList) {
        ChatAdapter reviewsAdapter = new ChatAdapter(this, chatList, true);
        binding.recyclerChat.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerChat.setAdapter(reviewsAdapter);
        binding.recyclerChat.scrollToPosition(chatList.size() - 1);
    }

    public void sendReport() {
        sendReportBottomSheet = new SendReportBottomSheet(DriverNewOrderActivity.this, orderId, viewModel.clientId);
        sendReportBottomSheet.show(getSupportFragmentManager(), "tag");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {

                //imageList.clear();

                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        try {
                            Uri imageUri = clipData.getItemAt(i).getUri();

                            Bitmap bitmapImage;

                            try {
                                bitmapImage = Camera.resizeBitmap(this, BitmapFactory.decodeFile(RealPathUtil.getRealPath(this, imageUri)));
                            } catch (Exception e) {
                                bitmapImage = Camera.resizeBitmap(this, BitmapFactory.decodeFile(Camera.getRealPathFromURI(this, imageUri)));
                                e.printStackTrace();
                            }

                            imageList.add(bitmapImage);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    sendReportBottomSheet.initRecyclerLocalImg(imageList);
                } else {
                    try {
                        Uri imageUri = data.getData();

                        Bitmap bitmapImage;

                        try {
                            bitmapImage = Camera.resizeBitmap(this, BitmapFactory.decodeFile(RealPathUtil.getRealPath(this, imageUri)));
                        } catch (Exception e) {
                            bitmapImage = Camera.resizeBitmap(this, BitmapFactory.decodeFile(Camera.getRealPathFromURI(this, imageUri)));
                            e.printStackTrace();
                        }

                        Log.e("Base64", Camera.convertBitmapToBase64(bitmapImage));
                        imageList.add(bitmapImage);

                        sendReportBottomSheet.initRecyclerLocalImg(imageList);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
                try {

                    Bitmap bitmapImage;

                    try {
                        bitmapImage = Camera.resizeBitmap(this, BitmapFactory.decodeFile(RealPathUtil.getRealPath(this, Uri.parse(currentPhotoPath))));
                    } catch (Exception e) {
                        bitmapImage = Camera.resizeBitmap(this, BitmapFactory.decodeFile(Camera.getRealPathFromURI(this, Uri.parse(currentPhotoPath))));
                        e.printStackTrace();
                    }

                    imageList.add(bitmapImage);

                    sendReportBottomSheet.initRecyclerLocalImg(imageList);

                } catch (Exception e) {
                    Log.e("IMAGE", "CAMERA_REQUEST Exception: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}