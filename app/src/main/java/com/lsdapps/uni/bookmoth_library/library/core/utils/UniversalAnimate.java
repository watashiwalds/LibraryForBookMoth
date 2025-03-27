package com.lsdapps.uni.bookmoth_library.library.core.utils;

import android.animation.ObjectAnimator;
import android.view.ViewGroup;

public class UniversalAnimate {
    public static final int PLACEMENT_TOP = 0;
    public static final int PLACEMENT_END = 1;
    public static final int PLACEMENT_BOTTOM = 2;
    public static final int PLACEMENT_START = 3;
    public static void animateWallHiding(ViewGroup tb, int placement, boolean doHiding) {
        switch (placement) {
            case PLACEMENT_TOP:
                ObjectAnimator.ofFloat(tb, "translationY", doHiding ? -tb.getHeight() : 0).setDuration(200).start();
                break;
            case PLACEMENT_END:
                ObjectAnimator.ofFloat(tb, "translationX", doHiding ? tb.getWidth() : 0).setDuration(200).start();
                break;
            case PLACEMENT_BOTTOM:
                ObjectAnimator.ofFloat(tb, "translationY", doHiding ? tb.getHeight() : 0).setDuration(200).start();
                break;
            case PLACEMENT_START:
                ObjectAnimator.ofFloat(tb, "translationX", doHiding ? -tb.getWidth() : 0).setDuration(200).start();
                break;
        }
    }
}
