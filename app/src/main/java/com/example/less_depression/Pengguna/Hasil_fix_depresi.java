package com.example.less_depression.Pengguna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.less_depression.API.APIRequestData;
import com.example.less_depression.API.RetroServer;
import com.example.less_depression.Model.Pengguna;
import com.example.less_depression.Model.ResponseModelDataCekGejala;
import com.example.less_depression.Model.ResponseModelDataHistory;
import com.example.less_depression.Model.ResponseModelDataHistoryHasil;
import com.example.less_depression.Model.ResponseModelDataProfil;
import com.example.less_depression.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Hasil_fix_depresi extends AppCompatActivity {

    TextView hasil, rekomendasi;
    RelativeLayout btn_oke;

    int id_pengguna = 0;
    protected Cursor cursor;
    protected Cursor cursorid;
    Pengguna dbHelper;

    String email = "";
    String nama = "";
    String kode_history = "";
    String indikasi = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_fix_depresi);

        hasil = findViewById(R.id.tv_hasil);
        rekomendasi = findViewById(R.id.TV_jawaban);
        btn_oke = findViewById(R.id.button_oke);

        dbHelper = new Pengguna(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        cursor = db.rawQuery("SELECT * FROM biodata",null);
        cursorid = db.rawQuery("SELECT email FROM biodata",null);

        //ambil email

        if (cursor.getCount()>0){
            cursorid.moveToPosition(0);
            email = cursorid.getString(0).toString();
        }

        btn_oke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Hasil_fix_depresi.this, HomePengguna.class));
                finish();
            }
        });


        APIRequestData ardDataProfile = RetroServer.koneksiRetrofil().create(APIRequestData.class);
        Call<ResponseModelDataProfil> tampilDataProfile = ardDataProfile.downloadDataProfile(email);

        tampilDataProfile.enqueue(new Callback<ResponseModelDataProfil>() {
            @Override
            public void onResponse(Call<ResponseModelDataProfil> call, Response<ResponseModelDataProfil> response) {

                ResponseModelDataProfil data =response.body();

                id_pengguna = data.getId_pengguna();
                nama = data.getNama_pengguna();

                APIRequestData ardDataHistory = RetroServer.koneksiRetrofil().create(APIRequestData.class);
                Call<ResponseModelDataHistory> tampilDataHistory = ardDataHistory.downloadDataHistory(id_pengguna);

                tampilDataHistory.enqueue(new Callback<ResponseModelDataHistory>() {
                    @Override
                    public void onResponse(Call<ResponseModelDataHistory> call, Response<ResponseModelDataHistory> response) {

                        ResponseModelDataHistory dataHistory =response.body();

                        kode_history = dataHistory.getKode_history();

                        APIRequestData ardDataHistoryHasil = RetroServer.koneksiRetrofil().create(APIRequestData.class);
                        Call<ResponseModelDataHistoryHasil> tampilDataHistoryHasil = ardDataHistoryHasil.downloadDataHistoryHasil(kode_history);

                        tampilDataHistoryHasil.enqueue(new Callback<ResponseModelDataHistoryHasil>() {
                            @Override
                            public void onResponse(Call<ResponseModelDataHistoryHasil> call, Response<ResponseModelDataHistoryHasil> response) {

                                ResponseModelDataHistoryHasil dataHistory =response.body();

                                indikasi = dataHistory.getIndikasi();

                                hasil.setText("Kami mendeteksi bahwa "+nama+" mengalami gejala depresi "+indikasi);

                                if (indikasi.equalsIgnoreCase("tidak_depresi")){
                                    rekomendasi.setText("Solusi yang dapat diberikan 2 solusi yaitu mengikuti konseling dan psikoterapi dengan psikolog");
                                }else if (indikasi.equalsIgnoreCase("ringan")){
                                    rekomendasi.setText("Solusi yang dapat diberikan 2 solusi yaitu mengikuti konseling dan psikoterapi dengan psikolog");
                                }else if (indikasi.equalsIgnoreCase("sedang")){
                                    rekomendasi.setText("Solusi yang dapat diberikan 2 solusi yaitu mengikuti konseling dan psikoterapi dengan psikolog");
                                }else if (indikasi.equalsIgnoreCase("berat")){

                                }


                            }

                            @Override
                            public void onFailure(Call<ResponseModelDataHistoryHasil> call, Throwable t) {
                                Toast.makeText(Hasil_fix_depresi.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<ResponseModelDataHistory> call, Throwable t) {
                        Toast.makeText(Hasil_fix_depresi.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseModelDataProfil> call, Throwable t) {
                Toast.makeText(Hasil_fix_depresi.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}