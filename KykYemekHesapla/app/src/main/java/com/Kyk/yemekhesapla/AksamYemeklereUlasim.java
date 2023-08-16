package com.Kyk.yemekhesapla;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class AksamYemeklereUlasim {


    public ArrayList<YemekVerileri> tumYemekler(VeriTabaniAksam veriTabaniAksam){

        ArrayList<YemekVerileri> yemekVerileriArrayList = new ArrayList<>();

        SQLiteDatabase db = veriTabaniAksam.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM aksam",null);

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

    public void yemekSil(VeriTabaniAksam veriTabaniAksam,int yemekId){
        SQLiteDatabase db = veriTabaniAksam.getWritableDatabase();
        db.delete("aksam","yemekId=?",new String[]{String.valueOf(yemekId)});
        db.close();
    }

    public void yemekEkle(VeriTabaniAksam veriTabaniAksam, String yemek_adi, Double yemek_ucreti){

        SQLiteDatabase db = veriTabaniAksam.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("yemek_adi",yemek_adi);

        values.put("yemek_ucreti",yemek_ucreti);

        db.insertOrThrow("aksam",null,values);

        db.close();

    }


    public void yemekGuncelle(VeriTabaniAksam veriTabaniAksam, int yemekId, String yemek_adi, Double yemek_ucreti){

        SQLiteDatabase db = veriTabaniAksam.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("yemek_adi",yemek_adi);
        values.put("yemek_ucreti",yemek_ucreti);

        db.update("aksam",values,"yemekId=?",new String[]{String.valueOf(yemekId)});
        db.close();

    }


}
