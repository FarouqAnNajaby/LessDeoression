package com.example.less_depression;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.example.less_depression.Model.Pengguna;
import com.example.less_depression.Pengguna.HomePengguna;

public class SplashScreen extends AppCompatActivity {

    Animation frombottom, fromtop, smltobig, bounce ;
    RelativeLayout btn_next;
    Handler handler;

    protected Cursor cursor;
    protected Cursor cursorid;
    Pengguna dbHelper;

    String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        btn_next = (RelativeLayout) findViewById(R.id.btn_next);
        smltobig = AnimationUtils.loadAnimation(this, R.anim.smltobig);

        btn_next.setAnimation(smltobig);

        dbHelper = new Pengguna(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        cursor = db.rawQuery("SELECT * FROM biodata",null);
        cursorid = db.rawQuery("SELECT email FROM biodata",null);


        if (cursor.getCount()>0){
            cursorid.moveToPosition(0);
            email = cursorid.getString(0).toString();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (email.equalsIgnoreCase("adminDepresi")){
                        startActivity(new Intent(SplashScreen.this, home_admin.class));
                        finish();
                    }else{
                        startActivity(new Intent(SplashScreen.this, HomePengguna.class));
                        finish();
                    }

                }
            },3000);

        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();

                }
            },3000);

        }


    }
}