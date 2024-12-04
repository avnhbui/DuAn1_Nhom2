package com.vanhbui04.duan1_nhom2.model;

public class LoaiSeries {
    private int maLoaiSeri;
    private String tenLoaiSeri;

    public LoaiSeries() {
    }

    public LoaiSeries(int maLoaiSeri, String tenLoaiSeri) {
        this.maLoaiSeri = maLoaiSeri;
        this.tenLoaiSeri = tenLoaiSeri;
    }

    public void setMaLoaiSeri(int maLoaiSeri) {
        this.maLoaiSeri = maLoaiSeri;
    }

    public void setTenLoaiSeri(String tenLoaiSeri) {
        this.tenLoaiSeri = tenLoaiSeri;
    }

    public int getMaLoaiSeri() {
        return maLoaiSeri;
    }

    public String getTenLoaiSeri() {
        return tenLoaiSeri;
    }
}
