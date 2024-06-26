package com.example.quran_application;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quran_application.Adaper.ChapterAdapter;
import com.example.quran_application.Chapter_response.Chapter;
import com.example.quran_application.Chapter_response.ChapterResponse;
import com.example.quran_application.Chapter_response.Quran_Api_Service;
import com.example.quran_application.Click_Animation.ClickedItemAnimator;
import com.example.quran_application.Model.SharedViewModel;
import com.example.quran_application.Translation.MySharedPreference;
import com.example.quran_application.Translation.Translation_Select_Fragment;
import com.example.quran_application.verses_response.VersesActivity;
import com.google.android.material.shadow.ShadowRenderer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Surah_Fragment extends Fragment {


    public Surah_Fragment() {
        // Required empty public constructor
    }
    private static RecyclerView recyclerView;
    private ChapterAdapter chapterAdapter;
    public static List<Chapter> chaptersList;
    private Chapter chapterModel;
    public static LinearLayoutManager layoutManager;
    private TextView surahNameTxt;
    private CardView cardView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_surah_, container, false);


        MySharedPreference mySharedPreferences = new MySharedPreference(getActivity());

       // mySharedPreferences.saveValue("translation_key", 158);
        // Retrieving a value
        int value = mySharedPreferences.getValue("translation_key", 158);
        //Log.d("MainActivity", "Value retrieved: " + value);

        recyclerView = view.findViewById(R.id.chapterRecycler);
        surahNameTxt = view.findViewById(R.id.lastSurahName);
        cardView = view.findViewById(R.id.lastSurahCard);

        chapterModel = new Chapter();
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        ClickedItemAnimator clickedItemAnimator = new ClickedItemAnimator();
        recyclerView.setItemAnimator(clickedItemAnimator);

        chaptersList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.quran.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Quran_Api_Service service = retrofit.create(Quran_Api_Service.class);


        Call<ChapterResponse> call = service.getChapters();

        call.enqueue(new Callback<ChapterResponse>() {
            @Override
            public void onResponse(Call<ChapterResponse> call, Response<ChapterResponse> response) {
                if (response.isSuccessful()) {
                    String url = String.valueOf(call.request().url());
                    Log.d("MyApp","url -> "+url);
                    ChapterResponse chaptersResponse = response.body();
                    chaptersList = chaptersResponse.getChapters();
                    chapterAdapter= new ChapterAdapter(chaptersList);
                    recyclerView.setAdapter(chapterAdapter);

                    SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

                    List<String> surahNameList = new ArrayList<>();
                    for (Chapter chapter : chaptersList) {
                        surahNameList.add(chapter.getName_arabic());
                        sharedViewModel.setSurahNameListData(chapter.getName_arabic());
                    }

                } else {
                    Toast.makeText(getActivity(), "Response Error "+response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ChapterResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Error "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.e("MyApp","Error ->"+t.getLocalizedMessage());
            }
        });
        getLastReadSurah();
        return view;
    }



    public static void performSearch(int query) {
        int positionToScroll = -1;
        for (int i = 0; i < chaptersList.size(); i++) {
            if (chaptersList.get(i).getId() == query) {
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

    private void getLastReadSurah(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("lastSurahSharedPreference", Context.MODE_PRIVATE);
        int surahID = sharedPreferences.getInt("lastSurahID",0);
        String surahName = sharedPreferences.getString("lastSurahName","");
        boolean isChapter = sharedPreferences.getBoolean("lastSurahIsChapter",false);
        boolean savedVerse = sharedPreferences.getBoolean("lastSurahSavedVerse",false);
        if (isChapter){
            cardView.setVisibility(View.VISIBLE);
            surahNameTxt.setText(surahName);
            cardView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), VersesActivity.class);
                intent.putExtra("chapterNumber",surahID);
                intent.putExtra("isChapter", isChapter);
                intent.putExtra("savedVerse", savedVerse);
                intent.putExtra("chapterName",surahName);
                startActivity(intent);
            });
        }
        else {
            cardView.setVisibility(View.GONE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getLastReadSurah();
    }
    //    public static void performSearch(final int query) {
//        int positionToScroll = -1;
//        for (int i = 0; i < chaptersList.size(); i++) {
//            if (chaptersList.get(i).getId() == query) {
//                positionToScroll = i;
//                break; // Stop searching after finding the first match
//            }
//        }
//
//        if (positionToScroll != -1) {
//            // Highlight the found item with a blinking animation
//            final int finalPosition = positionToScroll;
//            final View itemToBlink = layoutManager.findViewByPosition(finalPosition);
//            if (itemToBlink != null) {
//                final int blinkDuration = 500; // Duration of each blink in milliseconds
//
//                final Handler handler = new Handler();
//                final int[] blinkCount = {0};
//
//                final Runnable blinkRunnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        if (blinkCount[0] % 2 == 0) {
//                            itemToBlink.setBackgroundColor(Color.TRANSPARENT); // Set to transparent
//                        } else {
//                            itemToBlink.setBackgroundColor(Color.YELLOW); // Set to a highlight color
//                        }
//                        blinkCount[0]++;
//
//                        if (blinkCount[0] < 6) { // Adjust the number of blinks
//                            handler.postDelayed(this, blinkDuration);
//                        } else {
//                            // Animation completed, scroll to the item
//                            LinearSmoothScroller smoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
//                                @Override
//                                protected int getVerticalSnapPreference() {
//                                    return LinearSmoothScroller.SNAP_TO_START;
//                                }
//                            };
//                            smoothScroller.setTargetPosition(finalPosition);
//                            layoutManager.startSmoothScroll(smoothScroller);
//                        }
//                    }
//                };
//
//                handler.post(blinkRunnable);
//            }
//        } else {
//            Toast.makeText(recyclerView.getContext(), "No matching item found", Toast.LENGTH_SHORT).show();
//        }
//    }


}