package com.Kyk.yemekhesapla;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class VeriTabaniAksam extends SQLiteOpenHelper {

    public VeriTabaniAksam(@Nullable Context context) {
        super(context, "aksam.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE aksam (yemekId INTEGER PRIMARY KEY AUTOINCREMENT, yemek_adi TEXT, yemek_ucreti DOUBLE);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS aksam");
        onCreate(sqLiteDatabase);
    }
}
