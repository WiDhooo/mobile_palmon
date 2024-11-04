package com.example.project_palm_on;

public class Kalkulasi {
    private String tgl_panen;
    private double totalHasilBersih;
    private double totalPendapatan;
    private double totalPengeluaran;
    private String id;

    public Kalkulasi(String tgl_panen, double totalHasilBersih, double totalPendapatan, double totalPengeluaran, String id) {
        this.tgl_panen = tgl_panen;
        this.totalHasilBersih = totalHasilBersih;
        this.totalPendapatan = totalPendapatan;
        this.totalPengeluaran = totalPengeluaran;
        this.id = id;
    }

    public String getTgl_panen() {
        return tgl_panen;
    }

    public double getTotalHasilBersih() {
        return totalHasilBersih;
    }

    public double getTotalPendapatan() {
        return totalPendapatan;
    }

    public double getTotalPengeluaran() {
        return totalPengeluaran;
    }

    public String getId(){ return id;}
}
