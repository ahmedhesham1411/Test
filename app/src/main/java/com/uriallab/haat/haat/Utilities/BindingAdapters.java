package com.uriallab.haat.haat.Utilities;


import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.GravityCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


/**
 * Created by MAHMOUD on 12/12/2018.
 */

public class BindingAdapters {
    @BindingAdapter(value = {"bind:imageUrl", "bind:defaultImage"}, requireAll = false)
    public static void loadImage(ImageView imageView, String url, Integer defaultImage) {
        if (defaultImage == null) defaultImage = -1;
        Utilities.downLoadImage(imageView, url, defaultImage);
    }

    @BindingAdapter("bind:imageBitmap")
    public static void loadImage(ImageView iv, Bitmap bitmap) {
        iv.setImageBitmap(bitmap);
    }

    @BindingAdapter("bind:imageSrc")
    public static void loadImage(ImageView iv, Drawable drawable) {
        iv.setImageDrawable(drawable);
    }

    @BindingAdapter("bind:imageResource")
    public static void loadImage(ImageView iv, int drawable) {
        iv.setImageResource(drawable);
    }

    @BindingAdapter("bind:draw")
    public static void opendrawable(DrawerLayout drawerLayout, String url) {
        if (url.equals("0")) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (url.equals("1")) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @BindingAdapter("bind:backgroundResource")
    public static void backgroundResource(View view, int resource) {
        view.setBackgroundResource(resource);
    }

    @BindingAdapter("button:animate")
    public static void animateButton(final Button button, final boolean animate) {
        AnimationDrawable animationDrawable = (AnimationDrawable) button.getBackground();
        if (animate) {
            button.setEnabled(false);
            animationDrawable.start();
        } else {
            animationDrawable.stop();
            button.setEnabled(true);
        }
    }

    @BindingAdapter({"setUpWithViewpager"})
    public static void setUpWithViewpager(final TabLayout tabLayout, ViewPager viewPager) {
        viewPager.addOnAdapterChangeListener(new ViewPager.OnAdapterChangeListener() {
            @Override
            public void onAdapterChanged(@NonNull ViewPager viewPager,
                                         @Nullable PagerAdapter oldAdapter, @Nullable PagerAdapter newAdapter) {
                if (oldAdapter == null && newAdapter == null) {
                    return;
                }
                Log.i("TAG", "onAdapterChanged");
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }

    @BindingAdapter("bind:topBottomOf")
    public static void topBottomOf(View view, int id) {
        ConstraintLayout constraintLayout = (ConstraintLayout) view.getParent();

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        constraintSet.connect(view.getId(), ConstraintSet.TOP, id, ConstraintSet.BOTTOM);
        constraintSet.applyTo(constraintLayout);
    }

    @BindingAdapter({"video"})
    public static void bindVideo(final VideoView videoView, String url) {
        if (!url.equals("")){
            videoView.setVideoURI(Uri.parse(url));
            videoView.seekTo(200);
        }
    }



    @BindingAdapter({"drawer:state"})
    public static void drawerState(final DrawerLayout drawerLayout, boolean open) {
        if (open) {
            drawerLayout.openDrawer(GravityCompat.START);
        } else {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @BindingAdapter(value = {"selectedValue", "selectedValueAttrChanged"}, requireAll = false)
    public static void bindSpinnerData(AppCompatSpinner pAppCompatSpinner, String newSelectedValue, final InverseBindingListener newTextAttrChanged) {
        pAppCompatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newTextAttrChanged.onChange();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (newSelectedValue != null) {
            int pos = ((ArrayAdapter<String>) pAppCompatSpinner.getAdapter()).getPosition(newSelectedValue);
            pAppCompatSpinner.setSelection(pos, true);
        }
    }
    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    public static String captureSelectedValue(AppCompatSpinner pAppCompatSpinner) {
        return (String) pAppCompatSpinner.getSelectedItem();
    }

    @BindingAdapter({"textview:paint"})
    public static void textPaint(TextView textView, int paint) {
        textView.setPaintFlags(textView.getPaintFlags() | paint);
    }

    @BindingAdapter("strikeThrough")
    public static void strikeThrough(TextView textView, Boolean strikeThrough) {
        if (strikeThrough) {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            textView.setPaintFlags(textView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}