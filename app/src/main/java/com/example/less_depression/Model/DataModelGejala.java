package com.example.less_depression.Model;

public class DataModelGejala {

    private String nama_gejala, depresi_ringan, depresi_sedang, depresi_berat;
    private double bobot_gejala;

    public String getNama_gejala() {
        return nama_gejala;
    }

    public void setNama_gejala(String nama_gejala) {
        this.nama_gejala = nama_gejala;
    }

    public String getDepresi_ringan() {
        return depresi_ringan;
    }

    public void setDepresi_ringan(String depresi_ringan) {
        this.depresi_ringan = depresi_ringan;
    }

    public String getDepresi_sedang() {
        return depresi_sedang;
    }

    public void setDepresi_sedang(String depresi_sedang) {
        this.depresi_sedang = depresi_sedang;
    }

    public String getDepresi_berat() {
        return depresi_berat;
    }

    public void setDepresi_berat(String depresi_berat) {
        this.depresi_berat = depresi_berat;
    }

    public double getBobot_gejala() {
        return bobot_gejala;
    }

    public void setBobot_gejala(double bobot_gejala) {
        this.bobot_gejala = bobot_gejala;
    }
}
