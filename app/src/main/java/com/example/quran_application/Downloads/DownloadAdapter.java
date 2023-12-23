package com.example.quran_application.Downloads;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.fileSize.setText(chapterInfo.getFileSize());
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
