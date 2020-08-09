package com.uriallab.haat.haat.UI.Activities.makeOrder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.uriallab.haat.haat.DataModels.ProductsModel;
import com.uriallab.haat.haat.DataModels.StoreDetailsModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.UI.Adapters.MenuImagesAdapter;
import com.uriallab.haat.haat.UI.Adapters.ProductsAdapter;
import com.uriallab.haat.haat.Utilities.GPSTracker;
import com.uriallab.haat.haat.Utilities.GlobalVariables;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.databinding.ActivityStoreDetailsBinding;
import com.uriallab.haat.haat.viewModels.StoreDetailsViewModel;

import java.util.List;

public class StoreDetailsActivity extends AppCompatActivity {

    public ActivityStoreDetailsBinding binding;
    private String placeId = "";

    private boolean isFromServer = false;

    private StoreDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_store_details);

        GlobalVariables.FINISH_ACTIVITY = false;

        arrowColor();

        if (ConfigurationFile.getCurrentLanguage(this).equals("ar")) {
            binding.backImg.setRotation(180);
            binding.starBar.setRotationY(180);
        }

        Bundle bundle = getIntent().getBundleExtra("data");

        try {
            placeId = bundle.getString("placeId");
        } catch (Exception e) {
            Intent intent = getIntent();
            Uri uri = intent.getData();
            String segments3 = uri.getPath();
            placeId = segments3.substring(segments3.lastIndexOf("/") + 1, segments3.lastIndexOf("$"));
        }

        try {
            isFromServer = bundle.getBoolean("isFromServer");
        }catch (Exception e){
            e.printStackTrace();
        }

        viewModel = new StoreDetailsViewModel(this, placeId, isFromServer);

        binding.setStoreDetailsVM(viewModel);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (GlobalVariables.FINISH_ACTIVITY)
            finish();
    }

    public void initMenuRecycler(List<StoreDetailsModel.ResultBean.PhotosBean> imagesList) {
        MenuImagesAdapter imagesAdapter = new MenuImagesAdapter(this, imagesList);
        binding.recyclerMenu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerMenu.setAdapter(imagesAdapter);
        Utilities.runAnimation(binding.recyclerMenu, 2);
    }

    public void initProductsRecycler(List<ProductsModel.ResultEntity.ProductsEntity> imagesList) {
        ProductsAdapter imagesAdapter = new ProductsAdapter(this, imagesList);
        binding.productsRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.productsRecycler.setAdapter(imagesAdapter);
        Utilities.runAnimation(binding.productsRecycler, 2);
    }

    private void arrowColor() {
        binding.rateArrow.setImageResource(R.drawable.arrow_left);
        binding.branchArrow.setImageResource(R.drawable.arrow_left);
        binding.timeArrow.setImageResource(R.drawable.arrow_left);

        binding.rateArrow.setColorFilter(getResources().getColor(R.color.colorTextHint), PorterDuff.Mode.SRC_ATOP);
        binding.branchArrow.setColorFilter(getResources().getColor(R.color.colorTextHint), PorterDuff.Mode.SRC_ATOP);
        binding.timeArrow.setColorFilter(getResources().getColor(R.color.colorTextHint), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 369) {
                if (!data.getExtras().getString("address").equals("none")) {

                    binding.storeLocation.setText(data.getExtras().getString("address"));

                    viewModel.lat = data.getExtras().getDouble("lat");
                    viewModel.lng = data.getExtras().getDouble("lng");


                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        GPSTracker gpsTracker = new GPSTracker(this);

                        double distance = Utilities.getKilometers(
                                gpsTracker.getLocation().getLatitude(),
                                gpsTracker.getLocation().getLongitude(),
                                data.getExtras().getDouble("lat"),
                                data.getExtras().getDouble("lng"));

                        binding.distanceFromYou.setText(getString(R.string.distance_from_you) + "  " + Utilities.roundPrice(distance) +
                                getString(R.string.km));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}