package com.example.quran_application.Translation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quran_application.Model.SharedViewModel;
import com.example.quran_application.R;

import java.util.List;

public class TranslationListAdapter extends RecyclerView.Adapter<TranslationListAdapter.ViewHolder> {

    private List<TranslationList> translationsList;

    public TranslationListAdapter(List<TranslationList> translationsList) {
        this.translationsList = translationsList;
    }

    @NonNull
    @Override
    public TranslationListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.translation_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TranslationListAdapter.ViewHolder holder, int position) {
        TranslationList translation = translationsList.get(position);
        holder.languageName.setText(translation.getLanguage_name());
        holder.authorName.setText(translation.getAuthor_name());
        holder.type.setText(translation.getName());

        holder.itemView.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();

            int id = translation.getId();
            Toast.makeText(holder.itemView.getContext(), "Clicked "+id, Toast.LENGTH_SHORT).show();

            MySharedPreference mySharedPreferences = new MySharedPreference(holder.itemView.getContext());
            // Saving a value
            // Overriding a value
            mySharedPreferences.overrideValue("translation_key",id);


//            if (pos == translation.getId()){
//                Toast.makeText(holder.itemView.getContext(), "Clicked "+pos + translation.getId(), Toast.LENGTH_SHORT).show();
//            }
        });


    }

    @Override
    public int getItemCount() {
        return translationsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView languageName,authorName,type;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            languageName = itemView.findViewById(R.id.languageNameTxt);
            authorName = itemView.findViewById(R.id.authorNameTxt);
            type = itemView.findViewById(R.id.translationType);

        }
    }
}
