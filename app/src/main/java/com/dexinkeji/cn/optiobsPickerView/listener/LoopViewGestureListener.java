package com.dexinkeji.cn.optiobsPickerView.listener;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.dexinkeji.cn.optiobsPickerView.view.WheelView;

/**
 * 手势监听
 */
public class LoopViewGestureListener extends GestureDetector.SimpleOnGestureListener {
    private final WheelView wheelView;


    public LoopViewGestureListener(WheelView wheelView) {
        this.wheelView = wheelView;
    }

    @Override
    public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        wheelView.scrollBy(velocityY);
        return true;
    }
}
