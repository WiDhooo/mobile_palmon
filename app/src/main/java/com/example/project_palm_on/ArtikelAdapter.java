package com.example.project_palm_on;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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

        // Batasi deskripsi hingga 65 kata
        String truncatedDescription = truncateText(artikel.getDescription(), 65);
        holder.descriptionTextView.setText(truncatedDescription);

        // Format waktu
        String timeAgo = TimeUtils.getTimeAgo(artikel.getTimeUpload());
        holder.timeUploadTextView.setText(timeAgo);

        Glide.with(context).load(artikel.getImageURL()).into(holder.articleImageView);

        // Klik ikon titik tiga untuk membuka isi artikel
        holder.optionsIcon.setOnClickListener(v -> {
            Intent intent = new Intent(context, isi_artikel.class);
            intent.putExtra("author", artikel.getAuthor());
            intent.putExtra("title", artikel.getTitle());
            intent.putExtra("content", artikel.getDescription());
            intent.putExtra("time", artikel.getTimeUpload());
            intent.putExtra("image", artikel.getImageURL());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return artikelList.size();
    }

    public static class ArtikelViewHolder extends RecyclerView.ViewHolder {

        TextView authorTextView, titleTextView, descriptionTextView, timeUploadTextView;
        ImageView articleImageView, optionsIcon;

        public ArtikelViewHolder(@NonNull View itemView) {
            super(itemView);
            authorTextView = itemView.findViewById(R.id.nama_author_artikel_1);
            titleTextView = itemView.findViewById(R.id.judul_artikel_page_1);
            descriptionTextView = itemView.findViewById(R.id.deskripsi_artikel_1);
            timeUploadTextView = itemView.findViewById(R.id.waktu_upload_artikel_1);
            articleImageView = itemView.findViewById(R.id.image_artikel_1);
            optionsIcon = itemView.findViewById(R.id.icon_titik_tiga_artikel_1);
        }
    }

    private static String truncateText(String text, int wordLimit) {
        String[] words = text.split("\\s+");
        if (words.length > wordLimit) {
            StringBuilder truncated = new StringBuilder();
            for (int i = 0; i < wordLimit; i++) {
                truncated.append(words[i]).append(" ");
            }
            return truncated.toString().trim() + " ...";
        }
        return text;
    }
}
