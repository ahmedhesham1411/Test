package com.uriallab.haat.haat;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import androidx.viewpager.widget.ViewPager;

import com.uriallab.haat.haat.UI.MainActivity;

import java.lang.reflect.Field;

public class PresentationViewPager2 extends ViewPager {

    private static final int DEFAULT_SCROLL_DURATION = 250;
    private static final int PRESENTATION_MODE_SCROLL_DURATION = 1000;

    public PresentationViewPager2(Context context) {
        super(context);
    }

    public PresentationViewPager2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public final void setDurationScroll(int millis) {
        try {
            Class viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            Context context = this.getContext();
            scroller.set(this, new PresentationViewPager2.OwnScroller(context, millis));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final class OwnScroller extends Scroller {

        private int durationScroll;

        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, durationScroll);
        }

        public final int getDurationScroll() {
            return durationScroll;
        }

        public final void setDurationScroll(int var) {
            durationScroll = var;
        }

        private OwnScroller(Context context, int durationScroll) {
            super(context, (new DecelerateInterpolator()));
            this.durationScroll = durationScroll;
        }
    }}
