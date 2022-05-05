package com.mig35.carousellayoutmanager;

import androidx.annotation.NonNull;
import android.view.View;

/**
 * Implementation of {@link CarouselLayoutManager.PostLayoutListener} that makes interesting scaling of items. <br />
 * We are trying to make items scaling quicker for closer items for center and slower for when they are far away.<br />
 * Tis implementation uses atan function for this purpose.
 */
public class CarouselZoomPostLayoutListener extends CarouselLayoutManager.PostLayoutListener {

    private final float mScaleMultiplier;

    public CarouselZoomPostLayoutListener() {
        this(0.17f);
    }

    public CarouselZoomPostLayoutListener(final float scaleMultiplier) {
        mScaleMultiplier = scaleMultiplier;
    }

    @Override
    public ItemTransformation transformChild(@NonNull final View child,
                                             final float itemPositionToCenterDiff,
                                             final int orientation, final int itemPosition) {
        final float scale = 1.0f - Math.abs(itemPositionToCenterDiff * 0.2f);
        // because scaling will make view smaller in its center, then we should move this item to the top or bottom to make it visible
        final float translateY;
        final float translateX;
        if (CarouselLayoutManager.VERTICAL == orientation) {
            final float translateYGeneral = child.getMeasuredHeight();
            /** 此处不再额外进行平移 */
            translateY = 0;
            translateX = 0;
        } else {
            final float translateXGeneral = child.getMeasuredWidth() * (1 - scale) / 2f;
            translateX = Math.signum(itemPositionToCenterDiff) * translateXGeneral;
            translateY = 0;
        }

        return new ItemTransformation(scale, scale, translateX, translateY);
    }
}