package com.uriallab.haat.haat.viewModels;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;

import com.uriallab.haat.haat.DataModels.OtherBranchesModel;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.UI.Activities.makeOrder.OtherBranchesActivity;

public class OtherBranchesViewModel {

    public ObservableInt rotate = new ObservableInt(0);

    public ObservableBoolean isNoData = new ObservableBoolean(false);
    public ObservableBoolean isList = new ObservableBoolean(true);

    private OtherBranchesActivity activity;

    private OtherBranchesModel storeDetailsModel;

    public OtherBranchesViewModel(OtherBranchesActivity activity, OtherBranchesModel storeDetailsModel) {
        this.activity = activity;
        this.storeDetailsModel = storeDetailsModel;

        if (ConfigurationFile.getCurrentLanguage(activity).equals("ar"))
            rotate.set(180);
    }

    public void isListClick(boolean isListClick){
        isList.set(isListClick);
    }

    /*public TextWatcher getSearchText() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (charSequence.length() == 0) {
                        isNoData.set(false);
                        activity.initRecycler(storeDetailsModel.getResults());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(final Editable editable) {

                List<GoogleStoresModel.ResultsBean> list = new ArrayList<>();
                try {
                    for (int i = 0; i < storeDetailsModel.getResults().size(); i++) {
                        if (storeDetailsModel.getResults().get(i).getName().contains(editable.toString())) {
                            list.add(storeDetailsModel.getResults().get(i));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (list.isEmpty()) {
                    isNoData.set(true);
                } else
                    activity.initRecycler(list);
            }
        };
    }*/

    public void back() {
        activity.onBackPressed();
    }
}