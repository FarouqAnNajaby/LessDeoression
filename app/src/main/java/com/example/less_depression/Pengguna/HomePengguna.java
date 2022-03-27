package com.example.less_depression.Pengguna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.less_depression.Model.Pengguna;
import com.example.less_depression.R;
import com.example.less_depression.SessionManager;
import com.example.less_depression.SplashScreen;

public class HomePengguna extends AppCompatActivity {

    protected Cursor cursor;
    Pengguna dbHelper;

    SessionManager sessionManager;
    TextView txt1;
    RelativeLayout btnCek , btnRiwayat , btnArtikel, btnProfile;
    ImageView IV_Profil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pengguna);

        btnCek = findViewById(R.id.btn_cek_depresi);
        btnRiwayat = findViewById(R.id.btn_cek_depresi);
        btnArtikel = findViewById(R.id.btn_lihat_artikel);
        btnProfile = findViewById(R.id.btn_Profile);
        IV_Profil = findViewById(R.id.profil);

        dbHelper = new Pengguna(this);

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePengguna.this, profil.class);
                startActivity(i);
            }
        });

        btnArtikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePengguna.this, artikel.class);
                startActivity(i);
            }
        });


        btnCek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePengguna.this, cekDepresi.class);
                startActivity(i);
            }
        });

        txt1 = findViewById(R.id.text1);

    }

}