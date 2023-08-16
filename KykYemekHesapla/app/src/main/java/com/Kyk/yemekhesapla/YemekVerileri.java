package com.Kyk.yemekhesapla;

public class YemekVerileri {

    int yemekId;
    String yemekAdi;
    Double yemekUcreti;


    public YemekVerileri(int yemekId, String yemekAdi, Double yemekUcreti) {
        this.yemekId = yemekId;
        this.yemekAdi = yemekAdi;
        this.yemekUcreti = yemekUcreti;
    }

    public int getYemekId() {
        return yemekId;
    }

    public void setYemekId(int yemekId) {
        this.yemekId = yemekId;
    }

    public String getYemekAdi() {
        return yemekAdi;
    }

    public void setYemekAdi(String yemekAdi) {
        this.yemekAdi = yemekAdi;
    }

    public Double getYemekUcreti() {
        return yemekUcreti;
    }

    public void setYemekUcreti(Double yemekUcreti) {
        this.yemekUcreti = yemekUcreti;
    }
}