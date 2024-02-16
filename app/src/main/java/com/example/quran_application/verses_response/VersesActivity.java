package com.example.quran_application.verses_response;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.quran_application.Chapter_response.Quran_Api_Service;
import com.example.quran_application.CombineVerses;
import com.example.quran_application.Database.RoomDB;
import com.example.quran_application.Downloads.SavedVerseAdapter;
import com.example.quran_application.Model.SharedViewModel;
import com.example.quran_application.R;
import com.example.quran_application.Adaper.VersesCountAdapter;
import com.example.quran_application.Translation.MySharedPreference;
import com.example.quran_application.Translation.Translation;
import com.example.quran_application.Translation.Translation_Response;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VersesActivity extends AppCompatActivity {
    private RecyclerView recyclerView,recyclerVerseCount;
    private Verses_Adapter versesAdapter;
    private VersesCountAdapter versesCountAdapter;
    private List<CombineVerses> combineVersesList;
    private Toolbar toolbar;

    private List<Verse> verseList;
    public static List<Translation> translationList;

    private LinearLayoutManager layoutManagerVerse,layoutManagerVerseCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verses);
        toolbar=findViewById(R.id.verseToolbar);

        combineVersesList = new ArrayList<>();
        recyclerVerseCount =findViewById(R.id.verseCountRecycler);
        layoutManagerVerseCount = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        recyclerVerseCount.setLayoutManager(layoutManagerVerseCount);


        recyclerView =findViewById(R.id.versesRecycler);
        layoutManagerVerse = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManagerVerse);

        //Intent savedIntent = getIntent();

        Intent  intent= getIntent();
        boolean isSaved = intent.getBooleanExtra("savedVerse",true);
        int savedChapterNumber = intent.getIntExtra("savedChapterNumber",0);
        int chapterNumber = intent.getIntExtra("chapterNumber",0);
        int juzNumber = intent.getIntExtra("juzNumber",0);
        String arabicName = intent.getStringExtra("chapterName");
        String arabicName1 = intent.getStringExtra("juzName");
        boolean isChapter = intent.getBooleanExtra("isChapter",true);

        if (isSaved){
              SavedVersesData(savedChapterNumber);

        }
        else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.quran.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Quran_Api_Service service = retrofit.create(Quran_Api_Service.class);

            Call<Verses_Response> call;

            if (isChapter){
                call = service.getVersesForChapter(chapterNumber);
            }
            else {
                call = service.getVersesBtJuz(juzNumber);
            }
            call.enqueue(new Callback<Verses_Response>() {
                @Override
                public void onResponse(Call<Verses_Response> call, Response<Verses_Response> response) {
                    if (response.isSuccessful()) {
                        Verses_Response versesResponse = response.body();
                        verseList = versesResponse.getVerses();
                        versesAdapter = new Verses_Adapter(verseList); // Update the data in the adapter
                        recyclerView.setAdapter(versesAdapter);
                        for (Verse verse : verseList){

                            Log.e("MyApp","Surah "+verse.getText_uthmani());

                            CombineVerses combineVerses = new CombineVerses();
                            if (isChapter) {
                                combineVerses.setChapterName(arabicName);
                            }
                            else {
                                combineVerses.setChapterName(arabicName1);
                            }
                            combineVerses.setVerseKey(verse.getVerse_key());
                            combineVersesList.add(combineVerses);
                        }



                    } else {
                        Toast.makeText(VersesActivity.this, "Exception "+response.message(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<Verses_Response> call, Throwable t) {
                    Toast.makeText(VersesActivity.this, "Error "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("MyApp","Error ->"+t.getLocalizedMessage());
                }
            });





            // mySharedPreferences.saveValue("translation_key", 158);
            // Retrieving a value
//            Call<Translation_Response> call1 = service.getTranslationForChapter(chapterNumber);
//            call1.enqueue(new Callback<Translation_Response>() {
//                @Override
//                public void onResponse(Call<Translation_Response> call, Response<Translation_Response> response) {
//
//                }
//
//                @Override
//                public void onFailure(Call<Translation_Response> call, Throwable t) {
//
//
//                }
//            });



            MySharedPreference mySharedPreferences = new MySharedPreference(this);
            int id = mySharedPreferences.getValue("translation_key", 0);
            Log.d("MainActivity", "Value retrieved in surah : " + id);
            Call<Translation_Response> call2 = service.getTranslationForChapter1(id,chapterNumber);
            call2.enqueue(new Callback<Translation_Response>() {
                @Override
                public void onResponse(Call<Translation_Response> call, Response<Translation_Response> response) {
                    if (response.isSuccessful()){

                        String url = call.request().url().toString();
                        Log.e("MyApp","url ->"+url);
                        Translation_Response translationResponse = response.body();
                        translationList = translationResponse.getTranslation();

                    }
                    else {
                        Toast.makeText(VersesActivity.this, "Error"+response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Translation_Response> call, Throwable t) {
                    String url = call.request().url().toString();
                    Log.e("MyApp","url ->"+url);
                    Toast.makeText(VersesActivity.this, "Error "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            versesCountAdapter = new VersesCountAdapter(combineVersesList);
            recyclerVerseCount.setAdapter(versesCountAdapter);
            versesCountAdapter.notifyDataSetChanged();

        }
        setToolbar();
    }

    private void SavedVersesData(int chapterId){

        RoomDB roomDB = RoomDB.getInstance(this);
        List<Verse> verseList1 =roomDB.mainDAO().getALLVerseForChapter(chapterId);;

        for (Verse verse : verseList1){
        Log.e("MyApp","verseSize"+verseList1.size());
        Log.e("MyApp","verse"+verse.getVerse_key());
        Log.e("MyApp","verse"+verse.getText_uthmani());
        Log.e("MyApp","verse"+verse.getId());

        }
        SavedVerseAdapter adapter = new SavedVerseAdapter(verseList1);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //actionBar.setHomeAsUpIndicator();
         //   actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);

        MenuItem item = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("type verse number here");
        //searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    performSearch(query);
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                  //  performSearch(newText);
                    return true;
                }
            });


        return true;
    }



    private void performSearch(String query) {
//        List<Verse> searchResults = new ArrayList<>();
//        List<Verse> nonSearchResults = new ArrayList<>();
//        List<CombineVerses> CombineSearchResult = new ArrayList<>();
//        List<CombineVerses> nonCombineSearchResults = new ArrayList<>();
//
//        for (Verse verse : verseList) {
//            if (verse.getVerse_key().toLowerCase().contains(query.toLowerCase())) {
//                searchResults.add(verse);
//            } else {
//                nonSearchResults.add(verse);
//            }
//        }
//        searchResults.addAll(nonSearchResults);
//
//        versesAdapter.updateList(searchResults); // Update the adapter with the updated list
//
//        for (CombineVerses combineVerses: combineVersesList){
//            if (combineVerses.getVerseKey().toLowerCase().contains(query.toLowerCase())){
//                CombineSearchResult.add(combineVerses);
//            }
//            else {
//                nonCombineSearchResults.add(combineVerses);
//            }
//        }
//
//        CombineSearchResult.addAll(nonCombineSearchResults);
//        versesCountAdapter.updateList(CombineSearchResult);

        int positionToScroll = -1;
        for (int i = 0; i < verseList.size(); i++) {
            if (verseList.get(i).getVerse_key().toLowerCase().contains(query.toLowerCase())) {
                positionToScroll = i;
                break; // Stop searching after finding the first match
            }
        }

// If a matching item was found, scroll to it
        if (positionToScroll != -1) {
            // Use RecyclerView's scrollToPosition method to scroll to the specific position
            LinearSmoothScroller smoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
                @Override
                protected int getVerticalSnapPreference() {
                    return LinearSmoothScroller.SNAP_TO_START;
                }
            };

            // Set the target position for smooth scrolling
            smoothScroller.setTargetPosition(positionToScroll);

            // Start smooth scrolling
            layoutManagerVerse.startSmoothScroll(smoothScroller);
        } else {
            // Handle the case where there is no match
            // For example, display a message to the user
            Toast.makeText(this, "No matching item found", Toast.LENGTH_SHORT).show();
        }

        //todo for combileVerse Item

        int positionToCombineScroll = -1;
        for (int i = 0; i < verseList.size(); i++) {
            if (verseList.get(i).getVerse_key().toLowerCase().contains(query.toLowerCase())) {
                positionToCombineScroll = i;
                break; // Stop searching after finding the first match
            }
        }

        if (positionToCombineScroll != -1) {
            LinearSmoothScroller smoothScroller = new LinearSmoothScroller(recyclerVerseCount.getContext()) {
                @Override
                protected int getVerticalSnapPreference() {
                    return LinearSmoothScroller.SNAP_TO_START;
                }
            };

            // Set the target position for smooth scrolling
            smoothScroller.setTargetPosition(positionToCombineScroll);

            // Start smooth scrolling
            layoutManagerVerseCount.startSmoothScroll(smoothScroller);
        } else {
            Toast.makeText(this, "No matching item found", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            getOnBackPressedDispatcher().onBackPressed();
        }
        return false;
    }
}