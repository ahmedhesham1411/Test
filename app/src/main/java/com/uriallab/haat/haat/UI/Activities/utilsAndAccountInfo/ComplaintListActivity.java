package com.uriallab.haat.haat.UI.Activities.utilsAndAccountInfo;

import android.os.Bundle;

import com.uriallab.haat.haat.DataModels.ComplaintModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Adapters.ComplaintAdapter;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.databinding.ActivityComplaintListBinding;
import com.uriallab.haat.haat.viewModels.ComplaintListViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

public class ComplaintListActivity extends AppCompatActivity {

    private ActivityComplaintListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_complaint_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.setComplaintVM(new ComplaintListViewModel(this));
    }

    public void initRecycler(List<ComplaintModel.ResultBean.ComplaintsBean> complaintList) {
        ComplaintAdapter complaintAdapter = new ComplaintAdapter(this, complaintList);
        binding.recyclerComplaint.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerComplaint.setAdapter(complaintAdapter);
        Utilities.runAnimation(binding.recyclerComplaint, 1);
    }
}