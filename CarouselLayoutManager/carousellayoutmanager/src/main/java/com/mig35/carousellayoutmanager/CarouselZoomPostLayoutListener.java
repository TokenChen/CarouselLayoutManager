package com.mig35.carousellayoutmanager;

import android.content.res.Configuration;
import android.util.Log;
import android.widget.ImageView;
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

            float translateXGeneral = 240;
            int screenOrientation = child.getContext().getResources().getConfiguration().orientation;
            Log.i("tag", "orientation test:" + screenOrientation);
            if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
                translateXGeneral = 135;
            }
            translateY = 0;

            float tempScale = Math.abs(itemPositionToCenterDiff * 0.2f) / 2;
            for (float i = Math.abs(itemPositionToCenterDiff) - 1; i > 0; i--) {
                tempScale += (i * 0.2f);
            }
            final float scaleXOffset = child.getMeasuredWidth() * tempScale * Math.signum(itemPositionToCenterDiff);
            translateX = translateXGeneral * (itemPositionToCenterDiff) - scaleXOffset + 20 * itemPositionToCenterDiff ;
            Log.i("zoom listener", "item pos to center diff:" + itemPositionToCenterDiff + "  item position:" + itemPosition
                    + " translateX" + translateX +" tempscal:" + tempScale + " scaleOffset:" + scaleXOffset);
        }

        return new ItemTransformation(scale, scale, translateX, translateY);
    }
}