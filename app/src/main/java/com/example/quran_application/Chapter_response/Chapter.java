package com.example.quran_application.Chapter_response;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "CHAPTERS")
public class Chapter {

    @PrimaryKey
    private int id;
    private String revelation_place;
    private int revelation_order;
    private boolean bismillah_pre;
    private String name_simple;
    private String name_complex;
    private String name_arabic;
    private int verses_count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRevelation_place() {
        return revelation_place;
    }

    public void setRevelation_place(String revelation_place) {
        this.revelation_place = revelation_place;
    }

    public int getRevelation_order() {
        return revelation_order;
    }

    public void setRevelation_order(int revelation_order) {
        this.revelation_order = revelation_order;
    }

    public boolean isBismillah_pre() {
        return bismillah_pre;
    }

    public void setBismillah_pre(boolean bismillah_pre) {
        this.bismillah_pre = bismillah_pre;
    }

    public String getName_simple() {
        return name_simple;
    }

    public void setName_simple(String name_simple) {
        this.name_simple = name_simple;
    }

    public String getName_complex() {
        return name_complex;
    }

    public void setName_complex(String name_complex) {
        this.name_complex = name_complex;
    }

    public String getName_arabic() {
        return name_arabic;
    }

    public void setName_arabic(String name_arabic) {
        this.name_arabic = name_arabic;
    }

    public int getVerses_count() {
        return verses_count;
    }

    public void setVerses_count(int verses_count) {
        this.verses_count = verses_count;
    }

}
