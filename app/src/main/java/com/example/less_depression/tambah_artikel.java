package com.example.less_depression;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
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

public class tambah_artikel extends AppCompatActivity {

    private EditText et_kode_artikel, et_judul_artikel, et_isi_artikel;
    RelativeLayout bg_putih, btn_chose, btn_add;
    private RadioButton RB_ringan, RB_sedang, RB_berat;
    private RadioGroup RG_depresi;
    private int IMG_REQUEST = 21;

    private ImageView imageView;

    private Bitmap bitmap;

    String kode_artikel = "";
    String judul_artikel = "";
    String isi_artikel = "";
    String gambar_artikel = "";
    String tingkatan_depresi = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_artikel);

        imageView = findViewById(R.id.IV_image);
        et_kode_artikel = findViewById(R.id.et_kode_artikel);
        et_judul_artikel = findViewById(R.id.et_judul_artikel);
        et_isi_artikel = findViewById(R.id.et_isi_artikel);
        btn_add = findViewById(R.id.button_add_artikel);
        btn_chose = findViewById(R.id.button_chose);
        et_judul_artikel = findViewById(R.id.et_judul_artikel);
        et_isi_artikel = findViewById(R.id.et_isi_artikel);
        RG_depresi = findViewById(R.id.RG_depresi);
//        RB_ringan = findViewById(R.id.RB_Ringan);
//        RB_sedang = findViewById(R.id.RB_Sedang);
//        RB_berat = findViewById(R.id.RB_Berat);

        RG_depresi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.RB_Ringan:
                        tingkatan_depresi = "Depresi Ringan";
                        break;
                    case R.id.RB_Sedang:
                        tingkatan_depresi = "Depresi Sedang";
                        break;
                    case R.id.RB_Berat:
                        tingkatan_depresi = "Depresi Berat";
                        break;

                }
            }
        });

        btn_chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMG_REQUEST);

            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
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


    private void uploadImage() {

        kode_artikel = et_kode_artikel.getText().toString();
        judul_artikel = et_judul_artikel.getText().toString();
        isi_artikel = et_isi_artikel.getText().toString();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte [] imageInByte = byteArrayOutputStream.toByteArray();

        String encodeImage = Base64.encodeToString(imageInByte, Base64.DEFAULT);

        gambar_artikel = encodeImage;

//        Toast.makeText(this, encodeImage, Toast.LENGTH_SHORT).show();

        APIRequestData ardData = RetroServer.koneksiRetrofil().create(APIRequestData.class);
        Call<ResponseModel> simpanArtikelData = ardData.ardAddArtikelData(kode_artikel, judul_artikel, isi_artikel, tingkatan_depresi, gambar_artikel);

        simpanArtikelData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(tambah_artikel.this, pesan   , Toast.LENGTH_SHORT).show();
                Intent godaftargejala = new Intent(tambah_artikel.this, daftar_artikel_admin.class);
                startActivity(godaftargejala);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(tambah_artikel.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("eror", "Upload : "+t.getMessage());
            }
        });
    }
}