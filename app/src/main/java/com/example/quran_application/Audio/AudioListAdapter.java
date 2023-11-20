package com.example.quran_application.Audio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quran_application.R;

import java.util.List;

public class AudioListAdapter extends RecyclerView.Adapter<AudioListAdapter.ViewHolder> {

    private List<Audio> audio_file;

    public AudioListAdapter(List<Audio> audio_file) {
        this.audio_file = audio_file;
    }

    @NonNull
    @Override
    public AudioListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_surah_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioListAdapter.ViewHolder holder, int position) {

        Audio audio = audio_file.get(position);
        holder.chapterNumber.setText(String.valueOf(audio.getChapter_id()));



    }

    @Override
    public int getItemCount() {
        return audio_file.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView play;
        private TextView chapterNumber;
        private SeekBar seekBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            play = itemView.findViewById(R.id.play);
            chapterNumber = itemView.findViewById(R.id.chapterNumber);
            seekBar = itemView.findViewById(R.id.seekBar);
        }
    }
}
