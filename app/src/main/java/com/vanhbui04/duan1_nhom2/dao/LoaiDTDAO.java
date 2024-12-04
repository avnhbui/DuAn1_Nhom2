package com.vanhbui04.duan1_nhom2.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vanhbui04.duan1_nhom2.model.LoaiSeries;
import com.vanhbui04.duan1_nhom2.database.DBHelper;

import java.util.ArrayList;

public class LoaiDTDAO {
    private DBHelper dBhelper;
    SQLiteDatabase database;
    public LoaiDTDAO(Context context) {
        dBhelper = new DBHelper(context);
    }
    public long add(LoaiSeries loaiSeries) {
        ContentValues values = new ContentValues();
        database = dBhelper.getReadableDatabase();
        values.put("tenLoaiSeries",loaiSeries.getTenLoaiSeri());

        return database.insert("LoaiSeriesDT", null, values);
    }
    public int update(LoaiSeries loaiSeries) {
        database = dBhelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenLoaiSeries", loaiSeries.getTenLoaiSeri());

        return database.update("LoaiSeriesDT", values, "maLoaiSeries = ?", new String[]{String.valueOf(loaiSeries.getMaLoaiSeri())});
    }

    public ArrayList<LoaiSeries> getALLSACH(String sql, String... selectionArgs) {
        ArrayList<LoaiSeries> list = new ArrayList<>();
        database = dBhelper.getReadableDatabase();

        Cursor cursor = database.rawQuery(sql, selectionArgs);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") LoaiSeries ls = new LoaiSeries(
                    cursor.getInt(cursor.getColumnIndex("maLoaiSeries")),
                    cursor.getString(cursor.getColumnIndex("tenLoaiSeries"))
            );
            list.add(ls);
        }


        cursor.close();

        return list;
    }


    public ArrayList<LoaiSeries> getAll() {
        String sql = "SELECT * FROM LoaiSeriesDT";
        return getALLSACH(sql);
    }

    public LoaiSeries getID(String id) {
        String sql = "select * from LoaiSeriesDT where maLoaiSeries=?";
        ArrayList<LoaiSeries> list = getALLSACH(sql, id);

        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return new LoaiSeries();
        }
    }
}
