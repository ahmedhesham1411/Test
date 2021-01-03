package com.uriallab.haat.haat.UI.Fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.uriallab.haat.haat.DataModels.NotificationsModel;
import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;
import com.uriallab.haat.haat.UI.Adapters.NotificationsAdapter;
import com.uriallab.haat.haat.UI.Adapters.SwipAdapter;
import com.uriallab.haat.haat.Utilities.Utilities;
import com.uriallab.haat.haat.databinding.FragmentNotificationBinding;
import com.uriallab.haat.haat.viewModels.HomeViewModel;
import com.uriallab.haat.haat.viewModels.NotificationsViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    private FragmentNotificationBinding binding;
    public List<NotificationsModel.ResultBean.NotficationsBean> notificationsList = new ArrayList<>();
    private NotificationsAdapter notificationsAdapter;
    public LinearLayout linearLayout;
    private NotificationsViewModel viewModel;

    public int nextPage = 1;
    private Paint p = new Paint();
    float newDx;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false);
        linearLayout=binding.notLinear;
        initRecycler();

        viewModel = new NotificationsViewModel(NotificationFragment.this);
        binding.setNotificationVM(viewModel);


        return binding.getRoot();
    }

    private void initRecycler() {
        notificationsAdapter = new NotificationsAdapter(getActivity(), notificationsList);
        binding.notificationRecycler.setAdapter(notificationsAdapter);

        final LinearLayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(getContext());
        binding.notificationRecycler.setLayoutManager(mLayoutManager);

/*
        binding.nestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null){
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight()))&&scrollY>oldScrollY){
                        try {
                            nextPage++;
                            if (nextPage <= viewModel.lastPage && !viewModel.isLoading) {
                                viewModel.getNotificationsRequest(nextPage);
                            }
                        }catch (Exception e){}
                    }
                }
            }
        });
*/


        binding.nestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                            scrollY > oldScrollY) {
                        //code to fetch more data for endless scrolling
                        try {
                            nextPage++;
                            if (nextPage <= viewModel.lastPage && !viewModel.isLoading) {
                                viewModel.getNotificationsRequest(nextPage);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });


        if (ConfigurationFile.getCurrentLanguage(getContext()).equals("ar")){
            initSwipe2("ar");
        }else if (ConfigurationFile.getCurrentLanguage(getContext()).equals("en")){
            initSwipe2("en");
        }


    }

    public void updateRecycler(List<NotificationsModel.ResultBean.NotficationsBean> notificationsModel) {
        if (viewModel.lastPage == 1)
            notificationsList.clear();
        notificationsList.addAll(notificationsModel);
        notificationsAdapter.notifyDataSetChanged();
    }


    public void updateRecycler1(List<NotificationsModel.ResultBean.NotficationsBean> notificationsModel) {
        if (viewModel.lastPage == 1)
            notificationsList.clear();
        notificationsList.addAll(notificationsModel);
        notificationsAdapter.notifyDataSetChanged();
        //Utilities.runAnimation(binding.notificationRecycler, 1);
    }
    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START | ItemTouchHelper.END) {

            @Override
            public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
                return 0.5f;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    notificationsAdapter.removeItem(position);
                    notificationsAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                } else {

                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 2;

                    if (dX > 0) {
                        p.setColor(Color.parseColor("#FFC43F"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_sweep_white_24dp);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);

                    }
                    else{

                    }
                }


                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(binding.notificationRecycler);
    }

    private void initSwipe2(String lang){
        SwipAdapter swipeHelper = new SwipAdapter(getContext(),lang) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipAdapter.UnderlayButton(
                        getContext(),
                        "",
                        getResources().getDrawable(R.drawable.notification),
                        R.color.orange2,
                        new SwipAdapter.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                int aa = pos+1;
                                Toast.makeText(getContext(), "You clicked delete on item position " + aa, Toast.LENGTH_LONG).show();
                            }
                        }
                ));
            }

        };
        swipeHelper.attachToRecyclerView(binding.notificationRecycler);

    }
}