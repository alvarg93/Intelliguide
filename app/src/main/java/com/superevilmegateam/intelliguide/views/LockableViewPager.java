package com.superevilmegateam.intelliguide.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * {@link ViewPager} subclass that allows to lock swipe motions.
 */
public class LockableViewPager extends ViewPager {
    private boolean swipe = true;

    public LockableViewPager(Context context) {
        super(context);
    }

    public LockableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean lock() {
        return swipe = false;
    }

    public void unlock() {
        this.swipe = true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {

        if (swipe) {
            return super.onInterceptTouchEvent(arg0);
        }

        // Never allow swiping to switch between pages
        return false;
    }

}
