package com.example.quran_application.Database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import static androidx.room.OnConflictStrategy.REPLACE;
import com.example.quran_application.Chapter_response.Chapter;
import com.example.quran_application.verses_response.Verse;

import java.util.List;

import retrofit2.http.DELETE;

@Dao
public interface MainDAO {
    @Insert(onConflict = REPLACE)
    void insertSurah(Chapter chapter);
    @Insert(onConflict = REPLACE)
    void insertVerse(Verse verse);
    @Query("SELECT * FROM CHAPTERS")
    List<Chapter> getALLChapters();
    @Query("SELECT * FROM VERSE WHERE id = :id")
    List<Verse> getALLVerseForChapter(int id);

    @Query("SELECT * FROM VERSE WHERE id = :verseId")
    Verse getVerseById(int verseId);
    @Delete()
    void delete(Chapter chapter);

    @Delete()
    void deleteVerse(Verse verse);
}
