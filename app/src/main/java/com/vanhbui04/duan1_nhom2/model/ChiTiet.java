package com.vanhbui04.duan1_nhom2.model;

import java.util.Date;

public class ChiTiet {
    private int mact;
    private int mahd;
    private int madt;
    private int soluong;
    private Double giatien;

    public ChiTiet() {
    }


    public ChiTiet(int mact, int mahd, int madt, int soluong, Double giatien) {
        this.mact = mact;
        this.mahd = mahd;
        this.madt = madt;
        this.soluong = soluong;
        this.giatien = giatien;
    }

    public int getMact() {
        return mact;
    }

    public void setMact(int mact) {
        this.mact = mact;
    }

    public int getMahd() {
        return mahd;
    }

    public void setMahd(int mahd) {
        this.mahd = mahd;
    }

    public int getMadt() {
        return madt;
    }

    public void setMadt(int madt) {
        this.madt = madt;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public Double getGiatien() {
        return giatien;
    }

    public void setGiatien(Double giatien) {
        this.giatien = giatien;
    }
    //        "(maCTDH INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "maHD INTEGER, " +
//                "maDT INTEGER, " +
//                "soLuong INTEGER, " +
//                "giaTien DOUBLE, " +
//                "FOREIGN KEY (maHD) REFERENCES HoaDon(maHD), " +
//                "FOREIGN KEY (maDT) REFERENCES DienThoai(maDT))";

    public static class DanhGia {
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

        public Date getThoigian() {
            return thoigian;
        }

        public void setThoigian(Date thoigian) {
            this.thoigian = thoigian;
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

        public String getNhanxet() {
            return nhanxet;
        }

        public void setNhanxet(String nhanxet) {
            this.nhanxet = nhanxet;
        }

        public int getMaDg() {
            return maDg;
        }

        public void setMaDg(int maDg) {
            this.maDg = maDg;
        }
    }
}
