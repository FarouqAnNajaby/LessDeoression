package com.example.less_depression.Model;

import java.util.List;

public class ResponseModelDataProfil {

    private int id_pengguna;
    private String pesan;
    private String email_pengguna;
    private String nama_pengguna;
    private String jk_pengguna;
    private int umur_pengguna;

    private List<DataModelProfil> data;

    public int getId_pengguna() {
        return id_pengguna;
    }

    public void setId_pengguna(int id_pengguna) {
        this.id_pengguna = id_pengguna;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getEmail_pengguna() {
        return email_pengguna;
    }

    public void setEmail_pengguna(String email_pengguna) {
        this.email_pengguna = email_pengguna;
    }

    public String getNama_pengguna() {
        return nama_pengguna;
    }

    public void setNama_pengguna(String nama_pengguna) {
        this.nama_pengguna = nama_pengguna;
    }

    public String getJk_pengguna() {
        return jk_pengguna;
    }

    public void setJk_pengguna(String jk_pengguna) {
        this.jk_pengguna = jk_pengguna;
    }

    public int getUmur_pengguna() {
        return umur_pengguna;
    }

    public void setUmur_pengguna(int umur_pengguna) {
        this.umur_pengguna = umur_pengguna;
    }

    public List<DataModelProfil> getData() {
        return data;
    }

    public void setData(List<DataModelProfil> data) {
        this.data = data;
    }
}
