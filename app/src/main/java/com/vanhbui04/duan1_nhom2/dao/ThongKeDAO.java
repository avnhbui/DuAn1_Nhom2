package com.vanhbui04.duan1_nhom2.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vanhbui04.duan1_nhom2.model.DienThoai;
import com.vanhbui04.duan1_nhom2.model.Top;
import com.vanhbui04.duan1_nhom2.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {
    private Context context;
    private DBHelper dbhelper;

    public ThongKeDAO(Context context) {
        dbhelper = new DBHelper(context);
        this.context = context;
    }


    @SuppressLint("Range")
    public List<Top> getTop() {
        String sqlTop = "SELECT maDT, SUM(soLuong) AS soLuong FROM ChiTietDonHang GROUP BY maDT ORDER BY soLuong DESC LIMIT 10";
        List<Top> list = new ArrayList<>();
        DienThoaiDAO dao = new DienThoaiDAO(context);
        SQLiteDatabase database = dbhelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(sqlTop, null);

        while (cursor.moveToNext()) {
            Top top = new Top();
            @SuppressLint("Range") DienThoai dienThoai = dao.getID(cursor.getString(cursor.getColumnIndex("maDT")));
            top.tenDt = dienThoai.getTenDT();
            top.soLuong = Integer.parseInt(cursor.getString(cursor.getColumnIndex("soLuong")));
            list.add(top);
        }

        cursor.close();
        return list;
    }

    @SuppressLint("Range")
    public int getDoanhThu(String tuNgay, String denNgay) {
        String sqlDoanhThu = "SELECT SUM(tongTien) AS doanhThu FROM HoaDon WHERE ngay BETWEEN ? AND ? AND trangThai = 3";
        SQLiteDatabase database = dbhelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sqlDoanhThu, new String[]{tuNgay, denNgay});

        int doanhThu = 0; // Khởi tạo tổng doanh thu

        if (cursor.moveToFirst()) {
            do {
                // Lấy giá trị tổng doanh thu từ Cursor
                doanhThu = cursor.getInt(cursor.getColumnIndex("doanhThu"));
            } while (cursor.moveToNext());
        }

        // Đóng con trỏ Cursor và tài nguyên cơ sở dữ liệu
        cursor.close();
        database.close();

        return doanhThu;
    }
}
