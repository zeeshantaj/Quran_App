package com.example.quran_application.Adaper;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quran_application.Juzs;
import com.example.quran_application.JuzsList;
import com.example.quran_application.R;
import com.example.quran_application.verses_response.VersesActivity;

import java.util.List;

public class JuzsAdapter extends RecyclerView.Adapter<JuzsAdapter.ViewHolder> {

    private List<JuzsList> juzsLists;

    public JuzsAdapter(List<JuzsList> juzsLists) {
        this.juzsLists = juzsLists;
    }

    @NonNull
    @Override
    public JuzsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.juzs_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JuzsAdapter.ViewHolder holder, int position) {

        JuzsList juzsList = juzsLists.get(position);
        holder.juzsNumber.setText(juzsList.getJuz_number());
        holder.verseStartToEnd.setText("Verses: "+juzsList.getFirst_verse_id()+" - "+juzsList.getLast_verse_id());

        String[] strings = holder.itemView.getContext().getResources().getStringArray(R.array.juzNameArray);

        if (position < strings.length){
            holder.juzName.setText(strings[position]);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), VersesActivity.class);
                intent.putExtra("isChapter", false);
                intent.putExtra("savedVerse", false);
                intent.putExtra("juzNumber",juzsList.getId());
                intent.putExtra("juzName",strings[position]);
                v.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return juzsLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView juzsNumber,verseStartToEnd,juzName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            juzsNumber = itemView.findViewById(R.id.juzNumber);
            verseStartToEnd = itemView.findViewById(R.id.verseStartToLast);
            juzName = itemView.findViewById(R.id.juzName);


        }
    }
}
