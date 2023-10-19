package com.example.quran_application.verses_response;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.quran_application.Translation.Translation;
@Entity(tableName = "VERSE")

public class Verse  {
    @PrimaryKey
    private int id;
    private String verse_key;
    private String text_uthmani;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVerse_key() {
        return verse_key;
    }

    public void setVerse_key(String verse_key) {
        this.verse_key = verse_key;
    }

    public String getText_uthmani() {
        return text_uthmani;
    }

    public void setText_uthmani(String text_uthmani) {
        this.text_uthmani = text_uthmani;
    }
}
