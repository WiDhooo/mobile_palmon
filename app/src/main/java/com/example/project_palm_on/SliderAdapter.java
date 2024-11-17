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

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    private Context context;
    private List<SliderItem> sliderItems;

    public SliderAdapter(Context context, List<SliderItem> sliderItems) {
        this.context = context;
        this.sliderItems = sliderItems;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_artikel_slider_home, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        SliderItem item = sliderItems.get(position);
        holder.textViewTitle.setText(item.getTitle());
        String timeAgo = TimeUtils.getTimeAgo(item.getTimeUpload());
        holder.textViewDate.setText(timeAgo);

        // Use Glide to load the image URL into the ImageView
        Glide.with(context).load(item.getImageURL()).into(holder.imageViewThumbnail);

        // Add click listener to open 'isi_artikel' when slider item is clicked
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, isi_artikel.class);
            intent.putExtra("author", item.getAuthor());
            intent.putExtra("title", item.getTitle());
            intent.putExtra("content", item.getDescription());
            intent.putExtra("time", item.getTimeUpload());
            intent.putExtra("image", item.getImageURL());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    public static class SliderViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewThumbnail;
        TextView textViewTitle, textViewDate;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewThumbnail = itemView.findViewById(R.id.imageViewThumbnail);
            textViewTitle = itemView.findViewById(R.id.judul_artikel_carousel_1);
            textViewDate = itemView.findViewById(R.id.textViewDate);
        }
    }
}
