package com.vanhbui04.duan1_nhom2.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vanhbui04.duan1_nhom2.model.DienThoai;
import com.vanhbui04.duan1_nhom2.database.DBHelper;

import java.util.ArrayList;

public class DienThoaiDAO {
    private DBHelper dbHelper;
    SQLiteDatabase database;

    public DienThoaiDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long add(DienThoai dienThoai) {
        ContentValues values = new ContentValues();
        database = dbHelper.getReadableDatabase();
        values.put("maLoaiSeries", dienThoai.getMaLoaiSeri());
        values.put("imageUrl", dienThoai.getAnhDT());
        values.put("tenDT", dienThoai.getTenDT());
        values.put("giaTien", dienThoai.getGiaTien());
        values.put("moTa", dienThoai.getMoTa());
        values.put("soLuong", dienThoai.getSoLuong());
        values.put("trangThai", dienThoai.getTrangThai());

        return database.insert("DienThoai", null, values);
    }

    public int update(DienThoai dienThoai, int maSPOld) {
        database = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("maLoaiSeries", dienThoai.getMaLoaiSeri());
        values.put("imageUrl", dienThoai.getAnhDT());
        values.put("tenDT", dienThoai.getTenDT());
        values.put("giaTien", dienThoai.getGiaTien());
        values.put("moTa", dienThoai.getMoTa());
        values.put("soLuong", dienThoai.getSoLuong());
        values.put("trangThai", dienThoai.getTrangThai());

        return database.update("DienThoai", values, "maDT = ?", new String[]{String.valueOf(maSPOld)});
    }

    public ArrayList<DienThoai> getALLSACH(String sql, String... selectionArgs) {
        ArrayList<DienThoai> list = new ArrayList<>();
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, selectionArgs);
        //maDT, maLoaiSeries, imageUrl, tenDT, giaTien, moTa,soLuong

        while (cursor.moveToNext()) {
            @SuppressLint("Range") DienThoai s = new DienThoai(
                    cursor.getInt(cursor.getColumnIndex("maDT")),
                    cursor.getInt(cursor.getColumnIndex("maLoaiSeries")),
                    cursor.getBlob(cursor.getColumnIndex("imageUrl")),
                    cursor.getString(cursor.getColumnIndex("tenDT")),
                    cursor.getDouble(cursor.getColumnIndex("giaTien")),
                    cursor.getString(cursor.getColumnIndex("moTa")),
                    cursor.getInt(cursor.getColumnIndex("soLuong")),
                    cursor.getInt(cursor.getColumnIndex("trangThai"))
            );
            list.add(s);
        }
        cursor.close(); // Đóng con trỏ khi hoàn thành công việc
        return list; //
    }

    public ArrayList<DienThoai> getAll() {
        String sql = "SELECT * FROM DienThoai";
        return (ArrayList<DienThoai>) getALLSACH(sql);
    }
    public ArrayList<DienThoai> getAllKD() {
        String sql = "SELECT * FROM DienThoai WHERE trangThai=0";
        return getALLSACH(sql);
    }
    public ArrayList<DienThoai> getAllKDD(int trangThai) {
        String sql = "SELECT * FROM DienThoai WHERE trangThai = ?";
        return getALLSACH(sql, String.valueOf(trangThai));
    }
    public ArrayList<DienThoai> getAllNKD(int trangThai) {
        String sql = "SELECT * FROM DienThoai WHERE trangThai = ?";
        return getALLSACH(sql, String.valueOf(trangThai));
    }

    public DienThoai getID(String id) {
        String sql = "select * from DienThoai where maDT=?";
        ArrayList<DienThoai> list = getALLSACH(sql, id);

        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return new DienThoai();
        }
    }

    public ArrayList<DienThoai> getDienThoaiByLoai(int maLoaiSeries) {
        String sql = "SELECT * FROM DienThoai WHERE maLoaiSeries = ? AND trangThai = 0";
        return getALLSACH(sql, String.valueOf(maLoaiSeries));
    }

    public void updateSoLuong(int maDT, int soLuongGiam) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String sqlUpdate = "UPDATE DienThoai SET soLuong = soLuong - ? WHERE maDT = ?";
        Object[] args = {soLuongGiam, maDT};
        db.execSQL(sqlUpdate, args);

        db.close();
    }

    @SuppressLint("Range")
    public byte[] getAnhByMaDT(int maDT) {
        byte[] anh = null;
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String sql = "SELECT imageUrl FROM DienThoai WHERE maDT = ?";
        String[] selectionArgs = {String.valueOf(maDT)};

        Cursor cursor = database.rawQuery(sql, selectionArgs);

        if (cursor.moveToFirst()) {
            anh = cursor.getBlob(cursor.getColumnIndex("imageUrl"));
        }

        cursor.close();
        return anh;
    }

}
