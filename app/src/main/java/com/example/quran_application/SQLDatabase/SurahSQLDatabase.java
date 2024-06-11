package com.example.quran_application.SQLDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SurahSQLDatabase extends SQLiteOpenHelper {

    private String TABLE_NAME = "SURAH_TABLE";
    private String DATABASE_NAME = "SURAH_DOWNLOADED_DATABASE";
    private int DATABASE_VERSION = 1;
    public SurahSQLDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
