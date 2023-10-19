package com.example.quran_application;

import com.example.quran_application.verses_response.Verse;

import java.util.List;

public class JuzsList {
    private int id;
    private String  juz_number,first_verse_id,last_verse_id,verses_count;


    public String getFirst_verse_id() {
        return first_verse_id;
    }

    public void setFirst_verse_id(String first_verse_id) {
        this.first_verse_id = first_verse_id;
    }

    public String getLast_verse_id() {
        return last_verse_id;
    }

    public void setLast_verse_id(String last_verse_id) {
        this.last_verse_id = last_verse_id;
    }

    public String getVerses_count() {
        return verses_count;
    }

    public void setVerses_count(String verses_count) {
        this.verses_count = verses_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJuz_number() {
        return juz_number;
    }

    public void setJuz_number(String juz_number) {
        this.juz_number = juz_number;
    }


}

