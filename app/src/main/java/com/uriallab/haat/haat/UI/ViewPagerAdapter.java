package com.uriallab.haat.haat.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.uriallab.haat.haat.R;
import com.uriallab.haat.haat.UI.Fragments.HomeFragment;
import com.uriallab.haat.haat.UI.Fragments.JourneyFragment;
import com.uriallab.haat.haat.UI.Fragments.MoreFragment;
import com.uriallab.haat.haat.UI.Fragments.NotificationFragment;
import com.uriallab.haat.haat.UI.Fragments.OrdersFragment;

import java.util.ArrayList;
import java.util.List;

import static com.uriallab.haat.haat.Utilities.GlobalVariables.JOURNEY_FRAGMENT_ID;
import static com.uriallab.haat.haat.Utilities.GlobalVariables.JOURNEY_FRAGMENT_TAG;
import static com.uriallab.haat.haat.Utilities.GlobalVariables.MAIN_FRAGMENT_ID;
import static com.uriallab.haat.haat.Utilities.GlobalVariables.MAIN_FRAGMENT_TAG;
import static com.uriallab.haat.haat.Utilities.GlobalVariables.MORE_FRAGMENT_ID;
import static com.uriallab.haat.haat.Utilities.GlobalVariables.MORE_FRAGMENT_TAG;
import static com.uriallab.haat.haat.Utilities.GlobalVariables.NOTIFICATIONS_FRAGMENT_ID;
import static com.uriallab.haat.haat.Utilities.GlobalVariables.NOTIFICATIONS_FRAGMENT_TAG;
import static com.uriallab.haat.haat.Utilities.GlobalVariables.ORDERS_FRAGMENT_ID;
import static com.uriallab.haat.haat.Utilities.GlobalVariables.ORDERS_FRAGMENT_TAG;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final String ARG_PARAM1 = "param1";
    private ArrayList<String> list;
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();


    public final void setList(ArrayList<String> list) {
        this.list = list;
    }

    public Fragment getItem(int position) {
        //ViewPagerAdapter.Child.Companion adapter = ViewPagerAdapter.Child.Companion;
        return mFragmentList.get(position);
    }


    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }


    public int getCount() {
        return mFragmentList.size();
    }

    public ViewPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }


}