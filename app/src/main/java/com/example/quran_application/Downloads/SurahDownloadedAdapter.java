package com.example.quran_application.Downloads;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quran_application.Chapter_response.Chapter;
import com.example.quran_application.Database.RoomDB;
import com.example.quran_application.R;
import com.example.quran_application.verses_response.Verse;
import com.example.quran_application.verses_response.VersesActivity;
import com.google.android.material.button.MaterialButton;
import com.mackhartley.roundedprogressbar.RoundedProgressBar;

import java.util.List;

public class SurahDownloadedAdapter extends RecyclerView.Adapter<SurahDownloadedAdapter.ViewHolder> {

    List<Chapter> chapters;

    public SurahDownloadedAdapter(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    @NonNull
    @Override
    public SurahDownloadedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.download_progress_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SurahDownloadedAdapter.ViewHolder holder, int position) {

        holder.downloadBtn.setVisibility(View.GONE);
        holder.radioButton.setVisibility(View.GONE);

        Chapter chapter = chapters.get(position);
        holder.surahName.setText(chapter.getName_arabic());
        holder.surahNumber.setText(String.valueOf(chapter.getId()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), VersesActivity.class);
                intent.putExtra("savedVerse",true);
                intent.putExtra("savedChapterNumber",chapter.getId());
                v.getContext().startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("ACTION REQUIRE")
                        .setMessage("Do you want to Delete Or Edit This Task Data")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                RoomDB roomDB = RoomDB.getInstance(v.getContext());
                                roomDB.mainDAO().delete(chapter);
                                Verse verse = roomDB.mainDAO().getVerseById(chapter.getId());
                                roomDB.mainDAO().deleteVerse(verse);
                                Toast.makeText(v.getContext(), "Deleted " , Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                                chapters.remove(position);
                                notifyDataSetChanged();

                            }
                        })
                        .show();

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MaterialButton downloadBtn;
        private TextView surahName,surahNumber;
        private RadioButton radioButton;
        private RoundedProgressBar progressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            downloadBtn = itemView.findViewById(R.id.downloadProgressBtn);
            surahName = itemView.findViewById(R.id.surahNameDownloadProgress);
            surahNumber = itemView.findViewById(R.id.surahNumberDownloadProgress);
            radioButton = itemView.findViewById(R.id.rationButtonDownload);
            progressBar = itemView.findViewById(R.id.progress);
        }
    }
}
