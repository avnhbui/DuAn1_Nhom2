package com.vanhbui04.duan1_nhom2.model;

public class TaiKhoan {
    private int maTk;
    private String tenDN;
    private String matKhau;
    private String hoten;
    private String sdt;
    private String diachi;


    public TaiKhoan(int maTk, String tenDN, String matKhau,String hoten ,String sdt , String diachi) {
        this.maTk = maTk;
        this.tenDN = tenDN;
        this.matKhau = matKhau;
        this.hoten = hoten;
        this.sdt = sdt;
        this.diachi = diachi;
    }

    public TaiKhoan() {
    }

    public int getMaTk() {
        return maTk;
    }

    public void setMaTk(int maTk) {
        this.maTk = maTk;
    }

    public String getTenDN() {
        return tenDN;
    }

    public void setTenDN(String tenDN) {
        this.tenDN = tenDN;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
}
