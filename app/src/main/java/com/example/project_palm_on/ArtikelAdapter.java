package com.example.project_palm_on;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ArtikelAdapter extends RecyclerView.Adapter<ArtikelAdapter.ArtikelViewHolder> {

    private Context context;
    private List<item_artikel> artikelList;

    public ArtikelAdapter(Context context, List<item_artikel> artikelList) {
        this.context = context;
        this.artikelList = artikelList;
    }

    @NonNull
    @Override
    public ArtikelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_artikel, parent, false);
        return new ArtikelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtikelViewHolder holder, int position) {
        item_artikel artikel = artikelList.get(position);
        holder.authorTextView.setText(artikel.getAuthor());
        holder.titleTextView.setText(artikel.getTitle());
        holder.descriptionTextView.setText(artikel.getDescription());
        holder.timeUploadTextView.setText(artikel.getTimeUpload());
        holder.articleImageView.setImageResource(artikel.getImageResourceId());
        holder.iconImageView.setImageResource(artikel.getIconResourceId());
    }

    @Override
    public int getItemCount() {
        return artikelList.size();
    }

    public static class ArtikelViewHolder extends RecyclerView.ViewHolder {

        TextView authorTextView, titleTextView, descriptionTextView, timeUploadTextView;
        ImageView articleImageView, iconImageView;

        public ArtikelViewHolder(@NonNull View itemView) {
            super(itemView);
            authorTextView = itemView.findViewById(R.id.nama_author_artikel_1);
            titleTextView = itemView.findViewById(R.id.judul_artikel_page_1);
            descriptionTextView = itemView.findViewById(R.id.deskripsi_artikel_1);
            timeUploadTextView = itemView.findViewById(R.id.waktu_upload_artikel_1);
            articleImageView = itemView.findViewById(R.id.image_artikel_1);
            iconImageView = itemView.findViewById(R.id.icon_titik_tiga_artikel_1);
        }
    }
}
