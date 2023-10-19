package com.example.quran_application.verses_response;

import static com.example.quran_application.verses_response.VersesActivity.translationList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.quran_application.R;
import com.example.quran_application.Translation.Translation;

import java.util.List;

public class Verses_Adapter extends RecyclerView.Adapter<Verses_Adapter.ViewHolder> {

    private List<Verse> versesList;
    public Verses_Adapter(List<Verse> versesList)
    {
        this.versesList = versesList;
    }

    @NonNull
    @Override
    public Verses_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.verses_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Verses_Adapter.ViewHolder holder, int position) {
        Verse verses = versesList.get(position);
        holder.versesText.setText(verses.getText_uthmani());
        holder.totalVerses.setText(verses.getVerse_key());

        if (translationList.size() != 0){
        Translation translation = translationList.get(position);
        holder.urduTransText.setText(translation.getText());
        if (!holder.urduTransText.getText().toString().isEmpty()) {
            holder.cardView.setVisibility(View.VISIBLE);
            }
        }



    }
    @Override
    public int getItemCount() {
        return versesList.size();
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
