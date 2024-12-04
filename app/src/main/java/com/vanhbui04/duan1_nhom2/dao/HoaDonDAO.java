package com.vanhbui04.duan1_nhom2.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vanhbui04.duan1_nhom2.model.ChiTiet;
import com.vanhbui04.duan1_nhom2.model.HoaDon;
import com.vanhbui04.duan1_nhom2.database.DBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoaDonDAO {
    private DBHelper dbHelper;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public HoaDonDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public ArrayList<HoaDon> getALLSACH(String sql, String... selectionArgs) {
        ArrayList<HoaDon> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery(sql, selectionArgs);

        while (cursor.moveToNext()) {
            try {
                @SuppressLint("Range") Date ngay = sdf.parse(cursor.getString(cursor.getColumnIndex("ngay")));


                @SuppressLint("Range") HoaDon s = new HoaDon(
                        cursor.getInt(cursor.getColumnIndex("maHD")),
                        cursor.getInt(cursor.getColumnIndex("maTk")),
                        (double) cursor.getDouble(cursor.getColumnIndex("tongTien")),
                        ngay,
                        cursor.getInt(cursor.getColumnIndex("trangThai")),
                        cursor.getString(cursor.getColumnIndex("phuongThuc"))
                );
                list.add(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return list;
    }

    public long insert(HoaDon s) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maTk", s.getMaTk());
        values.put("tongTien", s.getTongTien());
        values.put("ngay",  sdf.format(s.getNgay()));
        values.put("trangThai", s.getTrangThai());
        values.put("phuongThuc", s.getPhuongthuc());

        return database.insert("HoaDon", null, values);
    }

    public ArrayList<HoaDon> getAll() {
        String sql = "SELECT * FROM HoaDon";
        return getALLSACH(sql);
    }
    public ArrayList<HoaDon> getAllByMaKhachHang(int maKhachHang) {
        String sql = "SELECT * FROM HoaDon WHERE maTk = ?";
        String[] selectionArgs = { String.valueOf(maKhachHang) };
        return getALLSACH(sql, selectionArgs);
    }

    @SuppressLint("Range")
    public int getMaHoaDonMoiNhat() {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String sql = "SELECT MAX(maHd) FROM HoaDon";

        Cursor cursor = db.rawQuery(sql, null);
        int maGioHang = 0;
        if (cursor.moveToFirst()) {
            maGioHang = cursor.getInt(0);
        }

        cursor.close();
        return maGioHang;
    }

    public HoaDon getID(String id) {
        String sql = "select * from HoaDon where maHD=?";
        ArrayList<HoaDon> list = getALLSACH(sql, id);

        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return new HoaDon();
        }
    }

    public long update(int maHD, int trangThai) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("trangThai", trangThai);
        return database.update("HoaDon", values, "maHD=?", new String[]{String.valueOf(maHD)});
    }

    public List<ChiTiet> getChiTietByMaHoaDon(int maHoaDon) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<ChiTiet> chiTietList = new ArrayList<>();

        // Câu truy vấn SQL lấy danh sách chi tiết đơn hàng thuộc mã hóa đơn
        String query = "SELECT * FROM ChiTietDonHang WHERE maHd = ?";
        String[] selectionArgs = {String.valueOf(maHoaDon)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int maChiTiet = cursor.getInt(cursor.getColumnIndex("maCTDH"));
                @SuppressLint("Range") int mahd = cursor.getInt(cursor.getColumnIndex("maHD"));
                @SuppressLint("Range") int madt = cursor.getInt(cursor.getColumnIndex("maDT"));
                @SuppressLint("Range") int soLuong = cursor.getInt(cursor.getColumnIndex("soLuong"));
                @SuppressLint("Range") Double giatien = cursor.getDouble(cursor.getColumnIndex("giaTien"));

                // Thêm đối tượng ChiTiet vào danh sách
                ChiTiet chiTiet = new ChiTiet(maChiTiet, mahd, madt, soLuong, giatien);
                chiTietList.add(chiTiet);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return chiTietList;
    }
}
