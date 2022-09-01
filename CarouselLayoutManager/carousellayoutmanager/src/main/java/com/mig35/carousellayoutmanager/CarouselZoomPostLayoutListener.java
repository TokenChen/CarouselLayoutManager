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
    private final int mItemMargin;

    public CarouselZoomPostLayoutListener() {
        this(0.17f, 0);
    }

    public CarouselZoomPostLayoutListener(final float scaleMultiplier, final int itemMargin) {
        mScaleMultiplier = scaleMultiplier;
        mItemMargin = itemMargin;
    }

    @Override
    public ItemTransformation transformChild(@NonNull final View child,
                                             final float itemPositionToCenterDiff,
                                             final int orientation, final int itemPosition) {

        float tempScaleDiff = itemPositionToCenterDiff;
        if (Math.abs(itemPositionToCenterDiff) > 1) {
            /** 缩放比例最多一倍 */
            tempScaleDiff = Math.signum(itemPositionToCenterDiff);
        }
        float scale = 1.0f - Math.abs(tempScaleDiff * mScaleMultiplier);
        // because scaling will make view smaller in its center, then we should move this item to the top or bottom to make it visible
        final float translateY;
        final float translateX;
        if (CarouselLayoutManager.VERTICAL == orientation) {
            /** 暂不适配竖向滚动 */
            translateY = 0;
            translateX = 0;
        } else {

            final int measuredWidth = child.getMeasuredWidth();
            final float originMargin = measuredWidth * mScaleMultiplier / 2;
            /** 因为view缩放，所以视觉上本身就会有间距。我们要做的就是调整间距到构造方法设置的目标间距 */
            final float marginOffset = mItemMargin - originMargin;
            translateY = 0;
            /** 默认水平偏移量就是卡片距离中心位置偏移比例 * 目标偏移量 */
            float scaleXOffset = marginOffset * itemPositionToCenterDiff ;
            if (Math.abs(itemPositionToCenterDiff) > 1) {
                /** 如果距离中心偏移比例大于1，因为两个元素都在缩小变远，为了保证视觉距离不变，还需要对偏移量做调整。调整值为item缩小的距离/2(单侧) */
               scaleXOffset = scaleXOffset - (Math.abs(itemPositionToCenterDiff) - 1) * Math.signum(itemPositionToCenterDiff) * originMargin;
            }
            translateX = scaleXOffset;
        }

        return new ItemTransformation(scale, scale,  translateX , translateY);
    }
}