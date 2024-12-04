package com.vanhbui04.duan1_nhom2.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vanhbui04.duan1_nhom2.model.DanhGia;
import com.vanhbui04.duan1_nhom2.database.DBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DanhGiaDAO {
    private DBHelper dbHelper;

    public DanhGiaDAO(Context context) {
        dbHelper = new DBHelper(context);
    }


    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public ArrayList<DanhGia> getALLSACH(String sql, String... selectionArgs) {
        ArrayList<DanhGia> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery(sql, selectionArgs);

        while (cursor.moveToNext()) {
            try {
                @SuppressLint("Range") Date ngay = sdf.parse(cursor.getString(cursor.getColumnIndex("ngay")));


                @SuppressLint("Range") DanhGia s = new DanhGia(
                        cursor.getInt(cursor.getColumnIndex("maDG")),
                        cursor.getInt(cursor.getColumnIndex("maDT")),
                        cursor.getInt(cursor.getColumnIndex("maTk")),
                        cursor.getInt(cursor.getColumnIndex("diem")),
                        ngay,
                        cursor.getString(cursor.getColumnIndex("nhanXet"))
                );
                list.add(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return list;
    }

    public long insert(DanhGia dg) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maDT", dg.getMaDt());
        values.put("maTk", dg.getMaTk());
        values.put("diem", dg.getDiem());
        values.put("nhanXet", dg.getNhanxet());
        values.put("ngay",  sdf.format(dg.getThoigian()));

        return database.insert("DanhGia", null, values); // Trả về ID của hàng được chèn
    }
    public ArrayList<DanhGia> getAll() {
        String sql = "SELECT * FROM DanhGia";
        return getALLSACH(sql);
    }


    public ArrayList<DanhGia> getBymatk(int maDT) {
        ArrayList<DanhGia> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String sql = "SELECT * FROM DanhGia WHERE maDT = ?";
        String[] selectionArgs = {String.valueOf(maDT)};

        Cursor cursor = database.rawQuery(sql, selectionArgs);

        while (cursor.moveToNext()) {
            try {
                @SuppressLint("Range") String ngayString = cursor.getString(cursor.getColumnIndex("ngay"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date ngay = sdf.parse(ngayString);

                @SuppressLint("Range") DanhGia danhGia = new DanhGia(
                        cursor.getInt(cursor.getColumnIndex("maDG")),
                        cursor.getInt(cursor.getColumnIndex("maDT")),
                        cursor.getInt(cursor.getColumnIndex("maTk")),
                        cursor.getInt(cursor.getColumnIndex("diem")),
                        ngay,
                        cursor.getString(cursor.getColumnIndex("nhanXet"))
                );
                list.add(danhGia);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return list;
    }
}
