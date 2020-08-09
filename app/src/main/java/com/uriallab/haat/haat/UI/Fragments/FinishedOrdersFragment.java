package com.uriallab.haat.haat.UI.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uriallab.haat.haat.DataModels.OrdersModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Adapters.FinishedOrderAdapter;
import com.uriallab.haat.haat.databinding.FragmentFinishedOrdersBinding;
import com.uriallab.haat.haat.viewModels.FinishedOrdersViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class FinishedOrdersFragment extends Fragment {

    private FragmentFinishedOrdersBinding binding;
    private List<OrdersModel.ResultBean.OrdersBean> ordersList = new ArrayList<>();

    private FinishedOrdersViewModel viewModel;

    public FinishedOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_finished_orders, container, false);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel = new FinishedOrdersViewModel(this);

        binding.setFinishedOrdersVM(viewModel);
    }

    public void updateFinishedList(List<OrdersModel.ResultBean.OrdersBean> list) {
        ordersList.clear();
        ordersList.addAll(list);

        if (ordersList.size() > 0) {
            viewModel.isNoData.set(false);
            FinishedOrderAdapter finishedOrderAdapter = new FinishedOrderAdapter(getActivity(), ordersList);
            binding.myOrdersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.myOrdersRecycler.setAdapter(finishedOrderAdapter);
        } else
            viewModel.isNoData.set(true);
    }
}