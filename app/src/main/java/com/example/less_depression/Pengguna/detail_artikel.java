package com.example.less_depression.Pengguna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.less_depression.API.APIRequestData;
import com.example.less_depression.API.RetroServer;
import com.example.less_depression.Model.ResponseModel;
import com.example.less_depression.R;
import com.example.less_depression.ubah_artikel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class detail_artikel extends AppCompatActivity {

    private ImageView imageView;
    private int IMG_REQUEST = 21;

    private Bitmap bitmap;

    TextView TV_judul_artikel, TV_isi_artikel;

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
        setContentView(R.layout.activity_detail_artikel);

        imageView = findViewById(R.id.IV_gambar_artikel);
        TV_isi_artikel = findViewById(R.id.isi_artikel_pengguna);
        TV_judul_artikel = findViewById(R.id.judul_atikel_pengguna);

        Intent i =getIntent();
        kode_artikel = i.getStringExtra("kode_artikel");
        judul_artikel = i.getStringExtra("judul_artikel");
        isi_artikel = i.getStringExtra("isi_artikel");
        tingkatan_depresi = i.getStringExtra("tingkatan_depresi");
        gambar_artikel = i.getStringExtra("gambar_artikel");
        lokasi_gambar = i.getStringExtra("lokasi_gambar");

        TV_judul_artikel.setText(judul_artikel.toString());
        TV_isi_artikel.setText(isi_artikel.toString());


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
                    Toast.makeText(detail_artikel.this, "Invalid SN", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(ubah_artikel.this, "Berhasil ditambahkan", Toast.LENGTH_SHORT).show();
//                Intent godaftargejala = new Intent(ubah_artikel.this, daftar_gejala.class);
//                startActivity(godaftargejala);
//                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(detail_artikel.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}