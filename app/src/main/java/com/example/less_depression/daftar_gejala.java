package com.example.less_depression;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.less_depression.API.APIRequestData;
import com.example.less_depression.API.RetroServer;
import com.example.less_depression.Adapter.AdapterDataGejala;
import com.example.less_depression.Model.DataModel;
import com.example.less_depression.Model.ResponseModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class daftar_gejala extends AppCompatActivity {

    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<DataModel> listData = new ArrayList<>();
    private SwipeRefreshLayout srlData;
    private ProgressBar pbData;

    FloatingActionButton add;
    Animation frombottom, fromtop, smltobig, bounce ;
    RelativeLayout bg_putih, btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_gejala);

        bg_putih = (RelativeLayout) findViewById(R.id.bg_putih_daftar_gejala);

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);

        bg_putih.setAnimation(frombottom);

        rvData = findViewById(R.id.rv_data);
        add = findViewById(R.id.btn_add);
        srlData = findViewById(R.id.srl_data);
        pbData = findViewById(R.id.pb_data);
        btn_back = findViewById(R.id.btn_back);

        lmData = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);

        srlData.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlData.setRefreshing(true);
                retrieveData();
                srlData.setRefreshing(false);
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotambahgejala = new Intent(daftar_gejala.this, tambah_gejala.class);
                startActivity(gotambahgejala);
                finish();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gobackadmin = new Intent(daftar_gejala.this, home_admin.class);
                startActivity(gobackadmin);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveData();
    }

    public void retrieveData(){
        pbData.setVisibility(View.VISIBLE);

        APIRequestData ardData = RetroServer.koneksiRetrofil().create(APIRequestData.class);
        Call<ResponseModel> tampilData = ardData.ardRetrieveData();

        tampilData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

//                Toast.makeText(daftar_gejala.this, "Kode : "+kode+"| Pesan : "+pesan, Toast.LENGTH_SHORT).show();

                listData = response.body().getData();

                adData = new AdapterDataGejala(daftar_gejala.this, listData);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();

                pbData.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(daftar_gejala.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("eror", "Eror : "+t.getMessage());

                pbData.setVisibility(View.INVISIBLE);
            }
        });
    }
}