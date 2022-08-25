package com.mig35.carousellayoutmanager.sample;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.mig35.carousellayoutmanager.CarouselLayoutManager;
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.mig35.carousellayoutmanager.CenterScrollListener;
import com.mig35.carousellayoutmanager.DefaultChildSelectionListener;
import com.mig35.carousellayoutmanager.sample.databinding.ActivityCarouselPreviewBinding;
import com.mig35.carousellayoutmanager.sample.databinding.ItemViewBinding;

import java.util.Locale;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class CarouselPreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivityCarouselPreviewBinding binding = ActivityCarouselPreviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        final TestAdapter adapter = new TestAdapter();

        // create layout manager with needed params: vertical, cycle
        initRecyclerView(binding.list, new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, false), adapter);

    }

    private void initRecyclerView(final RecyclerView recyclerView, final CarouselLayoutManager layoutManager, final TestAdapter adapter) {
        // enable zoom effect. this line can be customized
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener(0.7f));
        layoutManager.setMaxVisibleItems(2);

        recyclerView.setLayoutManager(layoutManager);
        // we expect only fixed sized item for now
        recyclerView.setHasFixedSize(true);
        // sample adapter with random data
        recyclerView.setAdapter(adapter);
        // enable center post scrolling
        recyclerView.addOnScrollListener(new CenterScrollListener());
        // enable center post touching on item and item click listener
        DefaultChildSelectionListener.initCenterItemListener(new DefaultChildSelectionListener.OnCenterItemClickListener() {
            @Override
            public void onCenterItemClicked(@NonNull final RecyclerView recyclerView, @NonNull final CarouselLayoutManager carouselLayoutManager, @NonNull final View v) {
                final int position = recyclerView.getChildLayoutPosition(v);
                final String msg = String.format(Locale.US, "Item %1$d was clicked", position);
                Toast.makeText(CarouselPreviewActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        }, recyclerView, layoutManager);

        layoutManager.addOnItemSelectionListener(new CarouselLayoutManager.OnCenterItemSelectionListener() {

            @Override
            public void onCenterItemChanged(final int adapterPosition) {
                if (CarouselLayoutManager.INVALID_POSITION != adapterPosition) {
                    final int value = adapter.mPosition[adapterPosition];
/*
                    adapter.mPosition[adapterPosition] = (value % 10) + (value / 10 + 1) * 10;
                    adapter.notifyItemChanged(adapterPosition);
*/
                }
            }
        });
    }

    private static final class TestAdapter extends RecyclerView.Adapter<TestViewHolder> {

        private final int[] mColors;
        private final int[] mPosition;
        private final int mItemsCount = 20;

        TestAdapter() {
            mColors = new int[mItemsCount];
            mPosition = new int[mItemsCount];
            mColors[0] = R.drawable.a1;
            mPosition[0] = 0;
            mColors[1] = R.drawable.a2;
            mPosition[1] = 1;
            mColors[2] = R.drawable.a3;
            mPosition[2] = 2;
            mColors[3] = R.drawable.a4;
            mPosition[3] = 3;
            mColors[4] = R.drawable.a5;
            mPosition[4] = 4;
            mColors[5] = R.drawable.a6;
            mPosition[5] = 5;
            mColors[6] = R.drawable.a7;
            mPosition[6] = 6;
            mColors[7] = R.drawable.a8;
            mPosition[7] = 7;
            mColors[8] = R.drawable.a9;
            mPosition[8] = 8;
            mColors[9] = R.drawable.a10;
            mPosition[9] = 9;
            mColors[10] = R.drawable.a11;
            mPosition[10] = 10;
            mColors[11] = R.drawable.a12;
            mPosition[11] = 11;
            mColors[12] = R.drawable.a13;
            mPosition[12] = 12;
            mColors[13] = R.drawable.a14;
            mPosition[13] = 13;
            mColors[14] = R.drawable.a15;
            mPosition[14] = 14;
            mColors[15] = R.drawable.a16;
            mPosition[15] = 15;
            mColors[16] = R.drawable.a17;
            mPosition[16] = 16;
            mColors[17] = R.drawable.a18;
            mPosition[17] = 17;
            mColors[18] = R.drawable.a19;
            mPosition[18] = 18;
            mColors[19] = R.drawable.a20;
            mPosition[19] = 19;
        }

        @Override
        public TestViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
            Log.e("!!!!!!!!!", "onCreateViewHolder");
            return new TestViewHolder(ItemViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @Override
        public void onBindViewHolder(final TestViewHolder holder, final int position) {
            Log.e("!!!!!!!!!", "onBindViewHolder: " + position);
            holder.mItemViewBinding.cItem1.setText(String.valueOf(mPosition[position]));
            holder.mItemViewBinding.cItem2.setText(String.valueOf(mPosition[position]));
            int orientation = holder.itemView.getContext().getResources().getConfiguration().orientation;
            Log.i("tag", "orientation test:" + orientation);
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                holder.mItemViewBinding.testIv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            holder.mItemViewBinding.testIv.setImageResource(mColors[position]);
        }

        @Override
        public int getItemCount() {
            return mItemsCount;
        }

        @Override
        public long getItemId(final int position) {
            return position;
        }
    }

    private static class TestViewHolder extends RecyclerView.ViewHolder {

        private final ItemViewBinding mItemViewBinding;

        TestViewHolder(final ItemViewBinding itemViewBinding) {
            super(itemViewBinding.getRoot());

            mItemViewBinding = itemViewBinding;
        }
    }
}