package com.example.less_depression.Model;

import java.util.List;

public class ResponseModel {

    private int kode;
    private String pesan;

    private String imageSn;
    private String imageTitle;
    private String encodedImage;

    private List<DataModel> data;

    public String getImageSn() {
        return imageSn;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public String getEncodedImage() {
        return encodedImage;
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

    public List<DataModel> getData() {
        return data;
    }

    public void setData(List<DataModel> data) {
        this.data = data;
    }
}
