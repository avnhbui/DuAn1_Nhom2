package com.vanhbui04.duan1_nhom2.model;

import java.util.Date;

public class DanhGia {
    private int maDg;
    private int maDt;
    private int maTk;
    private int diem;
    private Date thoigian;
    private String nhanxet;

    public DanhGia(int maDg, int maDt, int maTk, int diem, Date thoigian, String nhanxet) {
        this.maDg = maDg;
        this.maDt = maDt;
        this.maTk = maTk;
        this.diem = diem;
        this.thoigian = thoigian;
        this.nhanxet = nhanxet;
    }

    public DanhGia() {
    }

    public int getMaDg() {
        return maDg;
    }

    public void setMaDg(int maDg) {
        this.maDg = maDg;
    }

    public int getMaDt() {
        return maDt;
    }

    public void setMaDt(int maDt) {
        this.maDt = maDt;
    }

    public int getMaTk() {
        return maTk;
    }

    public void setMaTk(int maTk) {
        this.maTk = maTk;
    }

    public int getDiem() {
        return diem;
    }

    public void setDiem(int diem) {
        this.diem = diem;
    }

    public Date getThoigian() {
        return thoigian;
    }

    public void setThoigian(Date thoigian) {
        this.thoigian = thoigian;
    }

    public String getNhanxet() {
        return nhanxet;
    }

    public void setNhanxet(String nhanxet) {
        this.nhanxet = nhanxet;
    }
}
