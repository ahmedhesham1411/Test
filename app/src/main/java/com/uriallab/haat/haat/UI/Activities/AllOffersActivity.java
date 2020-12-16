package com.uriallab.haat.haat.UI.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.bumptech.glide.Glide;
import com.uriallab.haat.haat.DataModels.OffersModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.UI.Adapters.OffersAdapter;
import com.uriallab.haat.haat.UI.Adapters.OffersAdapterPop;
import com.uriallab.haat.haat.Utilities.GlobalVariables;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.databinding.ActivityAllOffersBinding;
import com.uriallab.haat.haat.viewModels.AllOffersViewModel;

import java.util.Collections;
import java.util.List;

public class AllOffersActivity extends AppCompatActivity {

    public ActivityAllOffersBinding binding;

    private AllOffersViewModel viewModel;

    private boolean isNewOrder;

    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_offers);

        GlobalVariables.FINISH_ACTIVITY = false;

        Bundle bundle = getIntent().getBundleExtra("data");

        isNewOrder = bundle.getBoolean("isNew");

        orderId = bundle.getString("orderId");

        //Glide.with(this).load(R.drawable.new_order_sent).into(binding.loadingImg);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("RefreshOffers"));

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            viewModel.getOffers2();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        ConfigurationFile.setIsOffersActive(this, false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ConfigurationFile.setIsOffersActive(this, true);

        if (GlobalVariables.FINISH_ACTIVITY)
            finish();

        viewModel = new AllOffersViewModel(this, isNewOrder, orderId);

        binding.setOfferVM(viewModel);
    }

    public void initRecycler(List<OffersModel.ResultBean.OffersBean> itemsBeanList) {
        Collections.reverse(itemsBeanList);
        OffersAdapter reviewsAdapter = new OffersAdapter(this, itemsBeanList);
        binding.recyclerOffers.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerOffers.setAdapter(reviewsAdapter);
        Utilities.runAnimation(binding.recyclerOffers, 1);
    }


    public void initRecyclerPop(List<OffersModel.ResultBean.OffersBean> itemsBeanList) {

        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        binding.recyclerOffersPop.addOnScrollListener(new CenterScrollListener());

        OffersAdapterPop reviewsAdapter = new OffersAdapterPop(this, itemsBeanList);
        binding.recyclerOffersPop.smoothScrollToPosition(reviewsAdapter.getItemCount() - 2);
        layoutManager.setMaxVisibleItems(2);

        binding.recyclerOffersPop.setLayoutManager(layoutManager);
        binding.recyclerOffersPop.setAdapter(reviewsAdapter);
        //Utilities.runAnimation(binding.recyclerOffersPop, 1);
    }
}