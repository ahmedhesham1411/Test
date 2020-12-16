package com.uriallab.haat.haat.viewModels;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Fragments.ActiveOrdersFragment;
import com.uriallab.haat.haat.UI.Fragments.FinishedOrdersFragment;
import com.uriallab.haat.haat.UI.Fragments.NewOrdersFragment;
import com.uriallab.haat.haat.databinding.ActivityMainBinding;

import androidx.databinding.ObservableBoolean;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.uriallab.haat.haat.Utilities.GlobalVariables.ACTIVE_ORDERS_FRAGMENT_ID;
import static com.uriallab.haat.haat.Utilities.GlobalVariables.ACTIVE_ORDERS_FRAGMENT_TAG;
import static com.uriallab.haat.haat.Utilities.GlobalVariables.FINISHED_ORDERS_FRAGMENT_ID;
import static com.uriallab.haat.haat.Utilities.GlobalVariables.FINISHED_ORDERS_FRAGMENT_TAG;
import static com.uriallab.haat.haat.Utilities.GlobalVariables.NEW_ORDERS_FRAGMENT_ID;
import static com.uriallab.haat.haat.Utilities.GlobalVariables.NEW_ORDERS_FRAGMENT_TAG;

public class OrdersViewModel {

    public ObservableBoolean isFinished = new ObservableBoolean(false);
    public ObservableBoolean bbbbbb = new ObservableBoolean(false);
    public ObservableBoolean aaaaa = new ObservableBoolean(true);
    public ObservableBoolean ccccc = new ObservableBoolean(false);

    public Boolean a,bbb,c = false;
    //public ObservableBoolean isFinished1 = new ObservableBoolean(false);

    ActivityMainBinding activityMainBinding;

    TextView textView;
    private int id;
    private String selectedFragmentTag;

    private Activity activity;
    private FragmentManager fragmentManager;

    public OrdersViewModel(Activity activity, final FragmentManager fragmentManager) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;


        textView = activity.findViewById(R.id.finished_orders);
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                selectedFragmentTag = fragmentManager.getFragments().get(fragmentManager.getFragments().size() - 1).getTag();
                Log.e("backstack fragmentTag", selectedFragmentTag);
                Bundle bundle = fragmentManager.getFragments().get(fragmentManager.getFragments().size() - 1).getArguments();
            }
        });

        pushFragment(ACTIVE_ORDERS_FRAGMENT_ID);
    }

    @SuppressLint("ResourceAsColor")
    public void orderTabClick(int type) {
        if (type == 1) {
            pushFragment(ACTIVE_ORDERS_FRAGMENT_ID);
            aaaaa.set(true);
            bbbbbb.set(false);
            ccccc.set(false);

            //isFinished.set(false);

        } else if (type == 2){
            bbbbbb.set(true);
            aaaaa.set(false);
            ccccc.set(false);
            pushFragment(FINISHED_ORDERS_FRAGMENT_ID);
            //isFinished.set(true);

        }
        else if (type == 3){
            pushFragment(NEW_ORDERS_FRAGMENT_ID);
            aaaaa.set(false);
            bbbbbb.set(false);
            ccccc.set(true);
            //isFinished.set(false);
        }

    }

    public void pushFragment(int fragmentId) {
        String tag;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        if (!isFinished.get())
//            fragmentTransaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_right_out,
//                    R.anim.slide_right_in, R.anim.slide_left_out);
//        else
//            fragmentTransaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
//                    R.anim.slide_left_in, R.anim.slide_right_out);

        Fragment fragment;

        switch (fragmentId) {
            case ACTIVE_ORDERS_FRAGMENT_ID:
                tag = ACTIVE_ORDERS_FRAGMENT_TAG;
                fragment = new ActiveOrdersFragment();


                int count = fragmentManager.getBackStackEntryCount();
                for (int i = 0; i < count; ++i)
                    fragmentManager.popBackStackImmediate();
                break;
            case FINISHED_ORDERS_FRAGMENT_ID:
                tag = FINISHED_ORDERS_FRAGMENT_TAG;
                fragment = new FinishedOrdersFragment();

                break;
            case NEW_ORDERS_FRAGMENT_ID:
                tag = NEW_ORDERS_FRAGMENT_TAG;
                fragment = new NewOrdersFragment();
                break;
            default:
                try {
                    throw new Exception("Invalid fragment id :: " + fragmentId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
        }

        selectedFragmentTag = tag;

        fragmentTransaction.replace(R.id.container, fragment, tag).commit();
    }

    public void back() {
        activity.onBackPressed();
    }

    public static Integer GetMainId(Context context){
        SharedPreferences pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        return pref.getInt("Main_id",1);
    }

    public int SetId(int a){
        id = a;
        return 0;
    }

    public void b_is_active(){
    }
}