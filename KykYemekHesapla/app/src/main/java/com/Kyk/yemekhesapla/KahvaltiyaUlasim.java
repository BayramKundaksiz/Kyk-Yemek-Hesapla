package com.Kyk.yemekhesapla;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class KahvaltiyaUlasim {


    public ArrayList<YemekVerileri> tumYemekler(VeriTabaniKahvalti vt){

        ArrayList<YemekVerileri> yemekVerileriArrayList = new ArrayList<>();

        SQLiteDatabase db = vt.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM kahvalti",null);

        while (c.moveToNext()){
            @SuppressLint("Range")

            YemekVerileri y = new YemekVerileri(
                    c.getInt(c.getColumnIndex("yemekId")),
                    c.getString(c.getColumnIndex("yemek_adi"))
                    ,c.getDouble(c.getColumnIndex("yemek_ucreti")));
            yemekVerileriArrayList.add(y);
        }

        db.close();
        return yemekVerileriArrayList;
    }

    public void yemekSil(VeriTabaniKahvalti vt, int yemekId){
        SQLiteDatabase db = vt.getWritableDatabase();
        db.delete("kahvalti","yemekId=?",new String[]{String.valueOf(yemekId)});
        db.close();
    }

    public void yemekEkle(VeriTabaniKahvalti vt, String yemek_adi, Double yemek_ucreti){

        SQLiteDatabase db = vt.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("yemek_adi",yemek_adi);

        values.put("yemek_ucreti",yemek_ucreti);

        db.insertOrThrow("kahvalti",null,values);

        db.close();

    }


    public void yemekGuncelle(VeriTabaniKahvalti vt, int yemekId, String yemek_adi, Double yemek_ucreti){

        SQLiteDatabase db = vt.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("yemek_adi",yemek_adi);
        values.put("yemek_ucreti",yemek_ucreti);

        db.update("kahvalti",values,"yemekId=?",new String[]{String.valueOf(yemekId)});

        db.close();

    }


}
