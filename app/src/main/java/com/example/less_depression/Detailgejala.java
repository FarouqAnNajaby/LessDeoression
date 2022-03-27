package com.example.less_depression;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.less_depression.API.APIRequestData;
import com.example.less_depression.API.RetroServer;
import com.example.less_depression.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detailgejala extends AppCompatActivity {

    private EditText Edit_kode_gejala;
    private EditText Edit_nama_gejala;
    private EditText Edit_bobot_gejala;
    private CheckBox CK_ringan;
    private CheckBox CK_sedang;
    private CheckBox CK_berat;

    double bobot;

    String kode_gejala = "";
    String nama_gejala = "";
    String bobot_gejala = "";
    String gejala_ringan = "";
    String gejala_sedang = "";
    String gejala_berat = "";

    String ubah_kode_gejala = "";
    String ubah_nama_gejala = "";
    String ubah_bobot_gejala = "";

    private RelativeLayout btn_hapus, btn_rubah;

    String Depresi_ringan = "false";
    String Depresi_sedang = "false";
    String Depresi_berat = "false";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailgejala);

        Edit_kode_gejala = findViewById(R.id.et_ubah_kode_gejala);
        Edit_nama_gejala = findViewById(R.id.et_ubah_nama_gejala);
        Edit_bobot_gejala = findViewById(R.id.et_ubah_bobot_gejala);
        CK_ringan = findViewById(R.id.cb_ubah_ringan);
        CK_sedang = findViewById(R.id.cb_ubah_sedang);
        CK_berat = findViewById(R.id.cb_ubah_berat);
        btn_hapus = findViewById(R.id.btn_hapus);
        btn_rubah = findViewById(R.id.btn_ubah);

        Intent i =getIntent();
        kode_gejala = i.getStringExtra("kode_gejala");
        nama_gejala = i.getStringExtra("nama_gejala");
        bobot_gejala = i.getStringExtra("bobot_gejala");
        gejala_ringan = i.getStringExtra("gejala_ringan");
        gejala_sedang = i.getStringExtra("gejala_sedang");
        gejala_berat = i.getStringExtra("gejala_berat");

        Edit_kode_gejala.setText(kode_gejala);
        Edit_nama_gejala.setText(nama_gejala);
        Edit_bobot_gejala.setText(bobot_gejala);

        if (gejala_ringan.equalsIgnoreCase("true")){
            CK_ringan.setChecked(true);
        }

        if (gejala_sedang.equalsIgnoreCase("true")){
            CK_sedang.setChecked(true);
        }

        if (gejala_berat.equalsIgnoreCase("true")){
            CK_berat.setChecked(true);
        }

        btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hapusData();
            }
        });

        CK_ringan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CK_ringan.isChecked()){
                    Depresi_ringan = "true";
                }
            }
        });

        CK_sedang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CK_sedang.isChecked()){
                    Depresi_sedang = "true";
                }
            }
        });

        CK_berat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CK_berat.isChecked()){
                    Depresi_berat = "true";
                }
            }
        });


        btn_rubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ubahData();
            }
        });
    }

    private void hapusData(){
        APIRequestData ardData = RetroServer.koneksiRetrofil().create(APIRequestData.class);
        Call<ResponseModel> hapusData = ardData.ardDeleteTingkatanGejala(kode_gejala);

        hapusData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                Call<ResponseModel> hapusTingkatan = ardData.ardDeleteDataGejala(kode_gejala);

                hapusTingkatan.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                        Toast.makeText(Detailgejala.this, "Berhasil dihapus", Toast.LENGTH_SHORT).show();
                        Intent gohapusgejala = new Intent(Detailgejala.this, daftar_gejala.class);
                        startActivity(gohapusgejala);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Toast.makeText(Detailgejala.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(Detailgejala.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ubahData(){
        ubah_kode_gejala = Edit_kode_gejala.getText().toString();
        ubah_nama_gejala = Edit_nama_gejala.getText().toString();
        ubah_bobot_gejala = Edit_bobot_gejala.getText().toString();
        bobot = Double.parseDouble(ubah_bobot_gejala);


        APIRequestData ardData = RetroServer.koneksiRetrofil().create(APIRequestData.class);
        Call<ResponseModel> updateData = ardData.ardUpdateDataGejala(ubah_kode_gejala, ubah_nama_gejala, bobot);

        updateData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                Call<ResponseModel> updateDataTingkatan = ardData.ardUpdateTingkatanGejala(ubah_kode_gejala, Depresi_ringan, Depresi_sedang,Depresi_berat);

                updateDataTingkatan.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        int kode = response.body().getKode();
                        String pesan = response.body().getPesan();

                        Toast.makeText(Detailgejala.this, pesan, Toast.LENGTH_SHORT).show();
                        Intent godaftargejala = new Intent(Detailgejala.this, daftar_gejala.class);
                        startActivity(godaftargejala);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Toast.makeText(Detailgejala.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(Detailgejala.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}