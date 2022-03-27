package com.example.less_depression.Pengguna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.less_depression.API.APIRequestData;
import com.example.less_depression.API.RetroServer;
import com.example.less_depression.Adapter.AdapterArtikel;
import com.example.less_depression.Adapter.AdapterArtikelPengguna;
import com.example.less_depression.Model.DataModel;
import com.example.less_depression.Model.ResponseModel;
import com.example.less_depression.R;
import com.example.less_depression.daftar_artikel_admin;
import com.example.less_depression.tambah_artikel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class artikel extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RecyclerView rvDataArtikel;
    private RecyclerView.Adapter adDataArtikel;
    private RecyclerView.LayoutManager lmDataArtikel;
    private List<DataModel> listDataArtikel = new ArrayList<>();
    private SwipeRefreshLayout srlDataArtikel;
    private ProgressBar pbDataArtikel;

    Spinner depresi;
    String isi_depresi = "Depresi Ringan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikel);

        rvDataArtikel = findViewById(R.id.rv_data_artikel_pengguna);
        srlDataArtikel = findViewById(R.id.srl_data_artikel_pengguna);
        pbDataArtikel = findViewById(R.id.pb_data_artikel_pengguna);
        depresi = findViewById(R.id.Spinner_depresi);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.depresi, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        depresi.setAdapter(adapter);
        depresi.setOnItemSelectedListener(this);

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


//        Toast.makeText(artikel.this, isi_depresi , Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveData();
    }

    private void retrieveData() {
        pbDataArtikel.setVisibility(View.VISIBLE);

        APIRequestData ardData = RetroServer.koneksiRetrofil().create(APIRequestData.class);
        Call<ResponseModel> tampilData = ardData.ardRetrieveArtikelPenggunaData(isi_depresi);

        tampilData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

//                Toast.makeText(daftar_gejala.this, "Kode : "+kode+"| Pesan : "+pesan, Toast.LENGTH_SHORT).show();

                listDataArtikel = response.body().getData();

                adDataArtikel = new AdapterArtikelPengguna(artikel.this, listDataArtikel);
                rvDataArtikel.setAdapter(adDataArtikel);
                adDataArtikel.notifyDataSetChanged();

                pbDataArtikel.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(artikel.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("eror", "Eror : "+t.getMessage());

                pbDataArtikel.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        isi_depresi = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}