package com.example.less_depression.Model;

import java.util.ArrayList;
import java.util.List;

public class ResponseModelDataCekGejala {

    private int kode;
    private String pesan;
    String kode_gejala;
    ArrayList<String> arrayListKodeGejala = new ArrayList<String>();

    private List<DataModelCekGejala> data;

    public ArrayList<String> getArrayListKodeGejala() {
        return arrayListKodeGejala;
    }

    public void setArrayListKodeGejala(ArrayList<String> arrayListKodeGejala) {
        this.arrayListKodeGejala = arrayListKodeGejala;
    }

    public List<DataModelCekGejala> getData() {
        return data;
    }

    public void setData(List<DataModelCekGejala> data) {
        this.data = data;
    }


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

    public String getKode_gejala() {
        return kode_gejala;
    }

    public void setKode_gejala(String kode_gejala) {
        this.kode_gejala = kode_gejala;
    }
}
