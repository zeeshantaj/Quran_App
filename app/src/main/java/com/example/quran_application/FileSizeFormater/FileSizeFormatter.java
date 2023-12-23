package com.example.quran_application.FileSizeFormater;

public class FileSizeFormatter {

    public static String getFileSizeString(long fileSizeInBytes) {
        if (fileSizeInBytes < 1024) {
            return fileSizeInBytes + " B";
        } else if (fileSizeInBytes < 1024 * 1024) {
            return String.format("%.2f KB", fileSizeInBytes / (double) 1024);
        } else if (fileSizeInBytes < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", fileSizeInBytes / (double) (1024 * 1024));
        } else {
            return String.format("%.2f GB", fileSizeInBytes / (double) (1024 * 1024 * 1024));
        }
    }
}
