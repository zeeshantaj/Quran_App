package com.example.quran_application.Fragment;

import static com.example.quran_application.Surah_Fragment.chaptersList;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.CloseGuard;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quran_application.Chapter_response.Chapter;
import com.example.quran_application.Chapter_response.Quran_Api_Service;
import com.example.quran_application.Database.RoomDB;
import com.example.quran_application.R;
import com.example.quran_application.verses_response.Verse;
import com.example.quran_application.verses_response.Verses_Response;
import com.google.android.material.button.MaterialButton;
import com.mackhartley.roundedprogressbar.RoundedProgressBar;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Download_Progress_Adapter extends RecyclerView.Adapter<Download_Progress_Adapter.ViewHolder> {


    List<Chapter> chapters;

    RoomDB database;

    private Handler handler = new Handler(Looper.getMainLooper());
    private int currentProgress = 0;
    private static final int TOTAL_PROGRESS = 100;
    private static final int PROGRESS_INCREMENT = 1;
    private static final long PROGRESS_UPDATE_INTERVAL = 50;

    Context context;
    public Download_Progress_Adapter(List<Chapter> chapters,Context context) {
        this.chapters = chapters;
        this.context = context;
    }

    @NonNull
    @Override
    public Download_Progress_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.download_progress_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Download_Progress_Adapter.ViewHolder holder, int position) {

        Chapter chapter = chaptersList.get(position);
        holder.surahName.setText(chapter.getName_arabic());
        holder.surahNumber.setText(String.valueOf(chapter.getId()));


        database = RoomDB.getInstance(holder.itemView.getContext());

        List<Chapter> chapter1 = database.mainDAO().getALLChapters();


        holder.downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.mainDAO().insertSurah(chapter);
                startCustomAnimation(holder.progressBar);
                saveVerses(chapter.getId());
            }
        });

        for (Chapter chapter2:chapter1){
            Log.e("MyApp","ChapterDownloaded"+chapter2.getId());
            int chapterNumber = chapter2.getId();
            //saveVerses(chapterNumber);
            if (chapterNumber == chapter.getId()){
                holder.downloadBtn.setVisibility(View.GONE);
                holder.radioButton.setChecked(true);
            }
        }
    }




    @Override
    public int getItemCount() {
        return chaptersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private MaterialButton downloadBtn;
        private TextView surahName,surahNumber;
        private RadioButton radioButton;
        private ProgressBar progressBar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            downloadBtn = itemView.findViewById(R.id.downloadProgressBtn);
            surahName = itemView.findViewById(R.id.surahNameDownloadProgress);
            surahNumber = itemView.findViewById(R.id.surahNumberDownloadProgress);
            radioButton = itemView.findViewById(R.id.rationButtonDownload);
            progressBar = itemView.findViewById(R.id.progressBar);

        }
    }

    private void saveVerses(int chapterNumber) {



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.quran.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Quran_Api_Service service = retrofit.create(Quran_Api_Service.class);

        Call<Verses_Response> call = service.getVersesForChapter(chapterNumber);
        call.enqueue(new Callback<Verses_Response>() {
            @Override
            public void onResponse(Call<Verses_Response> call, Response<Verses_Response> response) {
                if (response.isSuccessful()) {
                    List<Verse> verses = response.body().getVerses();
                    RoomDB roomDB = RoomDB.getInstance(context);

                    // Save the verses to your Room database.
                   for (Verse verse : verses){

                       Log.e("MyApp","response -> size"+verses.size());
                       Log.e("MyApp","response ->"+verse.getText_uthmani());
                       Log.e("MyApp","response ->"+verse.getVerse_key());
                       Log.e("MyApp","response ->"+verse.getId());
                       roomDB.mainDAO().insertVerse(verse);
                       
                   }


                    } else {
                    // Handle the error.
                    Log.e("MyApp","responseError"+response.message());
                }
            }

            @Override
            public void onFailure(Call<Verses_Response> call, Throwable t) {
                // Handle the network error.
            }
        });


    }

    private void startCustomAnimation(ProgressBar progressBar) {
        // Create a Runnable to update the progress.
        Runnable progressUpdater = new Runnable() {
            @Override
            public void run() {
                if (currentProgress < TOTAL_PROGRESS) {
                    currentProgress += PROGRESS_INCREMENT;
                    progressBar.setProgress(currentProgress);

                    // Repeat the animation until the desired progress is reached.
                    handler.postDelayed(this, PROGRESS_UPDATE_INTERVAL);
                }
            }
        };

        // Start the custom animation.
        handler.postDelayed(progressUpdater, PROGRESS_UPDATE_INTERVAL);
    }
}
