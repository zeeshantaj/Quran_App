package com.example.quran_application.Translation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quran_application.Chapter_response.Quran_Api_Service;
import com.example.quran_application.MainActivity;
import com.example.quran_application.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Translation_Select_Fragment extends Fragment {
    public Translation_Select_Fragment() {
        // Required empty public constructor
    }

    private View view;
    private RecyclerView recyclerView;

    private TranslationListAdapter adapter;
    private List<TranslationList> translationLists;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_translation__select_, container, false);

        recyclerView = view.findViewById(R.id.translation_select_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        translationLists = new ArrayList<>();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.quran.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Quran_Api_Service service = retrofit.create(Quran_Api_Service.class);
        Call<Translation_Info> call1 = service.getTranslationList();
        call1.enqueue(new Callback<Translation_Info>() {
            @Override
            public void onResponse(Call<Translation_Info> call, Response<Translation_Info> response) {
                if (response.isSuccessful()){
                    Translation_Info translationInfo = response.body();
                    translationLists = translationInfo.getTranslations();
                    adapter=new TranslationListAdapter(translationLists);
                    recyclerView.setAdapter(adapter);

                    for (TranslationList translationList:translationLists){
                        Log.e("MyApp","tranlationList"+translationList.getLanguage_name());
                        Log.e("MyApp","authorName"+translationList.getAuthor_name());
                        Log.e("MyApp","Name"+translationList.getName());
                        Log.e("MyApp","id"+translationList.getId());
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Exception "+ response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Translation_Info> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}