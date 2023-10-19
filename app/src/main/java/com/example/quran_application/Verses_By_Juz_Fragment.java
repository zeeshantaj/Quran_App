package com.example.quran_application;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Verses_By_Juz_Fragment extends Fragment {

    public Verses_By_Juz_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_verses__by__juz_, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.versesByjuzRecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        return view;
    }
}