package com.example.less_depression.Model;

import java.util.List;

public class ResponseModelDataHistoryHasil {

    private String indikasi;
    private float nilai_akhir;

    private List<DataModelHistoryHasil> data;

    public String getIndikasi() {
        return indikasi;
    }

    public void setIndikasi(String indikasi) {
        this.indikasi = indikasi;
    }

    public float getNilai_akhir() {
        return nilai_akhir;
    }

    public void setNilai_akhir(float nilai_akhir) {
        this.nilai_akhir = nilai_akhir;
    }

    public List<DataModelHistoryHasil> getData() {
        return data;
    }

    public void setData(List<DataModelHistoryHasil> data) {
        this.data = data;
    }
}
