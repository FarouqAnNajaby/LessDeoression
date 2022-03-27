package com.example.less_depression.Pengguna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.less_depression.API.APIRequestData;
import com.example.less_depression.API.RetroServer;
import com.example.less_depression.Adapter.AdapterCekGejala;
import com.example.less_depression.Model.DataModel;
import com.example.less_depression.Model.Pengguna;
import com.example.less_depression.Model.ResponseModel;
import com.example.less_depression.Model.ResponseModelDataCekGejala;
import com.example.less_depression.Model.ResponseModelDataProfil;
import com.example.less_depression.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cekDepresi extends AppCompatActivity {

    ArrayList<String> arrayListKodeGejala = new ArrayList<String>();

    private RecyclerView rvData_pilihan;
    private RecyclerView.Adapter adData_pilihan;
    private RecyclerView.LayoutManager lmData_pilihan;
    private List<DataModel> listData_pilihan = new ArrayList<>();
    private ProgressBar pbData_pilihan;

    RelativeLayout btn_batal, btn_simpan;

    int id_pengguna = 0;
    int perhitungan = 0;
    String kode_gejala = "";

    protected Cursor cursor;
    protected Cursor cursorid;
    Pengguna dbHelper;

    String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_depresi);

        btn_batal = findViewById(R.id.button_batal);
        btn_simpan = findViewById(R.id.button_selesai);
        rvData_pilihan = findViewById(R.id.rv_data_pilihan);
        pbData_pilihan = findViewById(R.id.pb_data_pilihan);

        lmData_pilihan = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData_pilihan.setLayoutManager(lmData_pilihan);

        pbData_pilihan.setVisibility(View.VISIBLE);


        dbHelper = new Pengguna(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        cursor = db.rawQuery("SELECT * FROM biodata",null);
        cursorid = db.rawQuery("SELECT email FROM biodata",null);

        //ambil email

        if (cursor.getCount()>0){
            cursorid.moveToPosition(0);
            email = cursorid.getString(0).toString();
        }

        // ambil id pengguna

        arrayListKodeGejala.clear();

        APIRequestData ardData = RetroServer.koneksiRetrofil().create(APIRequestData.class);
        Call<ResponseModel> tampilData = ardData.ardRetrieveData();

        tampilData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

//                Toast.makeText(daftar_gejala.this, "Kode : "+kode+"| Pesan : "+pesan, Toast.LENGTH_SHORT).show();

                listData_pilihan = response.body().getData();

                adData_pilihan = new AdapterCekGejala(cekDepresi.this, listData_pilihan);
                rvData_pilihan.setAdapter(adData_pilihan);
                adData_pilihan.notifyDataSetChanged();

                pbData_pilihan.setVisibility(View.INVISIBLE);

                //memilih gejala

                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                        perhitungan = Integer.valueOf(viewHolder.getAdapterPosition())+1;
                        kode_gejala = "G00"+String.valueOf(perhitungan);

                        if(perhitungan >= 10){
                            kode_gejala = "G0"+String.valueOf(perhitungan);
                        }

                        arrayListKodeGejala.add(kode_gejala);

                        btn_simpan.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Log.d("array", "array : "+arrayListKodeGejala);

                                    APIRequestData ardDataProfile = RetroServer.koneksiRetrofil().create(APIRequestData.class);
                                    Call<ResponseModelDataProfil> tampilDataProfile = ardDataProfile.downloadDataProfile(email);

                                    tampilDataProfile.enqueue(new Callback<ResponseModelDataProfil>() {
                                        @Override
                                        public void onResponse(Call<ResponseModelDataProfil> call, Response<ResponseModelDataProfil> response) {

                                            ResponseModelDataProfil data =response.body();

                                            id_pengguna = data.getId_pengguna();

                                            APIRequestData cekDepresi = RetroServer.koneksiRetrofil().create(APIRequestData.class);
                                            Call<ResponseModelDataCekGejala> CekDepresiHistory = cekDepresi.ardCekDepresi(id_pengguna, arrayListKodeGejala);

                                            CekDepresiHistory.enqueue(new Callback<ResponseModelDataCekGejala>() {
                                            @Override
                                            public void onResponse(Call<ResponseModelDataCekGejala> call, Response<ResponseModelDataCekGejala> response) {

                                                ResponseModelDataCekGejala datacek = response.body();

                                                String pesan = datacek.getPesan();
                                                Toast.makeText(cekDepresi.this, pesan, Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(cekDepresi.this, Hasil_cekDepresi.class));
                                                finish();

                                            }

                                            @Override
                                            public void onFailure(Call<ResponseModelDataCekGejala> call, Throwable t) {

                                                Log.d("eror", "eror : "+t.getMessage());
                                                Toast.makeText(cekDepresi.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                            });
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseModelDataProfil> call, Throwable t) {
                                            Toast.makeText(cekDepresi.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });


                                }
                            });

                    }
                }).attachToRecyclerView(rvData_pilihan);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(cekDepresi.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("eror", "Eror : "+t.getMessage());

                pbData_pilihan.setVisibility(View.INVISIBLE);
            }
        });

    }

}