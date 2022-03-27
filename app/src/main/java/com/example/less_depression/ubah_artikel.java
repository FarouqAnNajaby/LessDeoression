package com.example.less_depression;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.less_depression.API.APIRequestData;
import com.example.less_depression.API.RetroServer;
import com.example.less_depression.Model.ResponseModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ubah_artikel extends AppCompatActivity {

    private EditText Edit_kode_artikel;
    private EditText Edit_judul_artikel;
    private EditText Edit_isi_artikel;
    private ImageView imageView;
    private RelativeLayout button_ubah, btn_hapus_artikel;
    private int IMG_REQUEST = 21;

    private RadioButton RB_ringan, RB_sedang, RB_berat;
    private RadioGroup RG_depresi;

    private Bitmap bitmap;

    String kode_artikel = "";
    String judul_artikel = "";
    String isi_artikel = "";
    String tingkatan_depresi = "";
    String gambar_artikel = "";
    String lokasi_gambar = "";
    String encodedImage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_artikel);

        Edit_kode_artikel = findViewById(R.id.et_kode_artikel_ubah);
        Edit_kode_artikel.setEnabled(false);
        Edit_judul_artikel = findViewById(R.id.et_judul_artikel_ubah);
        Edit_isi_artikel = findViewById(R.id.et_isi_artikel_ubah);
        imageView = findViewById(R.id.IV_image_ubah);
        button_ubah = findViewById(R.id.button_ubah_artikel);
        btn_hapus_artikel = findViewById(R.id.btn_hapus_artikel);
        RG_depresi = findViewById(R.id.RG_depresi);
        RB_ringan = findViewById(R.id.RB_Ringan_ubah);
        RB_sedang = findViewById(R.id.RB_Sedang_ubah);
        RB_berat = findViewById(R.id.RB_Berat_ubah);

        Intent i =getIntent();
        kode_artikel = i.getStringExtra("kode_artikel");
        judul_artikel = i.getStringExtra("judul_artikel");
        isi_artikel = i.getStringExtra("isi_artikel");
        tingkatan_depresi = i.getStringExtra("tingkatan_depresi");
        gambar_artikel = i.getStringExtra("gambar_artikel");
        lokasi_gambar = i.getStringExtra("lokasi_gambar");

        Edit_kode_artikel.setText(kode_artikel);
        Edit_judul_artikel.setText(judul_artikel);
        Edit_isi_artikel.setText(isi_artikel);

        if (tingkatan_depresi.equalsIgnoreCase("Depresi Ringan")){
            RB_ringan.setChecked(true);
        }else if (tingkatan_depresi.equalsIgnoreCase("Depresi Sedang")){
            RB_sedang.setChecked(true);
        }else if (tingkatan_depresi.equalsIgnoreCase("Depresi Berat")){
            RB_berat.setChecked(true);
        }

        btn_hapus_artikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hapusData();
            }
        });

        button_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ubah();
            }
        });

        APIRequestData ardData = RetroServer.koneksiRetrofil().create(APIRequestData.class);
        Call<ResponseModel> tampilgambarData = ardData.downloadImage(kode_artikel);

        tampilgambarData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                ResponseModel image =response.body();


                if(image != null){
                    encodedImage = image.getEncodedImage();

                    byte[] imageInByte = Base64.decode(encodedImage, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);

                    imageView.setImageBitmap(decodedImage);
                }else{
                    Toast.makeText(ubah_artikel.this, "Invalid SN", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(ubah_artikel.this, "Berhasil ditambahkan", Toast.LENGTH_SHORT).show();
//                Intent godaftargejala = new Intent(ubah_artikel.this, daftar_gejala.class);
//                startActivity(godaftargejala);
//                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ubah_artikel.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null){

            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void hapusData(){
        APIRequestData ardData = RetroServer.koneksiRetrofil().create(APIRequestData.class);
        Call<ResponseModel> hapusData = ardData.ardDeleteArtikelData(kode_artikel);

        hapusData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                Toast.makeText(ubah_artikel.this, "Berhasil dihapus", Toast.LENGTH_SHORT).show();
                Intent gohapusartikel = new Intent(ubah_artikel.this, daftar_artikel_admin.class);
                startActivity(gohapusartikel);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ubah_artikel.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ubah() {

        kode_artikel = Edit_kode_artikel.getText().toString();
        judul_artikel = Edit_judul_artikel.getText().toString();
        isi_artikel = Edit_isi_artikel.getText().toString();

        APIRequestData ardData = RetroServer.koneksiRetrofil().create(APIRequestData.class);
        Call<ResponseModel> simpanArtikelData = ardData.ardUpdateArtikelData(kode_artikel, judul_artikel, isi_artikel, tingkatan_depresi);

        simpanArtikelData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(ubah_artikel.this, "Berhasil dirubah", Toast.LENGTH_SHORT).show();
                Intent godaftargejala = new Intent(ubah_artikel.this, daftar_artikel_admin.class);
                startActivity(godaftargejala);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ubah_artikel.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("eror", "Upload : "+t.getMessage());
            }
        });
    }
}