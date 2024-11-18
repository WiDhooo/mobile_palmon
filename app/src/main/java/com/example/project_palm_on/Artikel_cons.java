package com.example.project_palm_on;

public class Artikel_cons {
    private String judul;
    private String isi;
    private String gambar;
    private String nama_pembuat;
    private String created_at;

    public Artikel_cons(String judul, String isi, String gambar, String nama_pembuat, String created_at) {
        this.judul = judul;
        this.isi = isi;
        this.gambar = gambar;
        this.nama_pembuat = nama_pembuat;
        this.created_at = created_at;
    }

    public String getJudul() {
        return judul;
    }

    public String getIsi() {
        return isi;
    }

    public String getGambar() {
        return gambar;
    }

    public String getNama_pembuat() {
        return nama_pembuat;
    }

    public String getCreated_at() {
        return created_at;
    }

}
