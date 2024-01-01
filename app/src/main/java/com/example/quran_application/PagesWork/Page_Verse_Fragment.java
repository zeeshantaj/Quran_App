package com.example.quran_application.PagesWork;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.quran_application.Chapter_response.Quran_Api_Service;
import com.example.quran_application.R;
import com.example.quran_application.verses_response.Verse;
import com.example.quran_application.verses_response.VersesActivity;
import com.example.quran_application.verses_response.Verses_Response;
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Page_Verse_Fragment extends Fragment {

    private List<Verse> verseList;
    private ViewPager2 myViewPager2;
    private List<String> textArray;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_verse_fragment,container,false);

        myViewPager2 = view.findViewById(R.id.myViewPager2);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.quran.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Quran_Api_Service service = retrofit.create(Quran_Api_Service.class);


        Call<Verses_Response> call = service.getVersesForChapter(36);

        call.enqueue(new Callback<Verses_Response>() {
            @Override
            public void onResponse(Call<Verses_Response> call, Response<Verses_Response> response) {
                if (response.isSuccessful()) {
                    Verses_Response versesResponse = response.body();
                    verseList = versesResponse.getVerses();

                    //testing work
                    StringBuilder concatenatedString = new StringBuilder();

                    // Loop through the verseList and concatenate strings
                    for (Verse verse : verseList) {
                        String vrStr = verse.getText_uthmani();
                        concatenatedString.append(vrStr).append(" "); // You can modify the concatenation logic as needed
                    }

                    // Now you have the concatenated string
                    String finalString = concatenatedString.toString();

                    // Use the finalString according to your needs
                    System.out.println(finalString);
 // testing work


                    StringBuilder pageText = new StringBuilder();
                    List<String> pages = new ArrayList<>();
                    int lineCount = 0;

                    for (Verse verse : verseList) {
                        String verseText = verse.getText_uthmani();
                        pageText.append(verseText).append("\n");
                        lineCount++;

                        if (lineCount >= 13) {
                            pages.add(pageText.toString());
                            pageText = new StringBuilder();
                            lineCount = 0;
                        }
                    }

                    if (pageText.length() > 0) {
                        pages.add(pageText.toString());
                    }
                    // Create the adapter and set it to the ViewPager2
                    VersePagerAdapter adapter = new VersePagerAdapter(pages);
                    myViewPager2.setAdapter(adapter);

                    // Set page transformer
                    BookFlipPageTransformer2 bookFlipPageTransformer = new BookFlipPageTransformer2();
                    bookFlipPageTransformer.setEnableScale(true);
                    bookFlipPageTransformer.setScaleAmountPercent(10f);
                    myViewPager2.setPageTransformer(bookFlipPageTransformer);

                } else {
                    Toast.makeText(getActivity(), "Exception " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Verses_Response> call, Throwable t) {
                Toast.makeText(getActivity(), "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.e("MyApp", "Error ->" + t.getLocalizedMessage());
            }
        });



        return view;
    }


    private int countLines(String text) {
        String[] lines = text.split("\r\n|\r|\n");
        return lines.length;
    }
}
