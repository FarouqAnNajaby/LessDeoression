package com.example.less_depression.Pengguna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.less_depression.API.APIRequestData;
import com.example.less_depression.API.RetroServer;
import com.example.less_depression.Model.Pengguna;
import com.example.less_depression.Model.ResponseModelDataProfil;
import com.example.less_depression.Model.ResponseModelProfile;
import com.example.less_depression.R;
import com.example.less_depression.SplashScreen;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class profil extends AppCompatActivity {

    private ImageView imageView;
    private int IMG_REQUEST = 21;

    private Bitmap bitmap;

    TextView txt_nama, txt_jenis_kelamin, txt_umur, txt_email;

    RelativeLayout btnKeluar;

    String encodedImage = "";

    protected Cursor cursor;
    protected Cursor cursorid;
    Pengguna dbHelper;

    String email = "";
    String nama_pengguna = "";
    String email_pengguna = "";
    String kelamin_pengguna = "";
    int umur_pengguna = 0;

    String completeJSONdata = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        txt_nama = findViewById(R.id.TV_nama_pengguna);
        txt_jenis_kelamin = findViewById(R.id.TV_jenis_kelamin_pengguna);
        txt_umur = findViewById(R.id.TV_umur_pengguna);
        txt_email = findViewById(R.id.TV_email_pengguna);
        imageView = findViewById(R.id.gambar_profile);
        btnKeluar = findViewById(R.id.btn_keluar);

        dbHelper = new Pengguna(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        cursor = db.rawQuery("SELECT * FROM biodata",null);
        cursorid = db.rawQuery("SELECT email FROM biodata",null);

        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keluar();
            }
        });

        if (cursor.getCount()>0){
            cursorid.moveToPosition(0);
            email = cursorid.getString(0).toString();

                    APIRequestData ardData = RetroServer.koneksiRetrofil().create(APIRequestData.class);
                    Call<ResponseModelProfile> tampilgambarData = ardData.downloadImageProfile(email);

                    tampilgambarData.enqueue(new Callback<ResponseModelProfile>() {
                        @Override
                        public void onResponse(Call<ResponseModelProfile> call, Response<ResponseModelProfile> response) {

                            ResponseModelProfile image =response.body();

                            if(image != null){
                                encodedImage = image.getEncodedImage();

                                byte[] imageInByte = Base64.decode(encodedImage, Base64.DEFAULT);
                                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);

                                imageView.setImageBitmap(decodedImage);
                            }else{
                                Toast.makeText(profil.this, "Invalid SN", Toast.LENGTH_SHORT).show();
                            }

                        }


                        @Override
                        public void onFailure(Call<ResponseModelProfile> call, Throwable t) {
                            Toast.makeText(profil.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                    APIRequestData ardDataProfile = RetroServer.koneksiRetrofil().create(APIRequestData.class);
                    Call<ResponseModelDataProfil> tampilDataProfile = ardDataProfile.downloadDataProfile(email);

                    tampilDataProfile.enqueue(new Callback<ResponseModelDataProfil>() {
                        @Override
                        public void onResponse(Call<ResponseModelDataProfil> call, Response<ResponseModelDataProfil> response) {

                            ResponseModelDataProfil data =response.body();

                            if(data != null){

                                nama_pengguna = data.getNama_pengguna();
                                kelamin_pengguna = data.getJk_pengguna();
                                umur_pengguna = data.getUmur_pengguna();
                                email_pengguna = data.getEmail_pengguna();

                                txt_nama.setText(": "+nama_pengguna.toString());
                                if (kelamin_pengguna.equalsIgnoreCase("L")){
                                    txt_jenis_kelamin.setText(": Laki-Laki");
                                }else if (kelamin_pengguna.equalsIgnoreCase("P")) {
                                    txt_jenis_kelamin.setText(": Perempuan");
                                }

                                txt_umur.setText(": "+String.valueOf(umur_pengguna));
                                txt_email.setText(": "+email_pengguna.toString());

                            }else{
                                Toast.makeText(profil.this, "Invalid SN", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseModelDataProfil> call, Throwable t) {
                            Toast.makeText(profil.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("eror", "eror : "+t.getMessage());
                        }
                    });

                }

        }



    private void keluar() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from biodata");
        Intent goLogin = new Intent(profil.this, SplashScreen.class);
        startActivity(goLogin);
        finish();

    }
}