package com.uriallab.haat.haat.UI.Activities.utilsAndAccountInfo;

import android.os.Bundle;

import com.uriallab.haat.haat.DataModels.CommentsModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Adapters.CommentsAdapter;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.databinding.ActivityCommentsBinding;
import com.uriallab.haat.haat.viewModels.CommentsViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

public class CommentsActivity extends AppCompatActivity {

    private ActivityCommentsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_comments);

        binding.setCommentsVM(new CommentsViewModel(this));
    }

    public void initRecycler(List<CommentsModel.ResultBean.CommentsBean> commentsModelList) {
        CommentsAdapter complaintAdapter = new CommentsAdapter(this, commentsModelList);
        binding.recyclerComments.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerComments.setAdapter(complaintAdapter);
        Utilities.runAnimation(binding.recyclerComments, 1);
    }
}