package com.example.quran_application.Downloads;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quran_application.Audio.Play_Activity;
import com.example.quran_application.FileSizeFormater.FileSizeFormatter;
import com.example.quran_application.R;

import org.w3c.dom.Text;

import java.util.List;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder> {
    List<ChapterInfo> list;

    public DownloadAdapter(List<ChapterInfo> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public DownloadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_surah_list2,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadAdapter.ViewHolder holder, int position) {
        ChapterInfo chapterInfo = list.get(position);
        holder.name.setText(chapterInfo.getArabicText().replace(".mp3",""));
        holder.number.setText(String.valueOf(chapterInfo.getChapterNumber()));
        holder.fileType.setText("mp3");
       // holder.fileSize.setText(chapterInfo.getFileSize());
        long fileSizeInBytes = chapterInfo.getFileSize();
        String fileSizeText = FileSizeFormatter.getFileSizeString(fileSizeInBytes); // Custom method to convert bytes to appropriate size
        holder.fileSize.setText(fileSizeText);

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
                Intent intent = new Intent(v.getContext(), Play_Activity.class);
                intent.putExtra("playBoolean",false);
                intent.putExtra("audioChapter",chapterInfo.getChapterNumber());
                intent.putExtra("audioFormat",holder.fileType.getText());
                intent.putExtra("audioSurahName",holder.name.getText());
                intent.putExtra("path",chapterInfo.getFilePath());
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name,number,fileType,fileSize;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.surahNameAudio);
            number = itemView.findViewById(R.id.chapterNumber);
            fileType = itemView.findViewById(R.id.fileTypeText);
            fileSize = itemView.findViewById(R.id.fileSizeText);

        }
    }


}
