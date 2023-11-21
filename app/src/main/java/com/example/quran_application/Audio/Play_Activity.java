package com.example.quran_application.Audio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.quran_application.R;

public class Play_Activity extends AppCompatActivity {
    private SeekBar seekBar;
    private boolean isPlaying;
    private MediaPlayer mediaPlayer;
    private ImageView play,round;
    private TextView chapterNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        seekBar = findViewById(R.id.seekBar);
        play = findViewById(R.id.play);
        chapterNum = findViewById(R.id.chapterTxt);

        Intent intent = getIntent();
        String audioUrl = intent.getStringExtra("audioUrl");
        String audioFormat = intent.getStringExtra("audioFormat");
        double audioSize = intent.getDoubleExtra("audioSize",0);
        int chapterNumber = intent.getIntExtra("audioChapter",0);

        chapterNum.setText(String.valueOf(chapterNumber));



        Log.e("MyApp","url+format+size+chapter"+audioUrl+audioFormat+audioSize+chapterNumber);

        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isPlaying){
                    isPlaying = false;
                    play.setImageResource(R.drawable.play_vector);
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }
                else {
                    play.setImageResource(R.drawable.pause_vector);
                    isPlaying = true;
                    if (mediaPlayer == null) {
                        mediaPlayer = MediaPlayer.create(Play_Activity.this, R.raw.music);
                    }
                    mediaPlayer.start();
                }
            }
        });

    }
}