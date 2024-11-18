package com.example.project_palm_on;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.ViewHolder> {

    private List<GuideItem> guideItems;
    private Context context;

    public GuideAdapter(Context context, List<GuideItem> guideItems) {
        this.context = context;
        this.guideItems = guideItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guide, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GuideItem guideItem = guideItems.get(position);

        // Batasi panjang teks judul dan deskripsi
        holder.title.setText(limitText(guideItem.getTitle(), 30)); // Batas judul: 30 karakter
        holder.description.setText(limitText(guideItem.getDescription(), 100)); // Batas deskripsi: 100 karakter

        // Load image dengan Glide
        if (guideItem.getImageURL() != null && !guideItem.getImageURL().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(guideItem.getImageURL())
                    .apply(new RequestOptions().placeholder(R.drawable.carousel_sawit_1))
                    .error(R.drawable.carousel_sawit_1)
                    .into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.carousel_sawit_1);
        }

        // Set click listener untuk membuka isi_guide
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, isi_guide.class);
            intent.putExtra("title", guideItem.getTitle());
            intent.putExtra("image", guideItem.getImageURL());
            intent.putExtra("description", guideItem.getDescription());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return guideItems != null ? guideItems.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.guide_title);
            description = itemView.findViewById(R.id.guide_description);
            image = itemView.findViewById(R.id.guide_image);
        }
    }

    private String limitText(String text, int maxLength) {
        if (text != null && text.length() > maxLength) {
            return text.substring(0, maxLength) + "..."; // Tambahkan elipsis "..."
        }
        return text;
    }
}
