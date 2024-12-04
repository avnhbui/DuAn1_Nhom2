package com.vanhbui04.duan1_nhom2.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vanhbui04.duan1_nhom2.dao.ChiTietDAO;
import com.vanhbui04.duan1_nhom2.dao.DanhGiaDAO;
import com.vanhbui04.duan1_nhom2.dao.DienThoaiDAO;
import com.vanhbui04.duan1_nhom2.dao.HoaDonDAO;
import com.vanhbui04.duan1_nhom2.dao.LoaiDTDAO;
import com.vanhbui04.duan1_nhom2.dao.TaiKhoanDAO;
import com.vanhbui04.duan1_nhom2.activity.DanhGiaCustomer;
import com.vanhbui04.duan1_nhom2.model.ChiTiet;
import com.vanhbui04.duan1_nhom2.model.DanhGia;
import com.vanhbui04.duan1_nhom2.model.DienThoai;
import com.vanhbui04.duan1_nhom2.model.HoaDon;
import com.vanhbui04.duan1_nhom2.model.LoaiSeries;
import com.vanhbui04.duan1_nhom2.model.TaiKhoan;
import com.vanhbui04.duan1_nhom2.R;

import java.util.ArrayList;

public class ChiTietLSAdapter extends RecyclerView.Adapter<ChiTietLSAdapter.ViewHolder> {
    Context context;
    ArrayList<ChiTiet> list;
    ChiTietDAO dao;
    HoaDonDAO hoadonDAO;
    DienThoaiDAO dienthoaiDAO;
    LoaiDTDAO loaidtDAO;
    DanhGiaDAO danhgiaDAO;
    TaiKhoanDAO taikhoanDAO;
    public ChiTietLSAdapter(Context context, ArrayList<ChiTiet> list) {
        this.context = context;
        this.list = list;
        this.dao = new ChiTietDAO(context);
        hoadonDAO = new HoaDonDAO(context);
        dienthoaiDAO = new DienThoaiDAO(context);
        loaidtDAO = new LoaiDTDAO(context);
        danhgiaDAO = new DanhGiaDAO(context);
        taikhoanDAO = new TaiKhoanDAO(context);
    }
    @NonNull
    @Override
    public ChiTietLSAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chitietlsu, parent, false);
        return new ChiTietLSAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTietLSAdapter.ViewHolder holder, int position) {
        ChiTiet chiTiet = list.get(position);

        DienThoai dienThoai = dienthoaiDAO.getID(String.valueOf(chiTiet.getMadt()));

        LoaiSeries loaiSeries = loaidtDAO.getID(String.valueOf(dienThoai.getMaLoaiSeri()));

        HoaDon hoaDon = hoadonDAO.getID(String.valueOf(chiTiet.getMahd()));

//        holder.txtmachitiet.setText("Mã chi tiết đơn: " + String.valueOf(chiTiet.getMact()));
        holder.txtmadt.setText("Tên điện thoại: " + String.valueOf(dienThoai.getTenDT()));
        holder.txthoadon.setText("Tên loại: " + loaiSeries.getTenLoaiSeri());
        holder.txtsoluong.setText("Số lượng: " + chiTiet.getSoluong());
        holder.txtgiatien.setText(String.format("Giá điện thoại: %,.0f VNĐ", chiTiet.getGiatien()));
        holder.txttongtien.setText(String.format("Tổng tiền: %,.0f VNĐ", chiTiet.getGiatien() * chiTiet.getSoluong()));

        byte[] anhData = dienthoaiDAO.getAnhByMaDT(dienThoai.getMaDT());
        if (anhData != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(anhData, 0, anhData.length);
            holder.imganh.setImageBitmap(bitmap);
        } else {
            holder.imganh.setImageResource(R.drawable.iphone15);
        }
        SharedPreferences preferences = context.getSharedPreferences("USER_DATA", MODE_PRIVATE);
        String username = preferences.getString("username", "");


        TaiKhoan taiKhoan = taikhoanDAO.getID(username);
        boolean hasReviewed = false;

        for (DanhGia x : danhgiaDAO.getAll()) {
            if (hoaDon.getTrangThai() == 3 && x.getMaDt() == chiTiet.getMadt() && taiKhoan.getMaTk() == x.getMaTk()) {
                hasReviewed = true;
                break;
            }
        }

        if (hasReviewed) {
            holder.btndg.setVisibility(View.GONE);
        } else {
            holder.btndg.setVisibility(View.VISIBLE);
        }
        if (hoaDon.getTrangThai() != 3) {
            holder.btndg.setVisibility(View.GONE);
        }
        if (username.equalsIgnoreCase("admin")) {
            holder.btndg.setVisibility(View.GONE);
        }


        holder.btndg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DanhGiaCustomer.class);
                intent.putExtra("productId", chiTiet.getMadt()); // Truyền mã sản phẩm
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtmadt, txthoadon, txtsoluong, txtgiatien, txttongtien;
        ImageView imganh;
        Button btndg, txtmachitiet;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmadt = itemView.findViewById(R.id.txtmadt);
            txthoadon = itemView.findViewById(R.id.txthoadon);
            txtsoluong = itemView.findViewById(R.id.txtsoluong);
            txtgiatien = itemView.findViewById(R.id.txtgiadt);
            txttongtien = itemView.findViewById(R.id.txttongtien);
            imganh = itemView.findViewById(R.id.imganh);
            btndg = itemView.findViewById(R.id.btndanhg);
        }
    }
}
