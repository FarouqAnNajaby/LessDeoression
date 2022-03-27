package com.example.less_depression.Model;

import java.util.List;

public class ResponseModelDataHistoryPilihan {

    private int kode;
    private String pesan;
    private String kode_history;
    private String kode_gejala;

    private List<DataModelHistoryPilihan> data;

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
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

    public String getKode_gejala() {
        return kode_gejala;
    }

    public void setKode_gejala(String kode_gejala) {
        this.kode_gejala = kode_gejala;
    }

    public List<DataModelHistoryPilihan> getData() {
        return data;
    }

    public void setData(List<DataModelHistoryPilihan> data) {
        this.data = data;
    }
}
