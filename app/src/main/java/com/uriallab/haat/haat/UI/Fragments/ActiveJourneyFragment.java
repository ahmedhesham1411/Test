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

import com.uriallab.haat.haat.DataModels.FinishedJourneyModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.UI.Adapters.FinishedJourneyAdapter;
import com.uriallab.haat.haat.databinding.FragmentActiveJourneyBinding;
import com.uriallab.haat.haat.viewModels.ActiveJourneyViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActiveJourneyFragment extends Fragment {

    public FragmentActiveJourneyBinding binding;
    private List<FinishedJourneyModel.ResultBean.OrdersBean> finishedJourneyList = new ArrayList<>();
    public FinishedJourneyAdapter finishedOrderAdapter;

    private ActiveJourneyViewModel viewModel;

    public ActiveJourneyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_active_journey, container, false);

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("RefreshActiveJourney"));

        return binding.getRoot();
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            viewModel.getAcceptedOrders2();
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mMessageReceiver);
        ConfigurationFile.setIsActiveJourney(getActivity(), false);
    }

    @Override
    public void onResume() {
        super.onResume();

        ConfigurationFile.setIsActiveJourney(getActivity(), true);

        viewModel = new ActiveJourneyViewModel(this);

        binding.setActiveJourneyVM(viewModel);
    }

    public void updateFinishedList(List<FinishedJourneyModel.ResultBean.OrdersBean> list) {
        finishedJourneyList.clear();
        finishedJourneyList.addAll(list);
        if (finishedJourneyList.size() > 0) {
            viewModel.isNoData.set(false);
            finishedOrderAdapter = new FinishedJourneyAdapter(getActivity(), finishedJourneyList);
            binding.journeyRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.journeyRecycler.setAdapter(finishedOrderAdapter);
        } else
            viewModel.isNoData.set(true);
    }
}