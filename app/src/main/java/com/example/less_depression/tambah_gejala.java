package com.example.less_depression;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.less_depression.API.APIRequestData;
import com.example.less_depression.API.RetroServer;
import com.example.less_depression.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tambah_gejala extends AppCompatActivity {

    private EditText etkodegejala, etnamagejala, etbobotgejala;
    private CheckBox CB_ringan, CB_sedang, CB_berat;
    Animation frombottom, fromtop, smltobig, bounce ;
    RelativeLayout bg_putih, btn_tambah;

    String kode_gejala, nama_gejala, bobot_gejala, kode_indikasi;
    double bobot;
    String Depresi_ringan = "false";
    String Depresi_sedang = "false";
    String Depresi_berat = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_gejala);

        bg_putih = (RelativeLayout) findViewById(R.id.bg_putih_tambah_gejala);
        etkodegejala = findViewById(R.id.et_kode_gejala);
        etnamagejala = findViewById(R.id.et_nama_gejala);
        etbobotgejala = findViewById(R.id.et_bobot_gejala);
        CB_ringan = findViewById(R.id.cb_ringan);
        CB_sedang = findViewById(R.id.cb_sedang);
        CB_berat = findViewById(R.id.cb_berat);
        btn_tambah = findViewById(R.id.button_add);

        CB_ringan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CB_ringan.isChecked()){
                    Depresi_ringan = "true";
                }
            }
        });

        CB_sedang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CB_sedang.isChecked()){
                    Depresi_sedang = "true";

                }
            }
        });

        CB_berat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CB_berat.isChecked()){
                    Depresi_berat = "true";
                }
            }
        });

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kode_gejala = etkodegejala.getText().toString();
                nama_gejala = etnamagejala.getText().toString();
                bobot_gejala = etbobotgejala.getText().toString();

                if (kode_gejala.trim().equals("")){
                    etkodegejala.setError("Kode Harus Diisi");
                }else if (nama_gejala.trim().equals("")){
                    etnamagejala.setError("Nama Harus Diisi");
                }else if (bobot_gejala.trim().equals("")){
                    etbobotgejala.setError("Bobot Harus Diisi");
                }else {
                    createData();
                }

            }
        });

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);

        bg_putih.setAnimation(frombottom);

    }

    private void createData(){

        bobot = Double.parseDouble(bobot_gejala);
        APIRequestData ardData = RetroServer.koneksiRetrofil().create(APIRequestData.class);
        Call<ResponseModel> simpanData = ardData.ardCreateDataGejala(kode_gejala, nama_gejala, bobot);

        simpanData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                    APIRequestData ardtingkatangejala = RetroServer.koneksiRetrofil().create(APIRequestData.class);
                    Call<ResponseModel> simpanDataTingkatanGejala = ardtingkatangejala.ardCreateDataTingkatanGejala(kode_gejala, Depresi_ringan, Depresi_sedang, Depresi_berat);

                    simpanDataTingkatanGejala.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            int kode = response.body().getKode();
                            String pesan = response.body().getPesan();

                            Toast.makeText(tambah_gejala.this, pesan, Toast.LENGTH_SHORT).show();
                            Intent godaftargejala = new Intent(tambah_gejala.this, daftar_gejala.class);
                            startActivity(godaftargejala);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Toast.makeText(tambah_gejala.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(tambah_gejala.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}