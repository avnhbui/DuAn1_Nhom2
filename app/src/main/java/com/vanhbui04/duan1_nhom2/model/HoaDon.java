package com.vanhbui04.duan1_nhom2.model;

import java.util.Date;

public class HoaDon {
    private int maHD;
    private int maTk;
    private Double tongTien;
    private Date ngay;
    private int trangThai;
    private String phuongthuc;

    public HoaDon(int maHD, int maTk, Double tongTien, Date ngay, int trangThai, String phuongthuc) {
        this.maHD = maHD;
        this.maTk = maTk;
        this.tongTien = tongTien;
        this.ngay = ngay;
        this.trangThai = trangThai;
        this.phuongthuc = phuongthuc;
    }

    public HoaDon() {
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getMaTk() {
        return maTk;
    }

    public void setMaTk(int maTk) {
        this.maTk = maTk;
    }

    public Double getTongTien() {
        return tongTien;
    }

    public void setTongTien(Double tongTien) {
        this.tongTien = tongTien;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }


    public String getPhuongthuc() {
        return phuongthuc;
    }

    public void setPhuongthuc(String phuongthuc) {
        this.phuongthuc = phuongthuc;
    }
}
