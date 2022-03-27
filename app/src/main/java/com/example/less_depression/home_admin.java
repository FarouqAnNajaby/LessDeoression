package com.example.less_depression;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.less_depression.Model.Pengguna;
import com.example.less_depression.Pengguna.HomePengguna;

public class home_admin extends AppCompatActivity {

    RelativeLayout btn_menu_satu, btn_menu_dua, btn_keluar;
    protected Cursor cursor;
    Pengguna dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        dbHelper = new Pengguna(this);

        btn_menu_satu = (RelativeLayout) findViewById(R.id.btn_kelola_gejala);
        btn_menu_dua = (RelativeLayout) findViewById(R.id.btn_tambah_artikel);
        btn_keluar = (RelativeLayout) findViewById(R.id.btn_keluar_admin);

        btn_menu_satu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gomenusatu = new Intent(home_admin.this, daftar_gejala.class);
                startActivity(gomenusatu);
                finish();
            }
        });

        btn_menu_dua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goartikel = new Intent(home_admin.this, daftar_artikel_admin.class);
                startActivity(goartikel);
                finish();
            }
        });

        btn_keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keluar();
            }
        });


    }

    private void keluar() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from biodata");
        Intent goLogin = new Intent(home_admin.this, SplashScreen.class);
        startActivity(goLogin);
        finish();

    }


}