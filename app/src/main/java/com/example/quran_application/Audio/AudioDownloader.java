package com.example.quran_application.Audio;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class AudioDownloader {
    private final Context context;

    public AudioDownloader(Context context) {
        this.context = context;
    }
    public void downloadAudio(String url,String chapterNumber,String chapterName, final DownloadListener listener) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle failure
                if (listener != null) {
                    listener.onDownloadFailed(e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
//                if (body != null) {
//                    try (InputStream inputStream = body.byteStream()) {
//                        String destinationPath = context.getFilesDir() + File.separator + "audio.mp3";
//                        File destinationFile = new File(destinationPath);
//                        FileOutputStream outputStream = new FileOutputStream(destinationFile);
//                        byte[] buffer = new byte[4 * 1024];
//                        int read;
//                        while ((read = inputStream.read(buffer)) != -1) {
//                            outputStream.write(buffer, 0, read);
//                        }
//                        outputStream.flush();
//                        outputStream.close();
//
//                        if (listener != null) {
//                            listener.onDownloadCompleted(destinationPath);
//                            Handler handler = new Handler(Looper.getMainLooper());
//                            handler.post(() -> {
//                                Toast.makeText(context, "Audio downloaded"+destinationPath, Toast.LENGTH_SHORT).show();
//                                Log.e("MyApp","destination"+destinationPath);
//                            });
//                        }
//                    } catch (IOException e) {
//                        // Handle IOException
//                        if (listener != null) {
//                            listener.onDownloadFailed(e.getMessage());
//                        }
//                    }
//                }
                if (body != null) {
                    try (InputStream inputStream = body.byteStream()) {


                        String fileName = chapterNumber + "_" + chapterName + ".mp3";

                        File publicDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "Quran_App_Downloaded_Surah");

                        if (!publicDirectory.exists()) {
                            if (!publicDirectory.mkdirs()) {
                                // Directory creation failed
                                if (listener != null) {
                                    listener.onDownloadFailed("Failed to create directory");
                                }
                                return;
                            }
                        }

                        File destinationFile = new File(publicDirectory, fileName);
                        FileOutputStream outputStream = new FileOutputStream(destinationFile);
                        byte[] buffer = new byte[4 * 1024];
                        int read;
                        while ((read = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, read);
                        }
                        outputStream.flush();
                        outputStream.close();

                        if (listener != null) {
                            listener.onDownloadCompleted(destinationFile.getAbsolutePath());

                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.post(() -> {
                                Toast.makeText(context, "Audio downloaded: " + destinationFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                                Log.e("MyApp", "destination: " + destinationFile.getAbsolutePath());
                            });
                        }
                    } catch (IOException e) {
                        // Handle IOException
                        if (listener != null) {
                            listener.onDownloadFailed(e.getMessage());
                        }
                    }
                }

            }
        });
    }

    public interface DownloadListener {
        void onDownloadCompleted(String filePath);

        void onDownloadFailed(String errorMessage);
    }
}
