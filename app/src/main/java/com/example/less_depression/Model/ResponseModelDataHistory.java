package com.example.less_depression.Model;

import java.util.List;

public class ResponseModelDataHistory {

    private int kode;
    private int kode_percobaan;
    private String pesan;
    private String kode_history;

    private List<DataModelHistory> data;

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    public int getKode_percobaan() {
        return kode_percobaan;
    }

    public void setKode_percobaan(int kode_percobaan) {
        this.kode_percobaan = kode_percobaan;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getKode_history() {
        return kode_history;
    }

    public void setKode_history(String kode_history) {
        this.kode_history = kode_history;
    }

    public List<DataModelHistory> getData() {
        return data;
    }

    public void setData(List<DataModelHistory> data) {
        this.data = data;
    }
}
