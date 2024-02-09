package com.example.quran_application.Chapter_response;

import com.example.quran_application.Audio.AudioResponse;
import com.example.quran_application.Juzs;
import com.example.quran_application.Tafseer.Tafsirs;
import com.example.quran_application.Tafseer.Tasfirs_Info;
import com.example.quran_application.Translation.Translation;
import com.example.quran_application.Translation.Translation_Info;
import com.example.quran_application.Translation.Translation_Response;
import com.example.quran_application.verses_response.Verses_Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Quran_Api_Service {
    @GET("api/v4/chapters")
    Call<ChapterResponse> getChapters();
    @GET("api/v4/juzs")
    Call<Juzs> getJuzs();
    @GET("api/v4/resources/tafsirs")
    Call<Tasfirs_Info> getTafsirs();


    //https://api.quran.com/api/v4/verses/by_juz/30
//    @GET("api/v4/quran/verses/indopak")
//    Call<Verses_Response> getVersesForChapter(@Query("chapter_number") int chapterNumber);
    @GET("api/v4/quran/verses/uthmani")
    Call<Verses_Response> getVersesForChapter(@Query("chapter_number") int chapterNumber);

    @GET("api/v4/quran/verses/uthmani")
    Call<Verses_Response> getVersesBtJuz(@Query("juz_number") int juz_Number);

    //https://api.quran.com/api/v4/quran/verses/uthmani?juz_number=1
    @GET("api/v4/quran/translations/{translationId}")
        Call<Translation_Response> getTranslationForChapter1(@Path("translationId") int translationId, @Query("chapter_number") int chapterNumber);
 @GET("api/v4/quran/translations/158")
        Call<Translation_Response> getTranslationForChapter(@Query("chapter_number") int chapterNumber);

    @GET("api/v4/resources/translations")
    Call<Translation_Info> getTranslationList();

    @GET("api/v4/chapter_recitations/3?language=ar")
        Call<AudioResponse> getAudioResponse();

    //https://api.quran.com/api/v4/quran/verses/uthmani_tajweed?chapter_number=21
}
