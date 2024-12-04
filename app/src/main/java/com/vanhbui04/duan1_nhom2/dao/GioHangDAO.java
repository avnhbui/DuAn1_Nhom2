package com.vanhbui04.duan1_nhom2.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vanhbui04.duan1_nhom2.model.GioHang;
import com.vanhbui04.duan1_nhom2.database.DBHelper;

import java.util.ArrayList;

public class GioHangDAO {
    private DBHelper dbHelper;

    public GioHangDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public ArrayList<GioHang> getALLGIOHANG(String sql, String... selectionArgs) {
        ArrayList<GioHang> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery(sql, selectionArgs);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") GioHang s = new GioHang(
                    cursor.getInt(cursor.getColumnIndex("maGh")),
                    cursor.getInt(cursor.getColumnIndex("maDT")),
                    cursor.getDouble(cursor.getColumnIndex("giaTien")),
                    cursor.getInt(cursor.getColumnIndex("soLuong")),
                    cursor.getInt(cursor.getColumnIndex("maTk"))

            );
            list.add(s);
        }
        cursor.close();
        return list; //
    }

    public long insert(GioHang s) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maDT", s.getMadt());
        values.put("giaTien", s.getGia());
        values.put("soLuong", s.getSoLuong());
        values.put("maTk", s.getMaTk());

        return database.insert("GioHang", null, values); // Trả về ID của hàng được chèn
    }

    public long deleteAllGioHang(int matk) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String whereClause = "maTk=?";
        String[] whereArgs = {String.valueOf(matk)};
        return database.delete("GioHang", whereClause, whereArgs);
    }

    public long delete(int maTk, int maDT) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String whereClause = "maTk=? AND maDT=?";
        String[] whereArgs = {String.valueOf(maTk), String.valueOf(maDT)};
        long check = database.delete("GioHang", whereClause, whereArgs);
        return check;
    }

    public boolean checkExistence(int tenTaiKhoan, int maDT) {
        String query = "SELECT COUNT(*) FROM GioHang WHERE maTk = ? AND maDT = ?";
        String[] selectionArgs = {String.valueOf(tenTaiKhoan), String.valueOf(maDT)};
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, selectionArgs);
        int count = 0;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            cursor.close();
        }

        return (count > 0);
    }

//    public ArrayList<GioHang> getAll() {
//        String sql = "SELECT * FROM GioHang";
//        return (ArrayList<GioHang>) getALLGIOHANG(sql);
//    }

    public ArrayList<GioHang> getAllByMaKhachHang(int maKhachHang) {
        String sql = "SELECT * FROM GioHang WHERE maTk = ?";
        String[] selectionArgs = {String.valueOf(maKhachHang)};
        return getALLGIOHANG(sql, selectionArgs);
    }
}
