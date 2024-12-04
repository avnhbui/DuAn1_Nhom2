package com.vanhbui04.duan1_nhom2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "duan1";
    public static final int DB_VERSION = 31;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createLoaiSeriTable = "CREATE TABLE LoaiSeriesDT " +
                "(maLoaiSeries INTEGER PRIMARY KEY AUTOINCREMENT ," +
                " tenLoaiSeries TEXT)";

        String createDienThoaiTable = "CREATE TABLE DienThoai" +
                " (maDT INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "maLoaiSeries INTEGER," +
                "imageUrl BLOB," +
                "tenDT TEXT, " +
                "giaTien DOUBLE, " +
                "moTa TEXT," +
                "soLuong INTEGER," +
                "trangThai INTEGER, FOREIGN KEY (maLoaiSeries) REFERENCES LoaiSeriesDT(maLoaiSeries))";

        String createTaiKhoanTable = "CREATE TABLE TaiKhoan " +
                "(maTk INTEGER PRIMARY KEY AUTOINCREMENT," +
                " tenDN TEXT , " +
                "matKhau TEXT," +
                "hoTen TEXT," +
                "sdt TEXT," +
                "diaChi TEXT)";
        String createAdminTable = "CREATE TABLE Admin " +
                "(maTk INTEGER PRIMARY KEY AUTOINCREMENT," +
                " tenDN TEXT , " +
                "matKhau TEXT," +
                "hoTen TEXT," +
                "sdt TEXT," +
                "diaChi TEXT)";
        String createRatingTable = "CREATE TABLE DanhGia " +
                "(maDG INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maDT INTEGER, " +
                "maTk INTEGER, " +
                "diem INTEGER, " +
                "nhanXet TEXT," +
                "ngay DATE, " +
                "FOREIGN KEY (maDT) REFERENCES DienThoai(maDT), " +
                "FOREIGN KEY (maTk) REFERENCES TaiKhoan(maTk))";


        String createGioHangTable = "CREATE TABLE GioHang" +
                " (maGh INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maTk INTEGER," +
                " maDT TEXT," +
                " giaTien DOUBLE," +
                " soLuong INTEGER," +
                "FOREIGN KEY (maDT) REFERENCES DienThoai(maDT), " +
                "FOREIGN KEY (maTk) REFERENCES TaiKhoan(maTk))";

        String createHoaDonTable = "CREATE TABLE HoaDon " +
                "(maHD INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maTk INTEGER, " +
                "tongTien DOUBLE, " +
                "ngay DATE, " +
                "trangThai INTEGER, " +
                "phuongThuc TEXT, " +
                "FOREIGN KEY (maTk) REFERENCES TaiKhoan(maTk))";

        String createChiTietDonHangTable = "CREATE TABLE ChiTietDonHang " +
                "(maCTDH INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maHD INTEGER, " +
                "maDT INTEGER, " +
                "soLuong INTEGER, " +
                "giaTien DOUBLE, " +
                "FOREIGN KEY (maHD) REFERENCES HoaDon(maHD), " +
                "FOREIGN KEY (maDT) REFERENCES DienThoai(maDT))";


        db.execSQL(createLoaiSeriTable);
        db.execSQL(createDienThoaiTable);
        db.execSQL(createTaiKhoanTable);
        db.execSQL(createHoaDonTable);
        db.execSQL(createGioHangTable);
        db.execSQL(createChiTietDonHangTable);
        db.execSQL(createRatingTable);
        db.execSQL(createAdminTable);


        // Tạo các bảng

        // Thêm dữ liệu mẫu vào bảng LoaiSeriesDT


//        // Thêm dữ liệu mẫu vào bảng DienThoai
        db.execSQL("INSERT INTO DienThoai (maDT, maLoaiSeries, imageUrl, tenDT, giaTien, moTa, soLuong, trangThai) VALUES (1, 1, 'iphone16.jpg', 'Iphone 16', 30000000, 'Thời gian xem video lên đến 26 giờ chú thích ⁴\n" +
                "Hệ thống camera kép tiên tiến" +
                "Chính 48MP | Ultra Wide" +
                "Ảnh có độ phân giải siêu cao (24MP và 48MP)" +
                "Ảnh chân dung thế hệ mới với Focus và Depth Control" +
                "Chip A16 Bionic với GPU 5 lõi\n" +
                "Màn hình Super Retina XDR chú thích " +
                "Màn hình Luôn Bật" +
                "Công nghệ ProMotionDynamic Island" +
                "Một cách tuyệt diệu để tương tác với iPhone', 10,0)");


        db.execSQL("INSERT INTO LoaiSeriesDT (maLoaiSeries, tenLoaiSeries) VALUES (1, 'Series 16')");
        db.execSQL("INSERT INTO LoaiSeriesDT (maLoaiSeries, tenLoaiSeries) VALUES (2, 'Series 15')");
        db.execSQL("INSERT INTO LoaiSeriesDT (maLoaiSeries, tenLoaiSeries) VALUES (3, 'Series 14')");
        // Thêm dữ liệu mẫu vào bảng TaiKhoan
        db.execSQL("INSERT INTO Admin (maTk, tenDN, matKhau,hoTen,sdt,diaChi) VALUES (1, 'admin', '123','Vanh Bui',0348426647 ,'hanoi')");
        db.execSQL("INSERT INTO TaiKhoan (maTk, tenDN, matKhau,hoTen,sdt,diaChi) VALUES (1, 'user', '123','Nguyễn Văn A', 0987654321,'TP HCM')");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i != i1) {
            db.execSQL("drop table if exists TaiKhoan");
            db.execSQL("drop table if exists DienThoai");
            db.execSQL("drop table if exists LoaiSeriesDT");
            db.execSQL("drop table if exists HoaDon");
            db.execSQL("drop table if exists GioHang");
            db.execSQL("drop table if exists ChiTietDonHang");
            db.execSQL("drop table if exists Admin");
            db.execSQL("drop table if exists DanhGia");

            onCreate(db);
        }
    }

}

