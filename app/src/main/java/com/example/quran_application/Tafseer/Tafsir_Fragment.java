package com.example.quran_application.Tafseer;

import android.app.Dialog;
import android.app.TaskInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quran_application.Chapter_response.Quran_Api_Service;
import com.example.quran_application.R;
import com.example.quran_application.Translation.TranslationListAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tafsir_Fragment extends BottomSheetDialogFragment {

    private View view;
    private RecyclerView recyclerView;

    private TafsirListAdapter adapter;
    private BottomSheetBehavior<View> bottomSheetBehavior;
    BottomSheetDialog dialog;
    private List<Tafsirs> tafsirsList;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        return dialog;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tafseer_fragment,container,false);
        recyclerView = view.findViewById(R.id.tafsir_select_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
        CoordinatorLayout coordinatorLayout = dialog.findViewById(R.id.bottomSheetLayoutTafsir);
        assert coordinatorLayout != null;
        coordinatorLayout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);

        ImageView dissmissBtn = dialog.findViewById(R.id.dismissButtonTafsir);
        if (dissmissBtn != null) {
            dissmissBtn.setOnClickListener(v -> {
                dialog.dismiss();
            });
        }

        tafsirsList = new ArrayList<>();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.quran.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Quran_Api_Service service = retrofit.create(Quran_Api_Service.class);

        Call<Tasfirs_Info> call = service.getTafsirs();

        call.enqueue(new Callback<Tasfirs_Info>() {
            @Override
            public void onResponse(Call<Tasfirs_Info> call, Response<Tasfirs_Info> response) {
                if (response.isSuccessful()){
                    Tasfirs_Info tafsirs = response.body();
                    Log.e("MyApp","res" +call.request().url());
                    Log.e("MyApp","res" +tafsirs);

                    tafsirsList = tafsirs.getTafsirs();
                    adapter = new TafsirListAdapter(tafsirsList);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Tasfirs_Info> call, Throwable t) {
                Toast.makeText(getActivity(), "Error "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
