package com.example.less_depression.Model;

public class DataModelProfil {
    private int id, umur;

    private String email, nama, sandi, kelamin, foto, lokasi_foto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUmur() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getSandi() {
        return sandi;
    }

    public void setSandi(String sandi) {
        this.sandi = sandi;
    }

    public String getKelamin() {
        return kelamin;
    }

    public void setKelamin(String kelamin) {
        this.kelamin = kelamin;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getLokasi_foto() {
        return lokasi_foto;
    }

    public void setLokasi_foto(String lokasi_foto) {
        this.lokasi_foto = lokasi_foto;
    }
}
