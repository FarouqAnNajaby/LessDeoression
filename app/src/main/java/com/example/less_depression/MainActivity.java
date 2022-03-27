package com.example.less_depression;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    Animation frombottom, fromtop, smltobig, bounce ;
    RelativeLayout bg_putih, btn_login,btn_daftar;

//    SessionManager sessionManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bg_putih = (RelativeLayout) findViewById(R.id.bg_putih);
        btn_login = (RelativeLayout) findViewById(R.id.btn_login_next);
        btn_daftar = (RelativeLayout) findViewById(R.id.btn_daftar_akun);

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);

        bg_putih.setAnimation(frombottom);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gologin = new Intent(MainActivity.this, login.class);
                startActivity(gologin);
                finish();
            }
        });

        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goregister = new Intent(MainActivity.this, Register.class);
                startActivity(goregister);
                finish();
            }
        });

//        sessionManager = new SessionManager(getApplicationContext());
//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                sessionManager.checkLogin();
//                finish();
//            }
//        },2000);


    }
}