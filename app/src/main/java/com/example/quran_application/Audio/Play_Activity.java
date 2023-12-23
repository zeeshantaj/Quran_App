package com.example.quran_application.Audio;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import com.example.quran_application.Downloads.DownloadListener;
import com.example.quran_application.R;

import java.io.IOException;
import java.util.Locale;

public class Play_Activity extends AppCompatActivity {
    private SeekBar seekBar;
    private boolean isPlaying;
    private MediaPlayer mediaPlayer;
    private ImageView play,round,forward,backward;
    private TextView chapterNum,startTime,endTime,nameArabic;
    private Handler handler;
    private ProgressBar progressBar;
    private int total,chapterNumber;
    private String audioUrl,audioName,audioNumber;
    private ProgressBar downloadProgressBar;
    private LinearLayout linearLayout;
    private TextView progressText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        downloadProgressBar = findViewById(R.id.audioProgressDownload);
        linearLayout = findViewById(R.id.progressLinearLayout);
        progressText = findViewById(R.id.progressTxt);


        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(androidx.appcompat.R.id.action_bar_container),false);
        fade.excludeTarget(android.R.id.statusBarBackground,true);
        fade.excludeTarget(android.R.id.navigationBarBackground,true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        seekBar = findViewById(R.id.seekBar);
        play = findViewById(R.id.play);
        round = findViewById(R.id.roundPlay);
        progressBar = findViewById(R.id.myProgressBar);
        startTime = findViewById(R.id.startTime);
        nameArabic = findViewById(R.id.nameArabic);
        forward = findViewById(R.id.fastForwardBtn);
        backward = findViewById(R.id.fastBackwardBtn);
        endTime = findViewById(R.id.endTime);
        handler = new Handler();

        Intent intent = getIntent();

        boolean isPlay = intent.getBooleanExtra("playBoolean",false);


        //chapterNum.setText(String.valueOf(chapterNumber));


        mediaPlayer = new MediaPlayer();
        if ( mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        //mediaPlayer = MediaPlayer.create(this, R.raw.music);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (isPlaying){
//                    isPlaying = false;
//                    play.setImageResource(R.drawable.play_vector);
////                    if (mediaPlayer != null) {
////                        mediaPlayer.stop();
////                        mediaPlayer.release();
////                        mediaPlayer = null;
////                    }
//                    if (mediaPlayer.isPlaying()){
//                        mediaPlayer.pause();
//                    }
//                    else {
//                        mediaPlayer.start();
//                    }
//                }
//                else {
//                    play.setImageResource(R.drawable.pause_vector);
//                    isPlaying = true;
//
//                }

//                if (mediaPlayer.isPlaying()){
//                    play.setBackgroundResource(R.drawable.play_vector);
//                    mediaPlayer.pause();
//                }
//                else {
//                    play.setBackgroundResource(R.drawable.pause_vector);
//                    mediaPlayer.start();
//                }
                play.setImageResource(R.drawable.play_vector);
                if (isPlaying){
                    mediaPlayer.pause();
                    isPlaying = false;
                } else {
                    mediaPlayer.start();
                    isPlaying = true;
                    play.setImageResource(R.drawable.pause_vector);
                }
                Log.e("MyApp","isPlaying"+isPlaying);
            }
        });

        String audioFormat = intent.getStringExtra("audioFormat");
        audioName = intent.getStringExtra("audioSurahName");
        double audioSize = intent.getDoubleExtra("audioSize",0);
        chapterNumber = intent.getIntExtra("audioChapter",0);

        nameArabic.setText(audioName);

        Log.e("MyApp ","isPlaying"+isPlaying);

      // play online
        if (isPlay){
            audioUrl = intent.getStringExtra("audioUrl");
            try {
                mediaPlayer.setDataSource(audioUrl);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        else {
            // play offline
            String path = intent.getStringExtra("path");
            try {
                mediaPlayer.setDataSource(path);
            }
            catch (IOException e){
                e.printStackTrace();
            }

        }
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // Start playing when prepared
                    mediaPlayer.start();
                    progressBar.setVisibility(View.GONE); // Hide progress bar when playback starts
                    updateSeekBar();

                    int duration = mp.getDuration();
                    String formattedTime = millisecondsToTime(duration);
                    endTime.setText(formattedTime);
                    isPlaying = true;
                }
            });
            mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    if (percent < 100) {
                        progressBar.setVisibility(View.VISIBLE); // Show progress bar while buffering
                        progressBar.setIndeterminate(false);
                        progressBar.setProgress(percent);


                    } else {
                        progressBar.setVisibility(View.GONE); // Hide progress bar when buffering is complete
                    }
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    // Handle completion if needed
                }
            });
            mediaPlayer.prepareAsync();


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000);
                }
            }
        });
        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 10000);
                }
            }
        });
        setToolbar();
    }
    public String millisecondsToTime(long milliseconds) {
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        String time;

        if (hours > 0) {
            time = String.format(Locale.US,"%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            time = String.format(Locale.US,"%02d:%02d", minutes, seconds);
        }

        return time;

    }
    private void updateSeekBar() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    total = mediaPlayer.getDuration();
                    seekBar.setProgress(currentPosition);
                    seekBar.setMax(total);

                  //  int currentPosition = mediaPlayer.getCurrentPosition();

                    // Calculate minutes and seconds of the current position
                    int minutes = (currentPosition / 1000) / 60;
                    int seconds = (currentPosition / 1000) % 60;

                    // Update duration TextView with the current position
                    String durationString = String.format(Locale.US,"%02d:%02d", minutes, seconds);
                    startTime.setText(durationString);

                    handler.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.playTollBar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        String chap = String.valueOf(chapterNumber);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle("Chapter Number: "+chap);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.audio_download_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home){

            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                showConfirmationDialog();
            }
            else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    getOnBackInvokedDispatcher();
                } else {
                    onBackPressed();
                }
            }
            return true;
        }
        if (id == R.id.audioDownloadBtn){

            linearLayout.setVisibility(View.VISIBLE);
            AudioDownloader audioDownloader = new AudioDownloader(this);
            audioNumber = String.valueOf(chapterNumber);
            audioDownloader.downloadAudio(audioUrl,audioNumber,audioName, new DownloadListener() {
                @Override
                public void onDownloadCompleted(String filePath) {
                    //Toast.makeText(Play_Activity.this, "Downloaded Successfully"+filePath, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onDownloadFailed(String errorMessage) {

                }

                @Override
                public void onProgressUpdate(int progress) {

                    downloadProgressBar.setProgress(progress,true);
                    runOnUiThread(() -> progressText.setText(progress + "%"));
                }
            });

        }


        return super.onOptionsItemSelected(item);
    }
    private void showConfirmationDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Do you want to continue playing in the background?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Continue playing in the background
                // Implement code here to handle playing in the background
                mediaPlayer.start();
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Stop playing and go back
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (mediaPlayer != null) {
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
    }


    private void startAnimation(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(round,"rotation",0f,360f);
        animator.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator);
        animatorSet.start();


//        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
//        animator.setDuration(1000);
//        animator.setRepeatCount(ObjectAnimator.INFINITE); // Set to repeat infinitely
//        animator.setInterpolator(new LinearInterpolator()); // LinearInterpolator for smooth rotation
//
//        animator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                super.onAnimationCancel(animation);
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//                super.onAnimationRepeat(animation);
//            }
//
//            @Override
//            public void onAnimationStart(Animator animation) {
//                super.onAnimationStart(animation);
//            }
//        });
//
//        animator.start();
    }
}