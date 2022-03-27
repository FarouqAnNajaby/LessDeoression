package com.example.less_depression.Model;

import java.util.ArrayList;

public class DataModelCekGejala {

    String kode_gejala;
    ArrayList<String> arrayListKodeGejala = new ArrayList<String>();

    public ArrayList<String> getArrayListKodeGejala() {
        return arrayListKodeGejala;
    }

    public void setArrayListKodeGejala(ArrayList<String> arrayListKodeGejala) {
        this.arrayListKodeGejala = arrayListKodeGejala;
    }

    public String getKode_gejala() {
        return kode_gejala;
    }

    public void setKode_gejala(String kode_gejala) {
        this.kode_gejala = kode_gejala;
    }
}
