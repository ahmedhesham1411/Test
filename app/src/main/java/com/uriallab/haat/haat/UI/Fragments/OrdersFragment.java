package com.uriallab.haat.haat.UI.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.databinding.FragmentOrdersBinding;
import com.uriallab.haat.haat.viewModels.OrdersViewModel;

import static com.uriallab.haat.haat.Utilities.GlobalVariables.ACTIVE_ORDERS_FRAGMENT_ID;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment {

    OrdersViewModel ordersViewModel;
    public OrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentOrdersBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false);

        binding.setOrdersVM(new OrdersViewModel(getActivity(), getChildFragmentManager()));

/*        binding.activeOrders.setBackgroundResource(R.drawable.shape_rounded_blue_corner_4);
        binding.activeOrders.setTextColor(getResources().getColor(R.color.colorWhite));*/

        return binding.getRoot();
    }

}