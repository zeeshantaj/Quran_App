package com.example.quran_application.Downloads;

public class ChapterInfo {
    private int chapterNumber;
    private String arabicText;
    private String fileSize;

    public ChapterInfo(int chapterNumber, String arabicText, String fileSize) {
        this.chapterNumber = chapterNumber;
        this.arabicText = arabicText;
        this.fileSize = fileSize;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public String getArabicText() {
        return arabicText;
    }

    public void setArabicText(String arabicText) {
        this.arabicText = arabicText;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
}
