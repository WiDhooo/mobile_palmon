package com.example.project_palm_on;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class KalkulasiAdapter extends RecyclerView.Adapter<KalkulasiAdapter.ViewHolder> {
    private Context context;
    private List<Kalkulasi> kalkulasiList;

    public KalkulasiAdapter(Context context, List<Kalkulasi> kalkulasiList) {
        this.context = context;
        this.kalkulasiList = kalkulasiList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_kalkulasi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Kalkulasi kalkulasi = kalkulasiList.get(position);
        holder.tgl_panen.setText(String.format("Tanggal Panen: %s", kalkulasi.getTgl_panen()));
        holder.totalHasilBersih.setText(String.format("Rp. %,d", (int) kalkulasi.getTotalHasilBersih()));
        holder.totalPendapatan.setText(String.format("Rp. %,d", (int) kalkulasi.getTotalPendapatan()));
        holder.totalPengeluaran.setText(String.format("Rp. %,d", (int) kalkulasi.getTotalPengeluaran()));

        // Set listener untuk tombol hasil_button
        holder.hasilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil activity hasil_kalkulasi dan kirim ID dari kalkulasi
                Intent intent = new Intent(context, hasil_kalkulasi.class);
                intent.putExtra("KALKULASI_ID", kalkulasi.getId()); // Pastikan Anda memiliki method getId() di class Kalkulasi
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return kalkulasiList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tgl_panen, totalHasilBersih, totalPendapatan, totalPengeluaran, hasilButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tgl_panen = itemView.findViewById(R.id.tgl_panen);
            totalHasilBersih = itemView.findViewById(R.id.total_hasil_bersih);
            totalPendapatan = itemView.findViewById(R.id.total_pendapatan);
            totalPengeluaran = itemView.findViewById(R.id.total_pengeluaran);
            hasilButton = itemView.findViewById(R.id.hasil_button); // Inisialisasi hasilButton
        }
    }
}
