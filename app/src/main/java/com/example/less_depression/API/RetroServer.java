package com.example.less_depression.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {

    private static final String baseURL = "http://192.168.1.14/depresi/";
//private static final String baseURL = "https://www.api.javadevelopergroup.dev/";
    private static Retrofit retro;

    public static Retrofit koneksiRetrofil(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retro == null){
            retro = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retro;
    }

}
