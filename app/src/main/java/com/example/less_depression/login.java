package com.example.less_depression;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.less_depression.API.APIRequestData;
import com.example.less_depression.API.RetroServer;
import com.example.less_depression.Model.DataModel;
import com.example.less_depression.Model.Pengguna;
import com.example.less_depression.Model.ResponseModel;
import com.example.less_depression.Pengguna.HomePengguna;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {

    Animation frombottom, fromtop, smltobig, bounce ;
    RelativeLayout bg_putih, btn_login;
    private EditText txtemail, txtpass;
    SessionManager sessionManager;
    String username, password;
    Pengguna dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new Pengguna(this);

        bg_putih = (RelativeLayout) findViewById(R.id.bg_putih);
        btn_login = (RelativeLayout) findViewById(R.id.btn_login);
        txtemail = findViewById(R.id.et_email);
        txtpass = findViewById(R.id.et_pass);

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);

        bg_putih.setAnimation(frombottom);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
    }

    private void Login() {

        username = txtemail.getText().toString();
        password = txtpass.getText().toString();

        APIRequestData ardData = RetroServer.koneksiRetrofil().create(APIRequestData.class);
        Call<ResponseModel> loginData = ardData.ardLoginData(username, password);

        if (username.trim().isEmpty()){
            Toast.makeText(login.this,"Silakan isi Email anda",Toast.LENGTH_SHORT).show();
        }else if (password.trim().isEmpty()){
            Toast.makeText(login.this,"Silakan isi Password anda",Toast.LENGTH_SHORT).show();
        }else{

            loginData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.execSQL("insert into biodata values( '"+1+"','"+username+"')");

                    if (kode == 1){
                        Toast.makeText(login.this, pesan, Toast.LENGTH_SHORT).show();
                        Intent gologin = new Intent(login.this, home_admin.class);
                        startActivity(gologin);
                        finish();
                    }else if(kode == 2){
                        Toast.makeText(login.this, pesan, Toast.LENGTH_SHORT).show();
                        Intent gologin = new Intent(login.this, HomePengguna.class);
                        startActivity(gologin);
                        finish();
                    }else if (kode == 0){
                        Toast.makeText(login.this, pesan, Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(login.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}