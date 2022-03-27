package com.example.less_depression.API;

import android.util.Log;

import com.example.less_depression.Model.ResponseModel;
import com.example.less_depression.Model.ResponseModelDataCekGejala;
import com.example.less_depression.Model.ResponseModelDataHistory;
import com.example.less_depression.Model.ResponseModelDataHistoryHasil;
import com.example.less_depression.Model.ResponseModelDataHistoryPilihan;
import com.example.less_depression.Model.ResponseModelDataProfil;
import com.example.less_depression.Model.ResponseModelProfile;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {

    //history
    @FormUrlEncoded
    @POST("download_data_history.php")
    Call<ResponseModelDataHistory> downloadDataHistory(
            @Field("id_pengguna") int id_pengguna
    );

    @FormUrlEncoded
    @POST("download_data_history_hasil.php")
    Call<ResponseModelDataHistoryHasil> downloadDataHistoryHasil(
            @Field("kode_history") String kode_history
    );

    //profile

    @FormUrlEncoded
    @POST("download_image_profile.php")
    Call<ResponseModelProfile> downloadImageProfile(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("download_data_profil.php")
    Call<ResponseModelDataProfil> downloadDataProfile(
            @Field("email") String email
    );

    //Register

    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseModel> ardRegisterData(
            @Field("email") String email,
            @Field("nama") String nama,
            @Field("sandi") String sandi,
            @Field("kelamin") String kelamin,
            @Field("foto") String foto,
            @Field("umur") int umur
    );

    //Login

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseModel> ardLoginData(
            @Field("email") String email,
            @Field("sandi") String sandi
    );

    //Data Gejala

    @FormUrlEncoded
    @POST("create_history.php")
    Call<ResponseModelDataCekGejala> ardCekDepresi(
            @Field("id_pengguna") int id_pengguna,
            @Field("kode_gejala[]") ArrayList<String> arrayListKodeGejala
    );

    @FormUrlEncoded
    @POST("delete_data_history.php")
    Call<ResponseModelDataHistory> ardDeleteHistory(
            @Field("id_pengguna") int id_pengguna,
            @Field("kode_history") String kode_history
    );

    @FormUrlEncoded
    @POST("delete_data_history_pilihan.php")
    Call<ResponseModelDataHistoryPilihan> ardDeleteHistoryPilihan(
            @Field("kode_history") String kode_history
    );

    //Data Gejala

    @GET("retrieve.php")
    Call<ResponseModel> ardRetrieveData();

    @FormUrlEncoded
    @POST("create_data_tingkatan_gejala.php")
    Call<ResponseModel> ardCreateDataTingkatanGejala(
            @Field("kode_gejala") String kode_gejala,
            @Field("depresi_ringan") String depresi_ringan,
            @Field("depresi_sedang") String depresi_sedang,
            @Field("depresi_berat") String depresi_berat
    );

    @FormUrlEncoded
    @POST("create_data_gejala.php")
    Call<ResponseModel> ardCreateDataGejala(
      @Field("kode_gejala") String kode_gejala,
      @Field("nama_gejala") String nama_gejala,
      @Field("bobot_gejala") double bobot_gejala
    );

    @FormUrlEncoded
    @POST("delete_data_gejala.php")
    Call<ResponseModel> ardDeleteDataGejala(
            @Field("kode_gejala") String kode_gejala
    );

    @FormUrlEncoded
    @POST("delete_tingkatan_gejala.php")
    Call<ResponseModel> ardDeleteTingkatanGejala(
            @Field("kode_gejala") String kode_gejala
    );

    @FormUrlEncoded
    @POST("update_data_gejala.php")
    Call<ResponseModel> ardUpdateDataGejala(
            @Field("kode_gejala") String kode_gejala,
            @Field("nama_gejala") String nama_gejala,
            @Field("bobot_gejala") double bobot_gejala
    );

    @FormUrlEncoded
    @POST("update_tingkatan_gejala.php")
    Call<ResponseModel> ardUpdateTingkatanGejala(
            @Field("kode_gejala") String kode_gejala,
            @Field("depresi_ringan") String depresi_ringan,
            @Field("depresi_sedang") String depresi_sedang,
            @Field("depresi_berat") String depresi_berat
    );

    //Artikel

    @FormUrlEncoded
    @POST("create_artikel.php")
    Call<ResponseModel> ardAddArtikelData(
            @Field("kode_artikel") String kode_artikel,
            @Field("judul_artikel") String judul_artikel,
            @Field("isi_artikel") String isi_artikel,
            @Field("tingkat_depresi") String tingkat_depresi,
            @Field("gambar_artikel") String encodedImage
    );

    @FormUrlEncoded
    @POST("update_artikel.php")
    Call<ResponseModel> ardUpdateArtikelData(
            @Field("kode_artikel") String kode_artikel,
            @Field("judul_artikel") String judul_artikel,
            @Field("isi_artikel") String isi_artikel,
            @Field("tingkat_depresi") String tingkat_depresi
    );

    @FormUrlEncoded
    @POST("retrieve_artikel_pengguna.php")
    Call<ResponseModel> ardRetrieveArtikelPenggunaData(
            @Field("tingkat_depresi") String tingkat_depresi
    );

    @FormUrlEncoded
    @POST("download_image_artikel.php")
    Call<ResponseModel> downloadImage(
            @Field("kode_artikel") String kode_artikel
    );

    @GET("retrieve_artikel.php")
    Call<ResponseModel> ardRetrieveArtikelData();

    @FormUrlEncoded
    @POST("delete_artikel.php")
    Call<ResponseModel> ardDeleteArtikelData(
            @Field("kode_artikel") String kode_artikel
    );

}
