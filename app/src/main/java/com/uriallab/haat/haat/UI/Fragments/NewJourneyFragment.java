package com.uriallab.haat.haat.UI.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uriallab.haat.haat.DataModels.JourneyModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.UI.Adapters.JourneyAdapter;
import com.uriallab.haat.haat.databinding.FragmentNewJourneyBinding;
import com.uriallab.haat.haat.viewModels.NewJourneyViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewJourneyFragment extends Fragment {

    public FragmentNewJourneyBinding binding;
    private List<JourneyModel.ResultBean.OrdersBean> journeyList = new ArrayList<>();
    public JourneyAdapter journeyAdapter;

    private NewJourneyViewModel viewModel;

    public NewJourneyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_journey, container, false);

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("RefreshNewJourney"));

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
        ConfigurationFile.setIsNewOrderActive(getActivity(), false);
    }

    @Override
    public void onResume() {
        super.onResume();

        ConfigurationFile.setIsNewOrderActive(getActivity(), true);

        viewModel = new NewJourneyViewModel(this);

        binding.setNewJourneyVM(viewModel);
    }

    public void updateWaitingList(List<JourneyModel.ResultBean.OrdersBean> list) {
        journeyList.clear();
        journeyList.addAll(list);

        if (journeyList.size() > 0) {
            viewModel.isNoData.set(false);
            journeyAdapter = new JourneyAdapter(getActivity(), journeyList);
            binding.journeyRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.journeyRecycler.setAdapter(journeyAdapter);
        } else
            viewModel.isNoData.set(true);
    }
}