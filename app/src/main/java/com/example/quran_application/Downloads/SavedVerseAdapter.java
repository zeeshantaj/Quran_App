package com.example.quran_application.Downloads;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quran_application.R;
import com.example.quran_application.verses_response.Verse;

import java.util.List;

public class SavedVerseAdapter extends RecyclerView.Adapter<SavedVerseAdapter.ViewHolder> {


    private List<Verse> list;

    public SavedVerseAdapter(List<Verse> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public SavedVerseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.verses_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedVerseAdapter.ViewHolder holder, int position) {
        Verse verse = list.get(position);
        holder.versesText.setText(verse.getText_uthmani());
        holder.totalVerses.setText(verse.getVerse_key());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView versesText,totalVerses,urduTransText;
        private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            totalVerses = itemView.findViewById(R.id.totalVerses);
            versesText = itemView.findViewById(R.id.versesText);
            urduTransText = itemView.findViewById(R.id.urduTransTxt);
            cardView = itemView.findViewById(R.id.cardViewTranslation);
        }
    }
}
