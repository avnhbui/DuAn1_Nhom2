package com.vanhbui04.duan1_nhom2.model;

public class GioHang {
    private int maGh;
    private int madt;
    private Double gia;
    private int soLuong;
    private int maTk;

    public GioHang(int maGh,int madt, Double gia, int soLuong,int maTk) {
        this.maGh = maGh;
        this.madt = madt;
        this.gia = gia;
        this.soLuong = soLuong ;
        this.maTk = maTk ;

    }

    public GioHang(int madt, Double gia, int soLuong, int maTk) {
        this.madt = madt;
        this.gia = gia;
        this.soLuong = soLuong;
        this.maTk = maTk;
    }
    //    public GioHang(int madt, Double gia, int soLuong) {
//        this.madt = madt;
//        this.gia = gia;
//        this.soLuong = soLuong;
//    }

    public int getMaTk() {
        return maTk;
    }

    public void setMaTk(int maTk) {
        this.maTk = maTk;
    }

    public int getMadt() {
        return madt;
    }

    public void setMadt(int madt) {
        this.madt = madt;
    }

    public Double getGia() {
        return gia;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }

    public int getMaGh() {
        return maGh;
    }

    public void setMaGh(int maGh) {
        this.maGh = maGh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
