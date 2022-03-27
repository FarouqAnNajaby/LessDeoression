package com.example.less_depression.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Pengguna extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PenggunaKu.db";
    private static final int DATABASE_VERTSION = 1;
    public Pengguna(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERTSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String sql = "create table biodata(no integer primary key, email String null);";
        Log.d("Data","onCreate: "+sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2){

    }
}
