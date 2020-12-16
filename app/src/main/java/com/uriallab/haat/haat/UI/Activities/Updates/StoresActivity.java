package com.uriallab.haat.haat.UI.Activities.Updates;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.uriallab.haat.haat.DataModels.GoogleStoresModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Adapters.StoresAdapter;
import com.uriallab.haat.haat.UI.Adapters.StoresAdapter_horizontal;
import com.uriallab.haat.haat.Utilities.Dialogs;
import com.uriallab.haat.haat.Utilities.IntentClass;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.databinding.ActivityStoresBinding;
import com.uriallab.haat.haat.viewModels.StoresViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StoresActivity extends AppCompatActivity {
    private Spinner spinner;

    public ActivityStoresBinding binding;

    public ObservableBoolean isForest = new ObservableBoolean(false);

    private StoresViewModel viewModel;

    private List<GoogleStoresModel.ResultsBean> storesList = new ArrayList<>();
    private List<GoogleStoresModel.ResultsBean> storesList_rev = new ArrayList<>();

    private StoresAdapter storesAdapter,StoresAdapterHorizontal;
    //private StoresAdapter storesAdapterHorizontal;

    public boolean isLoading = false;

    private LinearLayoutManager layoutManager;

    public String pageToken = "", catType = "";
    Boolean isVertical = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stores);

        spinner = (Spinner) this.findViewById(R.id.spinner1);
        List<String> list = new ArrayList<>();
        list.add(getResources().getString(R.string.nearby));
        list.add(getResources().getString(R.string.nearby_not));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,list);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(StoresActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                if (position == 0){
                    //isForest.set(false);
                    if (isForest.get() == true){
                        isForest.set(false);
                        Collections.reverse(storesList);
                        storesAdapter = new StoresAdapter(StoresActivity.this, storesList,"ver");
                        StoresAdapterHorizontal = new StoresAdapter(StoresActivity.this, storesList,"hor");
                        //storesAdapterHorizontal = new StoresAdapter(this,storesList,R.layout.item_store_horizontal);
                        layoutManager = new LinearLayoutManager(StoresActivity.this);
                        LinearLayoutManager linearLayoutManager1 = (new GridLayoutManager(StoresActivity.this,2));
                        //binding.storesRecycler.setLayoutManager(layoutManager);
                        //LinearLayoutManager linearLayoutManager1 = (new GridLayoutManager(this,2));
                        if (isVertical == true){
                            binding.storesRecycler.setLayoutManager(layoutManager);
                            binding.storesRecycler.setAdapter(storesAdapter);
                            binding.nounMenu.setImageResource(R.drawable.noun_menuactive);
                            binding.menu.setImageResource(R.drawable.menu);
                        }else if (isVertical == false){
                            StoresAdapterHorizontal = new StoresAdapter(StoresActivity.this, storesList,"hor");
                            binding.storesRecycler.setLayoutManager(linearLayoutManager1);
                            binding.storesRecycler.setAdapter(StoresAdapterHorizontal);
                            binding.nounMenu.setImageResource(R.drawable.noun_menu);
                            binding.menu.setImageResource(R.drawable.menuactive);
                            binding.storesRecycler.setHasFixedSize(true);
                           /* StoresAdapterHorizontal.notifyDataSetChanged();
                            binding.storesRecycler.notify();*/
                         /*   new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            binding.storesRecycler.setLayoutManager(linearLayoutManager1);
                                            binding.storesRecycler.setAdapter(StoresAdapterHorizontal);
                                            binding.nounMenu.setImageResource(R.drawable.noun_menu);
                                            binding.menu.setImageResource(R.drawable.menuactive);

                                            binding.storesRecycler.setHasFixedSize(true);
                                        }
                                    });
                                }
                            }).start();*/
                        }
                    }

                    else if (isForest.get() == false){
                    }
                }else if (position == 1){
                    //isForest.set(true);
                    if (isForest.get() == false){
                        isForest.set(true);

                        Collections.reverse(storesList);
                        storesAdapter = new StoresAdapter(StoresActivity.this, storesList,"ver");
                        StoresAdapterHorizontal = new StoresAdapter(StoresActivity.this, storesList,"hor");
                        //storesAdapterHorizontal = new StoresAdapter(this,storesList,R.layout.item_store_horizontal);
                        layoutManager = new LinearLayoutManager(StoresActivity.this);
                        LinearLayoutManager linearLayoutManager1 = (new GridLayoutManager(StoresActivity.this,2));
                        //binding.storesRecycler.setLayoutManager(layoutManager);
                        //LinearLayoutManager linearLayoutManager1 = (new GridLayoutManager(this,2));

                        if (isVertical == true){
                            binding.storesRecycler.setLayoutManager(layoutManager);
                            binding.storesRecycler.setAdapter(storesAdapter);
                            binding.nounMenu.setImageResource(R.drawable.noun_menuactive);
                            binding.menu.setImageResource(R.drawable.menu);
                        }
                        else if (isVertical == false){
                            binding.storesRecycler.setLayoutManager(linearLayoutManager1);
                            binding.storesRecycler.setAdapter(StoresAdapterHorizontal);
                            binding.nounMenu.setImageResource(R.drawable.noun_menu);
                            binding.menu.setImageResource(R.drawable.menuactive);
                        }
                    }else if (isForest.get() == true){}

                }
                //textView.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Bundle bundle = getIntent().getBundleExtra("data");

        initRecycler();

        boolean isSearch = bundle.getBoolean("isSearch");
        String searchTxt = bundle.getString("searchTxt");
        String type = bundle.getString("type");
        String category = bundle.getString("category");

        viewModel = new StoresViewModel(this, type, category, isSearch, searchTxt);

        binding.setStoresVM(viewModel);

        binding.edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                Utilities.hideKeyboard(StoresActivity.this);
                handled = true;
                viewModel.categoryName.set(binding.edtSearch.getText().toString());
               viewModel.getSearchResult(binding.edtSearch.getText().toString());
            }
            return handled;
        });
    }


    private void initRecycler() {
        storesAdapter = new StoresAdapter(this, storesList,"ver");
        StoresAdapterHorizontal = new StoresAdapter(this, storesList,"hor");
        //storesAdapterHorizontal = new StoresAdapter(this,storesList,R.layout.item_store_horizontal);
        layoutManager = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager1 = (new GridLayoutManager(this,2));
        //binding.storesRecycler.setLayoutManager(layoutManager);
        //LinearLayoutManager linearLayoutManager1 = (new GridLayoutManager(this,2));
        binding.storesRecycler.setLayoutManager(layoutManager);
        binding.storesRecycler.setAdapter(storesAdapter);
        binding.nounMenu.setImageResource(R.drawable.noun_menuactive);
        binding.menu.setImageResource(R.drawable.menu);
        isVertical = true;

        binding.nounMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVertical){
                }else {
                    binding.storesRecycler.setLayoutManager(layoutManager);
                    binding.storesRecycler.setAdapter(storesAdapter);
                    binding.nounMenu.setImageResource(R.drawable.noun_menuactive);
                    binding.menu.setImageResource(R.drawable.menu);
                    isVertical = true;
                }
            }
        });

        binding.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVertical) {
                    binding.storesRecycler.setLayoutManager(linearLayoutManager1);
                    binding.storesRecycler.setAdapter(StoresAdapterHorizontal);
                    binding.nounMenu.setImageResource(R.drawable.noun_menu);
                    binding.menu.setImageResource(R.drawable.menuactive);
                    isVertical = false;
                }
            }
        });


        binding.nestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                            scrollY > oldScrollY) {
                        //code to fetch more data for endless scrolling
                        try {
                            viewModel.GetStoryPaginate(catType, pageToken);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public void updateRecycler(List<GoogleStoresModel.ResultsBean> storesModel) {

        //new MyTask(this,storesModel).execute("my string parameter");

        storesList.clear();
        storesList.addAll(storesModel);
        storesAdapter.notifyDataSetChanged();
        //StoresAdapterHorizontal.notifyDataSetChanged();

    }

    public void updateRecyclerPaginate(List<GoogleStoresModel.ResultsBean> storesModel) {
        if (isForest.get()==true){
            Collections.reverse(storesList);
            if (isVertical){
                storesList.add((GoogleStoresModel.ResultsBean) storesModel);
                storesAdapter.notifyDataSetChanged();
            }else {
                storesList.addAll(storesModel);
                StoresAdapterHorizontal.notifyDataSetChanged();
            }
        }else {

            if (isVertical){
                storesList.addAll(storesModel);
                storesAdapter.notifyDataSetChanged();
            }else {
                storesList.addAll(storesModel);
                StoresAdapterHorizontal.notifyDataSetChanged();
            }
        }

       /* if (isVertical){
            storesList.addAll(storesModel);
            storesAdapter.notifyDataSetChanged();
        }else {
            storesList.addAll(storesModel);
            StoresAdapterHorizontal.notifyDataSetChanged();
        }*/


    }


    private class MyTask extends AsyncTask<String, Integer, String> {

        Activity context;
        List<GoogleStoresModel.ResultsBean> storesModel;
        public MyTask(Activity context,List<GoogleStoresModel.ResultsBean> storesModel){
            this.context=context;
            this.storesModel=storesModel;
        }
        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            // Do something like display a progress bar
        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {

            binding.storesRecycler.setLayoutManager(layoutManager);
            binding.storesRecycler.setAdapter(storesAdapter);
            binding.nounMenu.setImageResource(R.drawable.noun_menuactive);
            binding.menu.setImageResource(R.drawable.menu);
            isVertical = true;
            return "this string is passed to onPostExecute";
        }

        // This is called from background thread but runs in UI
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);


            //textView.setText("ahmeasdd");
            // Do things like update the progress bar
        }

        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Do things like hide the progress bar or change a TextView
        }
    }
}