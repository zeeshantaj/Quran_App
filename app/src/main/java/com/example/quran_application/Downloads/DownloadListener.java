package com.example.quran_application.Downloads;

public interface DownloadListener {
    void onDownloadCompleted(String filePath);

    void onDownloadFailed(String errorMessage);

    void onProgressUpdate(int progress);
}
