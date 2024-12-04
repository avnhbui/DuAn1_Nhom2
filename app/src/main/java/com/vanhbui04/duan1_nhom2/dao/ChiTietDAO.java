package com.vanhbui04.duan1_nhom2.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vanhbui04.duan1_nhom2.model.ChiTiet;
import com.vanhbui04.duan1_nhom2.database.DBHelper;

import java.util.ArrayList;

public class ChiTietDAO {
    private DBHelper dbHelper;

    public ChiTietDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long insert(ChiTiet chiTietSanPham) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maHD", chiTietSanPham.getMahd());
        values.put("maDT", chiTietSanPham.getMadt());
        values.put("soLuong", chiTietSanPham.getSoluong());
        values.put("giaTien", chiTietSanPham.getGiatien());
        return db.insert("ChiTietDonHang", null, values);
    }

    public ArrayList<ChiTiet> getALLct(String sql, String... selectionArgs) {
        ArrayList<ChiTiet> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery(sql, selectionArgs);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") ChiTiet s = new ChiTiet(
                    cursor.getInt(cursor.getColumnIndex("maCTDH")),
                    cursor.getInt(cursor.getColumnIndex("maHD")),
                    cursor.getInt(cursor.getColumnIndex("maDT")),
                    cursor.getInt(cursor.getColumnIndex("soLuong")),
                    cursor.getDouble(cursor.getColumnIndex("giaTien"))
            );
            list.add(s);
        }
        cursor.close();
        return list; //
    }

    public ArrayList<ChiTiet> getAll() {
        String sql = "SELECT * FROM ChiTietDonHang";
        return getALLct(sql);
    }
}
