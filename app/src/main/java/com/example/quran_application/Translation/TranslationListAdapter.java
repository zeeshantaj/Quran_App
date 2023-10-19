package com.example.quran_application.Translation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
