package com.example.project_palm_on;

public class KalkulasiResponse {
    private double totalHasilBersih;
    private double totalPendapatan;
    private double totalPengeluaran;
    private String tgl_panen;
    private double berat_total_tbs;
    private double harga_tbs;
    private double potongan_timbangan;
    private double upah_panen;
    private double upah_transportasi;
    private double biaya_lainnya;
    private int berat_bersih;

    // Getter dan Setter
    public String getTglPanen() { return tgl_panen; }
    public double getBeratTotalTbs() { return berat_total_tbs; }
    public double getHargaTbs() { return harga_tbs; }
    public double getPotonganTimbangan() { return potongan_timbangan; }
    public double getUpahPanen() { return upah_panen; }
    public double getUpahTransportasi() { return upah_transportasi; }
    public double getBiayaLainnya() { return biaya_lainnya; }
    public double getTotalHasilBersih() {
        return totalHasilBersih;
    }
    public double getTotalPendapatan() {
        return totalPendapatan;
    }
    public double getTotalPengeluaran() {
        return totalPengeluaran;
    }
    public int getBeratBersih() { return berat_bersih; }
}
