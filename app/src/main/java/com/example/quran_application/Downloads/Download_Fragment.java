package com.example.quran_application.Downloads;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.quran_application.Audio.Audio;
import com.example.quran_application.Chapter_response.Chapter;
import com.example.quran_application.Database.RoomDB;
import com.example.quran_application.R;
import com.example.quran_application.verses_response.Verse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Download_Fragment extends Fragment {

    public Download_Fragment() {
        // Required empty public constructor
    }

    List<ChapterInfo> chapterInfoList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_download_, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.surahDownloadRecycler);
        ArrayList<String> mp3FileNames = new ArrayList<>();
        chapterInfoList = new ArrayList<>();


        // Directory where the MP3 files are stored
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "Quran_App_Downloaded_Surah");

// Check if the directory exists and is a directory
        if (directory.exists() && directory.isDirectory()) {
            // Retrieve all files in the directory
            File[] files = directory.listFiles();
            ArrayList<ChapterInfo> chapterInfoList = new ArrayList<>();

            if (files != null && files.length > 0) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".mp3")) {

                        Log.d("File", "File Path: " + file.getAbsolutePath());
                        Log.d("File", "File Name: " + file.getName());
                        mp3FileNames.add(file.getName());



                    }
                }
            } else {
                // No MP3 files found in the directory
                Log.d("File", "No MP3 files found in the directory");
            }
        } else {
            // Directory doesn't exist or is not a directory
            Log.d("File", "Directory does not exist or is not a directory");
        }

        for (String fileName : mp3FileNames) {
            String[] parts = fileName.split("_", 2); // Split filename by underscore (limiting to 2 parts)
            if (parts.length == 2) {
                try {
                    int chapterNumber = Integer.parseInt(parts[0]); // Extract chapter number
                    String arabicText = parts[1]; // Extract Arabic text
                    File mp3File = new File(directory, fileName);
                    long fileSize = mp3File.length(); // Get file size in bytes


                    String path = mp3File.getAbsolutePath();

                    // Create ChapterInfo object and add to the list
                    ChapterInfo chapterInfo = new ChapterInfo(chapterNumber, arabicText,fileSize,path);
                    chapterInfoList.add(chapterInfo);

                    Log.e("File","ChapterInfo "+chapterInfo.getChapterNumber());
                    Log.e("File","ChapterInfo "+chapterInfo.getArabicText());


                } catch (NumberFormatException e) {
                    // Handle parsing error if chapter number is not a valid integer
                    e.printStackTrace();
                }
            }
        }

        DownloadAdapter adapter = new DownloadAdapter(chapterInfoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return view;
    }



}