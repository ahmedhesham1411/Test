package com.uriallab.haat.haat.UI.Fragments;


import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.uriallab.haat.haat.DataModels.CategoryModel;
import com.uriallab.haat.haat.DataModels.ServerStoresModel;
import com.uriallab.haat.haat.Interfaces.CategoryClick;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Activities.Updates.StoresActivity;
import com.uriallab.haat.haat.UI.Adapters.CategoryAdapter;
import com.uriallab.haat.haat.UI.Adapters.FamousPlacesAdapter;
import com.uriallab.haat.haat.Utilities.IntentClass;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.databinding.FragmentHomeBinding;
import com.uriallab.haat.haat.viewModels.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements CategoryClick {

    public FragmentHomeBinding binding;
    private List<ServerStoresModel.ResultEntity> storesList = new ArrayList<>();
    private FamousPlacesAdapter famousPlacesAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        binding.searchIcon.setImageResource(R.drawable.search);
        binding.searchIcon.setColorFilter(getResources().getColor(R.color.colorBlue), PorterDuff.Mode.SRC_ATOP);

        initRecycler();

        final HomeViewModel viewModel = new HomeViewModel(this);

        binding.setHomeVM(viewModel);

        binding.edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                Utilities.hideKeyboard(getActivity());
                handled = true;
                Bundle bundle = new Bundle();
                bundle.putString("type", "");
                bundle.putString("category", "");
                bundle.putString("searchTxt", binding.edtSearch.getText().toString());
                bundle.putBoolean("isSearch", true);
                IntentClass.goToActivity(getActivity(), StoresActivity.class, bundle);
                binding.edtSearch.setText("");
            }
            return handled;
        });

        return binding.getRoot();
    }

    public void initCategoryRecycler(List<CategoryModel.ResultBean.CategoryBean> list) {
        CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(), list, this);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        binding.categoryRecycler.setLayoutManager(layoutManager);
        binding.categoryRecycler.setAdapter(categoryAdapter);
        Utilities.runAnimation(binding.categoryRecycler, 2);
    }

    private void initRecycler() {
        famousPlacesAdapter = new FamousPlacesAdapter(getActivity(), storesList);
        binding.storesRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.storesRecycler.setAdapter(famousPlacesAdapter);
    }

    public void updateRecycler(List<ServerStoresModel.ResultEntity> storesModel) {
        storesList.clear();
        storesList.addAll(storesModel);
        famousPlacesAdapter.notifyDataSetChanged();
    }

    @Override
    public void categoryClick(String type, String category) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("category", category);
        bundle.putString("searchTxt", "");
        bundle.putBoolean("isSearch", false);
        IntentClass.goToActivity(getActivity(), StoresActivity.class, bundle);
    }

}
