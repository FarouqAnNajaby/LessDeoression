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
import com.example.less_depression.Adapter.AdapterArtikel;
import com.example.less_depression.Model.DataModel;
import com.example.less_depression.Model.ResponseModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class daftar_artikel_admin extends AppCompatActivity {

    private RecyclerView rvDataArtikel;
    private RecyclerView.Adapter adDataArtikel;
    private RecyclerView.LayoutManager lmDataArtikel;
    private List<DataModel> listDataArtikel = new ArrayList<>();
    private SwipeRefreshLayout srlDataArtikel;
    private ProgressBar pbDataArtikel;

    FloatingActionButton add;
    Animation frombottom, fromtop, smltobig, bounce ;
    RelativeLayout bg_putih, btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_artikel_admin);

        bg_putih = (RelativeLayout) findViewById(R.id.bg_putih_daftar_gejala);
        add = findViewById(R.id.btn_add_artikel);

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);

        bg_putih.setAnimation(frombottom);

        rvDataArtikel = findViewById(R.id.rv_data_artikel);
        srlDataArtikel = findViewById(R.id.srl_data_artikel);
        pbDataArtikel = findViewById(R.id.pb_data_artikel);
        btn_back = findViewById(R.id.btn_back_artikel);

        lmDataArtikel = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvDataArtikel.setLayoutManager(lmDataArtikel);

        srlDataArtikel.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlDataArtikel.setRefreshing(true);
                retrieveData();
                srlDataArtikel.setRefreshing(false);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotambahartikel = new Intent(daftar_artikel_admin.this, tambah_artikel.class);
                startActivity(gotambahartikel);
                finish();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gobackadmin = new Intent(daftar_artikel_admin.this, home_admin.class);
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
        pbDataArtikel.setVisibility(View.VISIBLE);

        APIRequestData ardData = RetroServer.koneksiRetrofil().create(APIRequestData.class);
        Call<ResponseModel> tampilData = ardData.ardRetrieveArtikelData();

        tampilData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

//                Toast.makeText(daftar_gejala.this, "Kode : "+kode+"| Pesan : "+pesan, Toast.LENGTH_SHORT).show();

                listDataArtikel = response.body().getData();

                adDataArtikel = new AdapterArtikel(daftar_artikel_admin.this, listDataArtikel);
                rvDataArtikel.setAdapter(adDataArtikel);
                adDataArtikel.notifyDataSetChanged();

                pbDataArtikel.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(daftar_artikel_admin.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("eror", "Eror : "+t.getMessage());

                pbDataArtikel.setVisibility(View.INVISIBLE);
            }
        });
    }
}