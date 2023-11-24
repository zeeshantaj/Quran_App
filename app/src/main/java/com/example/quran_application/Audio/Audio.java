package com.example.quran_application.Audio;

public class Audio {
    private int id;
    private int chapter_id;
    private double file_size;
    private String format;
    private String audio_url;
    private String surah_name_arabic;

    public String getSurah_name_arabic() {
        return surah_name_arabic;
    }

    public void setSurah_name_arabic(String surah_name_arabic) {
        this.surah_name_arabic = surah_name_arabic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
    }

    public double getFile_size() {
        return file_size;
    }

    public void setFile_size(double file_size) {
        this.file_size = file_size;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getAudio_url() {
        return audio_url;
    }

    public void setAudio_url(String audio_url) {
        this.audio_url = audio_url;
    }
}
