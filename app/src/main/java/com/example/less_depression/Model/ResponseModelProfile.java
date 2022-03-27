package com.example.less_depression.Model;

import java.util.List;

public class ResponseModelProfile {

    private int kode;
    private String pesan;
    private String imageSn;
    private String imageTitle;
    private String encodedImage;

    private List<DataModelProfil> data;

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

    public String getImageSn() {
        return imageSn;
    }

    public void setImageSn(String imageSn) {
        this.imageSn = imageSn;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    public List<DataModelProfil> getData() {
        return data;
    }

    public void setData(List<DataModelProfil> data) {
        this.data = data;
    }
}
