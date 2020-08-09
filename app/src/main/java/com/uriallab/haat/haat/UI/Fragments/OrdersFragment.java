package com.uriallab.haat.haat.UI.Fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.databinding.FragmentOrdersBinding;
import com.uriallab.haat.haat.viewModels.OrdersViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment {

    public OrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentOrdersBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false);

        binding.setOrdersVM(new OrdersViewModel(getActivity(), getChildFragmentManager()));

        return binding.getRoot();
    }
}