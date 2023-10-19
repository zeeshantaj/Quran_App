package com.example.quran_application.Adaper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quran_application.CombineVerses;
import com.example.quran_application.R;
import com.example.quran_application.verses_response.Verse;

import java.util.List;

public class VersesCountAdapter extends RecyclerView.Adapter<VersesCountAdapter.ViewHolder> {
    List<CombineVerses> list;

    public VersesCountAdapter(List<CombineVerses> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public VersesCountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.verse_count_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VersesCountAdapter.ViewHolder holder, int position) {

        CombineVerses verse = list.get(position);
        holder.verseCountBtn.setText(verse.getVerseKey() +"\n"+verse.getChapterName());

        Log.e("verseKeyInAdaper","key->"+verse.getVerseKey());
        Log.e("verseKeyInAdaper","Name->"+verse.getChapterName());
    }
    public void updateList(List<CombineVerses> updatedList) {
        list.clear();
        list.addAll(updatedList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button verseCountBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            verseCountBtn = itemView.findViewById(R.id.verseCountBtn);
        }
    }
}
