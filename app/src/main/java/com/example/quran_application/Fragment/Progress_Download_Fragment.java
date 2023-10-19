package com.example.quran_application.Fragment;

import static com.example.quran_application.Surah_Fragment.chaptersList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quran_application.R;

public class Progress_Download_Fragment extends Fragment {


    public Progress_Download_Fragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private Download_Progress_Adapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_progress__download_, container, false);

        recyclerView = view.findViewById(R.id.downloadProgressRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new Download_Progress_Adapter(chaptersList,getActivity());
        recyclerView.setAdapter(adapter);

        return view;
    }
}