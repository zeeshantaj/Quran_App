package com.example.quran_application.Downloads;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quran_application.Chapter_response.Chapter;
import com.example.quran_application.Database.RoomDB;
import com.example.quran_application.Database.VerseForDb;
import com.example.quran_application.R;
import com.example.quran_application.verses_response.Verse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Download_Fragment extends Fragment {

    public Download_Fragment() {
        // Required empty public constructor
    }

    List<Chapter> chapters;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_download_, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.surahDownloadRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RoomDB roomDB = RoomDB.getInstance(getActivity());

         chapters = new ArrayList<>();
         chapters = roomDB.mainDAO().getALLChapters();

        SurahDownloadedAdapter adapter = new SurahDownloadedAdapter(chapters);
        recyclerView.setAdapter(adapter);

        for (int i = 0; i < chapters.size(); i++) {
            Chapter chapter = chapters.get(i);
            Log.e("MyApp","chapterDownloaded"+chapter.getId());
            List<VerseForDb> verses = roomDB.mainDAO().getALLVerseForChapter(chapter.getId());

            for (VerseForDb verse : verses){
                Log.e("MyApp","verseDownloaded"+verse.getVerse_key());

            }
        }
        return view;
    }



}