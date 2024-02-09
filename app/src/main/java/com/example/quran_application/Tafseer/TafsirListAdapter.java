package com.example.quran_application.Tafseer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quran_application.R;

import java.util.List;

public class TafsirListAdapter extends RecyclerView.Adapter<TafsirListAdapter.ViewHolder> {
    private List<Tafsirs> tafsirs;

    public TafsirListAdapter(List<Tafsirs> tafsirs) {
        this.tafsirs = tafsirs;
    }

    @NonNull
    @Override
    public TafsirListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tafsir_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TafsirListAdapter.ViewHolder holder, int position) {
        Tafsirs tafsirs1 = tafsirs.get(position);
        holder.tafsirName.setText(tafsirs1.getName());
        holder.authorName.setText(tafsirs1.getAuthor_name());
        holder.languageName.setText(tafsirs1.getLanguage_name());
        holder.slug.setText(tafsirs1.getSlug());
    }

    @Override
    public int getItemCount() {
        return tafsirs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tafsirName,authorName,languageName,slug;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tafsirName = itemView.findViewById(R.id.tafsirName);
            slug = itemView.findViewById(R.id.slug);
            authorName = itemView.findViewById(R.id.tafsirAuthorName);
            languageName = itemView.findViewById(R.id.tafsirLang);
        }
    }
}
