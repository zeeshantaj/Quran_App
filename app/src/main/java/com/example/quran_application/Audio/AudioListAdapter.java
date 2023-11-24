package com.example.quran_application.Audio;

import static com.google.gson.reflect.TypeToken.get;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quran_application.Model.SharedViewModel;
import com.example.quran_application.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AudioListAdapter extends RecyclerView.Adapter<AudioListAdapter.ViewHolder> {

    List<Audio> audio_file = new ArrayList<>();

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
        holder.fileType.setText(audio.getFormat());
        holder.fileSize.setText(String.valueOf(audio.getFile_size()));

        SharedViewModel sharedViewModel = new ViewModelProvider((ViewModelStoreOwner) holder.itemView.getContext()).get(SharedViewModel.class);
        //Log.e("MyApp","SharedModelValueAdapter:"+sharedViewModel.getSurahNameData());

        List<String> surahNames = sharedViewModel.getSurahNameData();
        if (surahNames != null && position < surahNames.size()) {
            String surahName = surahNames.get(position);
            holder.surahNameAudio.setText(surahName);
        } else {
            holder.surahNameAudio.setText("No Name"); // Set a default value if surahNames is null or empty
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Play_Activity.class);
                intent.putExtra("audioUrl",audio.getAudio_url());
                intent.putExtra("audioChapter",audio.getChapter_id());
                intent.putExtra("audioFormat",audio.getFormat());
                intent.putExtra("audioSize",audio.getFile_size());
                intent.putExtra("audioSurahName",surahNames.get(position));

                holder.itemView.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return audio_file.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView chapterNumber,fileType,fileSize,surahNameAudio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chapterNumber = itemView.findViewById(R.id.chapterNumber);
            fileSize = itemView.findViewById(R.id.fileSizeText);
            fileType = itemView.findViewById(R.id.fileTypeText);
            surahNameAudio = itemView.findViewById(R.id.surahNameAudio);

        }

    }
}
