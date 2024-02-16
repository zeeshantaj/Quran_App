package com.example.quran_application;

import static com.example.quran_application.Audio.AudioFragment.performSearchForAudio;
import static com.example.quran_application.Para_Fragment.performSearchForJuz;
import static com.example.quran_application.Surah_Fragment.performSearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.quran_application.Audio.AudioFragment;
import com.example.quran_application.Downloads.Download_Fragment;
import com.example.quran_application.Fragment.Progress_Download_Fragment;
import com.example.quran_application.Model.SharedViewModel;
import com.example.quran_application.Tafseer.Tafsir_Fragment;
import com.example.quran_application.Translation.Translation_Select_Fragment;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {


    private MaterialButton paraBtn,surahBtn,downloadBtn,audio,tafseerBtn;

    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    private SharedViewModel sharedViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);



        paraBtn = findViewById(R.id.paraBtn);
        audio = findViewById(R.id.audioBtn);
        surahBtn = findViewById(R.id.surahBtn);
        tafseerBtn = findViewById(R.id.tafseerBtn);
        downloadBtn = findViewById(R.id.downloadBtn);




        fragmentManager = getSupportFragmentManager();
        currentFragment = fragmentManager.findFragmentById(R.id.parentFrameLayout);
        currentFragment = new Surah_Fragment();
        setFragment(currentFragment);
        paraBtn.setOnClickListener(v -> setFragment(new Para_Fragment()));
        surahBtn.setOnClickListener(v -> setFragment(new Surah_Fragment()));
        downloadBtn.setOnClickListener(v -> setFragment(new Download_Fragment()));
        tafseerBtn.setOnClickListener(v -> {
            setFragment(new Tafsir_Fragment());
        });

        audio.setOnClickListener(v -> setFragment(new AudioFragment()));




      //  setColor();
        setToolbar();
       // CheckInterNetAccess();
    }

    private void setColor() {

        int color = ContextCompat.getColor(this,R.color.lightBlue);
        int defaultTextColor = ContextCompat.getColor(this,R.color.white);

        int defaultColor = ContextCompat.getColor(this,R.color.blue);
        int textColor = ContextCompat.getColor(this,R.color.black);

        long animationDuration = 500; // 500 milliseconds (adjust as needed)

        if (currentFragment instanceof Surah_Fragment) {
            animateButtonColorChange(surahBtn, color, textColor, animationDuration);
        } else {
            animateButtonColorChange(surahBtn, defaultColor, defaultTextColor, animationDuration);
        }

        if (currentFragment instanceof Para_Fragment) {
            animateButtonColorChange(paraBtn, color, textColor, animationDuration);
        } else {
            animateButtonColorChange(paraBtn, defaultColor, defaultTextColor, animationDuration);
        }

        if (currentFragment instanceof Download_Fragment) {
            animateButtonColorChange(downloadBtn, color, textColor, animationDuration);
        } else {
            animateButtonColorChange(downloadBtn, defaultColor, defaultTextColor, animationDuration);
        }
        if (currentFragment instanceof AudioFragment) {
            animateButtonColorChange(audio, color, textColor, animationDuration);
        } else {
            animateButtonColorChange(audio, defaultColor, defaultTextColor, animationDuration);
        }
        if (currentFragment instanceof Tafsir_Fragment) {
            animateButtonColorChange(audio, color, textColor, animationDuration);
        } else {
            animateButtonColorChange(audio, defaultColor, defaultTextColor, animationDuration);
        }

//        if (currentFragment instanceof Surah_Fragment){
//            surahBtn.setBackgroundColor(color);
//            surahBtn.setTextColor(textColor);
//        }
//        else {
//            surahBtn.setBackgroundColor(defaultColor);
//            surahBtn.setTextColor(defaultTextColor);
//        }
//         if (currentFragment instanceof Para_Fragment){
//            paraBtn.setBackgroundColor(color);
//             paraBtn.setTextColor(textColor);
//        }
//         else {
//             paraBtn.setBackgroundColor(defaultColor);
//             paraBtn.setTextColor(defaultTextColor);
//         }
//         if (currentFragment instanceof Download_Fragment){
//            downloadBtn.setBackgroundColor(color);
//             downloadBtn.setTextColor(textColor);
//        }
//         else {
//             downloadBtn.setTextColor(defaultTextColor);
//             downloadBtn.setBackgroundColor(defaultColor);
//         }
    }

    private void animateButtonColorChange(Button button, int backgroundColor, int textColor, long duration) {
        ObjectAnimator backgroundAnimator = ObjectAnimator.ofArgb(button, "backgroundColor", button.getCurrentTextColor(), backgroundColor);
        backgroundAnimator.setDuration(duration);

        ObjectAnimator textAnimator = ObjectAnimator.ofArgb(button, "textColor", button.getCurrentTextColor(), textColor);
        textAnimator.setDuration(duration);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(backgroundAnimator, textAnimator);
        animatorSet.start();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.parentFrameLayout,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        currentFragment = fragment;
        setColor();
        setToolbar();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);

        MenuItem item = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("type number here");
        //searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                try {
                    int convertedQuery = Integer.parseInt(query);

                    if (currentFragment instanceof Surah_Fragment){
                        performSearch(convertedQuery);
                    }
                    else if (currentFragment instanceof Para_Fragment){
                         performSearchForJuz(convertedQuery);
                    }
                    else if (currentFragment instanceof AudioFragment){
                        performSearchForAudio(convertedQuery);
                    }
                    else if (currentFragment instanceof Download_Fragment) {

                    }
                }
                catch (NumberFormatException e){
                    Toast.makeText(MainActivity.this, "Error "+e.getLocalizedMessage()+"\nEnter Valid Input", Toast.LENGTH_SHORT).show();

                }
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.translationList){
            Toast.makeText(this, "translation", Toast.LENGTH_SHORT).show();


            Translation_Select_Fragment fragment = new Translation_Select_Fragment();
            fragment.show(getSupportFragmentManager(), fragment.getTag());

//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.parentFrameLayout, fragment);  //here, android.R.id.content is a view on which your fragment's view is replaced
//            transaction.addToBackStack(null);
//            transaction.commit();

         //   TranslationBottomSheetFragment bottomSheetFragment = new TranslationBottomSheetFragment();
        }
        if (id == R.id.taafsirList){
            Tafsir_Fragment fragment = new Tafsir_Fragment();
            fragment.show(getSupportFragmentManager(), fragment.getTag());
            Toast.makeText(this, "tafsir", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.downloadVerse){
            FragmentManager fragmentManager= getSupportFragmentManager();;
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.parentFrameLayout,new Progress_Download_Fragment());
            transaction.addToBackStack(null);
            transaction.commit();
        }

        return false;
    }


    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.chapterToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

            if (currentFragment instanceof Surah_Fragment) {
                actionBar.setTitle("Surah");
            }
            if (currentFragment instanceof Para_Fragment) {
                actionBar.setTitle("Juz");
            }
            if (currentFragment instanceof AudioFragment) {
                actionBar.setTitle("Audio");
            }
            if (currentFragment instanceof Download_Fragment) {
                actionBar.setTitle("Download");
            }
            if (currentFragment instanceof Tafsir_Fragment) {
                actionBar.setTitle("Tafsir");
            }

        }
    }

//    public void CheckInterNetAccess(){
//        if (NetworkUtils.isNetworkAvailable(this)){
//            NetworkUtils.hasInternetAccess(new InternetAccessCallback() {
//                @Override
//                public void onInternetAccessResult(boolean hasInternetAccess) {
//                    if (hasInternetAccess){
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                setFragment(new Surah_Fragment());
//                            }
//                        });
//                    }
//                    else {
//                        setFragment(new Download_Fragment());
//                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "You have no internet connection", Snackbar.LENGTH_INDEFINITE);
//                        snackbar.setActionTextColor(getResources().getColor(R.color.lightBlue));
//                        snackbar.setAction("Check Again", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                CheckInterNetAccess();
//                                snackbar.dismiss();
//
//                            }
//                        });
//                        snackbar.show();
//                    }
//                }
//            });
//        }
//        else {
//            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Make sure you are connected to the internet", Snackbar.LENGTH_INDEFINITE);
//            snackbar.setActionTextColor(getResources().getColor(R.color.lightBlue));
//            setFragment(new Download_Fragment());
//            snackbar.setAction("DISMISS", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    snackbar.dismiss();
//                }
//            });
//            snackbar.show();
//        }
//    }

}