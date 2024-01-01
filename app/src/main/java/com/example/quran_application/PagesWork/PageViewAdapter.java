package com.example.quran_application.PagesWork;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.quran_application.R;

import java.util.ArrayList;

public class PageViewAdapter extends PagerAdapter {
    private ArrayList<String> chunks;

    public PageViewAdapter(ArrayList<String> chunks) {
        this.chunks = chunks;
    }

    @Override
    public int getCount() {
        return chunks.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View view = inflater.inflate(R.layout.page_layout, container, false);

        TextView textView = view.findViewById(R.id.pageVersesText);
        textView.setText(chunks.get(position));

        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }
}
