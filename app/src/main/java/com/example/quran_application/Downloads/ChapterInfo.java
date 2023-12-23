package com.example.quran_application.Downloads;

public class ChapterInfo {
    private int chapterNumber;
    private String arabicText;
    private long fileSize;
    private String filePath;

    public ChapterInfo(int chapterNumber, String arabicText, long fileSize,String filePath) {
        this.chapterNumber = chapterNumber;
        this.arabicText = arabicText;
        this.fileSize = fileSize;
        this.filePath = filePath;
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

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
