package com.uriallab.haat.haat.UI.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.uriallab.haat.haat.DataModels.OrdersModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.UI.Adapters.OrdersAdapter;
import com.uriallab.haat.haat.databinding.FragmentActiveOrdersBinding;
import com.uriallab.haat.haat.databinding.FragmentNewOrdersBinding;
import com.uriallab.haat.haat.viewModels.ActiveOrdersViewModel;
import com.uriallab.haat.haat.viewModels.NewOrdersViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewOrdersFragment extends Fragment {

    private FragmentNewOrdersBinding binding;
    private List<OrdersModel.ResultBean.OrdersBean> ordersList = new ArrayList<>();

    private NewOrdersViewModel viewModel;

    public NewOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_orders, container, false);

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("RefreshNewOffer"));

        return binding.getRoot();
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            viewModel.getOrders2();
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mMessageReceiver);
        ConfigurationFile.setIsNewOfferActive(getActivity(), false);
    }

/*
    @Override
    public void onResume() {
        super.onResume();

        ConfigurationFile.setIsNewOfferActive(getActivity(), true);

        viewModel = new NewOrdersViewModel(this);

        binding.setActiveOrdersVM(viewModel);
    }

*/

    public void updateWaitingList(List<OrdersModel.ResultBean.OrdersBean> list) {
        ordersList.clear();
        ordersList.addAll(list);

        if (ordersList.size() > 0) {
            viewModel.isNoData.set(false);
            OrdersAdapter ordersAdapter = new OrdersAdapter(getActivity(), ordersList);
            binding.myOrdersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.myOrdersRecycler.setAdapter(ordersAdapter);
        } else
            viewModel.isNoData.set(true);
    }
}