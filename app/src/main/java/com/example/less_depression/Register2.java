package com.example.less_depression;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.less_depression.API.APIRequestData;
import com.example.less_depression.API.RetroServer;
import com.example.less_depression.Model.ResponseModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register2 extends AppCompatActivity {

    String nama="",email="",sandi="",kelamin="",foto="";
    String namaget,emailget,umurget,sandiget,kelaminget;
    Date date;
    Intent i;
    Button btn_pilih;
    Animation fromBottom;
    RelativeLayout bg_putih,btn_daftar,btn_back;
    private ImageView imageView;
    private Bitmap bitmap;
    private int IMG_REQUEST = 21,usia = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        imageView = findViewById(R.id.IV_image);
        bg_putih = (RelativeLayout) findViewById(R.id.bg_putih);
        bg_putih.setAnimation(fromBottom);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (toolbar != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.utama), PorterDuff.Mode.SRC_ATOP);

        btn_pilih = findViewById(R.id.btn_Pilih);
        btn_pilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMG_REQUEST);
            }
        });

        i = getIntent();
        namaget = i.getStringExtra("nama");
        emailget = i.getStringExtra("email");
        sandiget = i.getStringExtra("password");
        umurget = i.getStringExtra("umur");

        btn_daftar = findViewById(R.id.btn_daftar);
        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daftar();
            }
        });

    }

    private void daftar() {

        nama = namaget.trim();
        email = emailget.trim();
        sandi = sandiget.trim();
        kelamin = kelaminget.trim();
        usia = Integer.parseInt(umurget);

        Toast.makeText(this, Integer.toString(usia), Toast.LENGTH_SHORT).show();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte [] imageInByte = byteArrayOutputStream.toByteArray();

        String encodeImage = Base64.encodeToString(imageInByte, Base64.DEFAULT);
        foto = encodeImage;

        APIRequestData ardData = RetroServer.koneksiRetrofil().create(APIRequestData.class);
        Call<ResponseModel> simpanRegisterData = ardData.ardRegisterData(email,
                nama, sandi, kelamin, foto,usia);

        simpanRegisterData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(Register2.this, "Berhasil Regist", Toast.LENGTH_SHORT).show();
                Intent goDaftar = new Intent(Register2.this, login.class);
                startActivity(goDaftar);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(Register2.this,
                        "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("eror", "Register : "+t.getMessage());
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_laki:
                if (checked)
                    kelaminget = "L";
                break;
            case R.id.radio_perempuan:
                if (checked)
                    kelaminget = "P";
                break;
        }
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}