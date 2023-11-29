package com.example.quran_application.Click_Animation;

import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

public class ClickedItemAnimator extends DefaultItemAnimator {
    @Override
    public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
        final View view = newHolder.itemView;
        view.setScaleX(0.9f);
        view.setScaleY(0.9f);
        view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(getChangeDuration()).start();

        return super.animateChange(oldHolder, newHolder, fromX, fromY, toX, toY);
    }
}
