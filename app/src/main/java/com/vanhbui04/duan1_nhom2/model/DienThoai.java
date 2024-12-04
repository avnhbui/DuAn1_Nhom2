package com.vanhbui04.duan1_nhom2.model;

public class DienThoai {
    private int maDT;
    private int maLoaiSeri;
    private byte[] anhDT;
    private String tenDT;
    private double giaTien;
    private String moTa;
    private int soLuong;
    private int trangThai;

    public DienThoai(int maDT, int maLoaiSeri,byte[] anhDT, String tenDT, double giaTien, String moTa,int soLuong, int trangThai) {
        this.maDT = maDT;
        this.maLoaiSeri = maLoaiSeri;
        this.anhDT = anhDT;
        this.tenDT = tenDT;
        this.giaTien = giaTien;
        this.moTa = moTa;
        this.soLuong=soLuong;
        this.trangThai = trangThai;
    }

    public byte[] getAnhDT() {
        return anhDT;
    }

    public void setAnhDT(byte[] anhDT) {
        this.anhDT = anhDT;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setMaDT(int maDT) {
        this.maDT = maDT;
    }

    public void setMaLoaiSeri(int maLoaiSeri) {
        this.maLoaiSeri = maLoaiSeri;
    }

    public void setTenDT(String tenDT) {
        this.tenDT = tenDT;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public DienThoai() {
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int getMaDT() {
        return maDT;
    }

    public int getMaLoaiSeri() {
        return maLoaiSeri;
    }

    public String getTenDT() {
        return tenDT;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public String getMoTa() {
        return moTa;
    }
}
