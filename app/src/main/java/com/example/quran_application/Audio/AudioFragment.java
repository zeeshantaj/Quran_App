package com.example.quran_application.Audio;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quran_application.Chapter_response.ChapterResponse;
import com.example.quran_application.Chapter_response.Quran_Api_Service;
import com.example.quran_application.Model.SharedViewModel;
import com.example.quran_application.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AudioFragment extends Fragment {

    public AudioFragment(){

    }

    private AudioListAdapter adapter;
    private List<Audio> audioList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.audio_fragment, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.audioRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        audioList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.quran.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Quran_Api_Service service = retrofit.create(Quran_Api_Service.class);
        Call<AudioResponse> call = service.getAudioResponse();

        call.enqueue(new Callback<AudioResponse>() {
            @Override
            public void onResponse(Call<AudioResponse> call, Response<AudioResponse> response) {
                if (response.isSuccessful()){

                    AudioResponse audioResponse = response.body();
                    audioList = audioResponse.getAudio_file();
                    for (Audio audio:audioList){
                        //Log.e("MyApp","AudioResponse"+audio.getAudio_url());

                    }
                    adapter = new AudioListAdapter(audioList);
                        recyclerView.setAdapter(adapter);
                }
                else {
                    Toast.makeText(getActivity(), "Response Error "+response.message(), Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<AudioResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Error "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.e("MyApp","Error "+t.getLocalizedMessage());
            }
        });

        return view;
    }
}
