package com.example.less_depression.Pengguna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.less_depression.MainActivity;
import com.example.less_depression.R;
import com.example.less_depression.SplashScreen;

public class Hasil_cekDepresi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_cek_depresi);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Hasil_cekDepresi.this, Hasil_fix_depresi.class));
                finish();

            }
        },5000);

    }
}