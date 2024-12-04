package com.vanhbui04.duan1_nhom2.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vanhbui04.duan1_nhom2.model.TaiKhoan;
import com.vanhbui04.duan1_nhom2.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class TaiKhoanDAO {
    private DBHelper dbHelper;

    public TaiKhoanDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public ArrayList<TaiKhoan> getALLTT(String sql, String... selectionArgs) {
        ArrayList<TaiKhoan> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery(sql, selectionArgs);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") TaiKhoan tt = new TaiKhoan(
                    cursor.getInt(cursor.getColumnIndex("maTk")),
                    cursor.getString(cursor.getColumnIndex("tenDN")),
                    cursor.getString(cursor.getColumnIndex("matKhau")),
                    cursor.getString(cursor.getColumnIndex("hoTen")),
                    cursor.getString(cursor.getColumnIndex("sdt")),
                    cursor.getString(cursor.getColumnIndex("diaChi"))

            );
            list.add(tt);
        }
        cursor.close(); // Đóng con trỏ khi hoàn thành công việc
        return list; //
    }

    public int checkLogin(String id, String pass) {
        String sql = "select * from TaiKhoan where tenDN=? and matKhau=?";
        String sqlAD = "select * from Admin where tenDN=? and matKhau=?";
        List<TaiKhoan> list = getALLTT(sql, id, pass);
        list.addAll(getALLTT(sqlAD, id, pass));
        if (list.size() == 0) {
            return -1;
        } else {
            return 1;
        }
    }
    public TaiKhoan getAD(String id) {
        String sql = "select * from Admin where tenDN=?";
        ArrayList<TaiKhoan> list = getALLTT(sql, id);

        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            // Trả về một giá trị LoaiSach mặc định hoặc tạo một đối tượng mới tùy ý
            return new TaiKhoan();
        }
    }
    public TaiKhoan getID(String id) {
        String sql = "select * from TaiKhoan where tenDN=?";
        ArrayList<TaiKhoan> list = getALLTT(sql, id);

        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            // Trả về một giá trị LoaiSach mặc định hoặc tạo một đối tượng mới tùy ý
            return new TaiKhoan();
        }
    }
    public String getSDT() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String result = "";
        Cursor cursor = database.rawQuery("SELECT sdt FROM Admin where maTk = 1", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0) + "\n";
        }
        cursor.close();
        return result;
    }

//    public void updateSdt(String newData, int id) {
//        ContentValues values = new ContentValues();
//        values.put("sdt", newData);
//
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.update("TaiKhoan", values, "maTk=?", new String[]{String.valueOf(id)});
//        db.close();
//    }

    public TaiKhoan getIDma(String id) {
        String sql = "select * from TaiKhoan where maTk=?";
        ArrayList<TaiKhoan> list = getALLTT(sql, id);

        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            // Trả về một giá trị LoaiSach mặc định hoặc tạo một đối tượng mới tùy ý
            return new TaiKhoan();
        }
    }



    public long insert(TaiKhoan s) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenDN", s.getTenDN());
        values.put("matKhau", s.getMatKhau());
        values.put("hoTen", s.getHoten());
        values.put("sdt", s.getSdt());
        values.put("diaChi", s.getDiachi());
        return database.insert("TaiKhoan", null, values); // Trả về ID của hàng được chèn
    }

    public long update(TaiKhoan user) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("tenDN",user.getTenDN());
        values.put("hoten", user.getHoten());
        values.put("sdt", user.getSdt());
        values.put("diachi", user.getDiachi());
        values.put("matkhau", user.getMatKhau());
        return database.update("TaiKhoan", values, "tenDN=?", new String[]{String.valueOf(user.getTenDN())});
    }
    public void updateAD(TaiKhoan user) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("tenDN",user.getTenDN());
        values.put("hoten", user.getHoten());
        values.put("sdt", user.getSdt());
        values.put("diachi", user.getDiachi());
        values.put("matkhau", user.getMatKhau());
        database.update("Admin", values, "tenDN=?", new String[]{String.valueOf(user.getTenDN())});
    }

    public boolean xoaKhachHang(int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long row = database.delete("TaiKhoan","maTk=?", new String[]{String.valueOf(id)});
        return (row >0);
    }


    public ArrayList<TaiKhoan> getDSDL() {
        ArrayList<TaiKhoan> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * from TaiKhoan ", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                //int maTk, String tenDN, String matKhau, String hoten, String sdt, String diachi
                list.add(new TaiKhoan(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));

            } while (cursor.moveToNext());
        }
        return list;
    }
    public boolean themThanhVien(String tenDN, String mk,String sdt,String hoten,String diachi){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenDN",tenDN);
        values.put("matKhau",mk);
        values.put("sdt",sdt);
        values.put("hoTen",hoten);
        values.put("diaChi",diachi);
        long check = database.insert("TaiKhoan",null,values);
        if (check ==-1){
            return false;
        }
        return true;
    }
    public boolean updateKhachHang(TaiKhoan tk){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenDN",tk.getTenDN());
        values.put("matKhau",tk.getMatKhau());
        values.put("hoTen",tk.getHoten());
        values.put("sdt",tk.getSdt());
        values.put("diaChi",tk.getDiachi());
        long row = database.update("TaiKhoan",values,"maTk=?",
                new String[]{String.valueOf(tk.getMaTk())});
        return row>0;
    }
}
