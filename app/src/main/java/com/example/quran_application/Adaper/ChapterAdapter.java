package com.example.quran_application.Adaper;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quran_application.Chapter_response.Chapter;
import com.example.quran_application.Fragment.Progress_Download_Fragment;
import com.example.quran_application.R;
import com.example.quran_application.verses_response.VersesActivity;

import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder> {

    private  List<Chapter> chapters;

    public ChapterAdapter(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    @NonNull
    @Override
    public ChapterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chaper_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterAdapter.ViewHolder holder, int position) {
        Chapter chapter = chapters.get(position);

        holder.surahName.setText(chapter.getName_arabic());
        holder.surahNumber.setText(String.valueOf(chapter.getId()));
        holder.totalAya.setText(String.valueOf(chapter.getVerses_count()));

        holder.itemView.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Scale down the clicked item
                    view.setScaleX(0.9f);
                    view.setScaleY(0.9f);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    // Restore the original size when touch is released or canceled
                    view.setScaleX(1.0f);
                    view.setScaleY(1.0f);
                    break;
            }
            return false;
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), VersesActivity.class);
                intent.putExtra("chapterNumber",chapter.getId());
                intent.putExtra("isChapter", true);
                intent.putExtra("savedVerse", false);
                intent.putExtra("chapterName",chapter.getName_arabic());
                v.getContext().startActivity(intent);
            }
        });

        holder.optionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(),holder.optionMenu);
                popupMenu.inflate(R.menu.verse_item_menu);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.itemDownload) {

                            FragmentManager fragmentManager=((AppCompatActivity)v.getContext()).getSupportFragmentManager();;
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.parentFrameLayout,new Progress_Download_Fragment());
                            transaction.addToBackStack(null);
                            transaction.commit();

                            Toast.makeText(v.getContext(), "save", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView surahName,surahNumber,totalAya,optionMenu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            surahName = itemView.findViewById(R.id.surahName);
            surahNumber = itemView.findViewById(R.id.surahNumber);
            totalAya = itemView.findViewById(R.id.totalAya);
            optionMenu = itemView.findViewById(R.id.optionMenuText);

        }
    }
}
