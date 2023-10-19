package com.example.quran_application;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quran_application.Adaper.ChapterAdapter;
import com.example.quran_application.Adaper.JuzsAdapter;
import com.example.quran_application.Chapter_response.ChapterResponse;
import com.example.quran_application.Chapter_response.Quran_Api_Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Para_Fragment extends Fragment {



    public Para_Fragment() {
        // Required empty public constructor
    }

    public static RecyclerView recyclerView;
    public static LinearLayoutManager layoutManager;
    public static List<JuzsList> juzsLists;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_para_, container, false);

        recyclerView = view.findViewById(R.id.juzsRecycler);
        layoutManager = new LinearLayoutManager(getActivity());
        juzsLists = new ArrayList<>();
        recyclerView.setLayoutManager(layoutManager);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.quran.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Quran_Api_Service service = retrofit.create(Quran_Api_Service.class);


        Call<Juzs> call = service.getJuzs();

        call.enqueue(new Callback<Juzs>() {
            @Override
            public void onResponse(Call<Juzs> call, Response<Juzs> response) {
                if (response.isSuccessful()) {
                    Juzs juzs = response.body();
                    juzsLists = juzs.getJuzs();
                    JuzsAdapter adapter = new JuzsAdapter(juzsLists);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getActivity(), "Response Error "+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Juzs> call, Throwable t) {
                Toast.makeText(getActivity(), "Error "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.e("MyApp","Error ->"+t.getLocalizedMessage());
            }
        });
        return view;
    }
    public static void performSearchForJuz(int query) {
        int positionToScroll = -1;
        for (int i = 0; i < juzsLists.size(); i++) {
            if (juzsLists.get(i).getId() == query) {
                positionToScroll = i;
                break; // Stop searching after finding the first match
            }
        }


        if (positionToScroll != -1) {
            LinearSmoothScroller smoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
                @Override
                protected int getVerticalSnapPreference() {
                    return LinearSmoothScroller.SNAP_TO_START;
                }
            };


            smoothScroller.setTargetPosition(positionToScroll);
            layoutManager.startSmoothScroll(smoothScroller);
        } else {
            Toast.makeText(recyclerView.getContext(), "No matching item found", Toast.LENGTH_SHORT).show();
        }
    }

}