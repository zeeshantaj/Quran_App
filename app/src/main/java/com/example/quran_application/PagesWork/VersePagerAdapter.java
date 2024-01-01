package com.example.quran_application.PagesWork;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quran_application.R;

import java.util.List;

public class VersePagerAdapter extends RecyclerView.Adapter<VersePagerAdapter.VerseViewHolder> {
    private List<String> pages;
    public VersePagerAdapter(List<String> pages) {
        this.pages = pages;
    }

    @NonNull
    @Override
    public VerseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_layout, parent, false);
        return new VerseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerseViewHolder holder, int position) {
        String pageText = pages.get(position);
        holder.bind(pageText);
    }

    @Override
    public int getItemCount() {
        return pages.size();
    }

    static class VerseViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public VerseViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.pageVersesText);
        }

        public void bind(String text) {
            textView.setText(text);
        }
    }
}
