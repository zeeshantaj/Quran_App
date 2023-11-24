package com.example.quran_application.Model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {

    private List<String> surahNameListData = new ArrayList<>();

    public void setSurahNameListData(String surahName){
        surahNameListData.add(surahName);
    }

    public List<String> getSurahNameData(){
        return surahNameListData;
    }
}
