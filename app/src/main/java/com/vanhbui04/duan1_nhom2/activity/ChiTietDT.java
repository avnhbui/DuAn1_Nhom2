package com.vanhbui04.duan1_nhom2.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vanhbui04.duan1_nhom2.R;
import com.vanhbui04.duan1_nhom2.adapter.DanhGiaAdapter;
import com.vanhbui04.duan1_nhom2.dao.DanhGiaDAO;
import com.vanhbui04.duan1_nhom2.dao.DienThoaiDAO;
import com.vanhbui04.duan1_nhom2.dao.GioHangDAO;
import com.vanhbui04.duan1_nhom2.dao.TaiKhoanDAO;
import com.vanhbui04.duan1_nhom2.model.DanhGia;
import com.vanhbui04.duan1_nhom2.model.DienThoai;
import com.vanhbui04.duan1_nhom2.model.GioHang;
import com.vanhbui04.duan1_nhom2.model.TaiKhoan;

import java.util.ArrayList;

public class ChiTietDT extends AppCompatActivity {
    private int quantity = 1;
    private double totalPrice = 0.0;

    DienThoai dienThoai;
    DienThoaiDAO dao;
    RecyclerView rcldg;
    DanhGiaDAO danhgiaDAO;
    TaiKhoanDAO taikhoanDAO;

    DanhGiaAdapter adapter_dg;
    ArrayList<DanhGia> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chi_tiet_dt);
        rcldg = findViewById(R.id.rcldanhgia);
        dao = new DienThoaiDAO(getApplicationContext());
        danhgiaDAO = new DanhGiaDAO(this);
        taikhoanDAO = new TaiKhoanDAO(this);

        loaddata();

        TextView btngio = findViewById(R.id.addgiohang);
        TextView tvQuantity = findViewById(R.id.tvQuantity);
        ImageView btnPlus = findViewById(R.id.btnPlus);
        ImageView btnMinus = findViewById(R.id.btnMinus);


        Intent intent = getIntent();

        byte[] hinhanhDT = getIntent().getByteArrayExtra("bitmapImage");
        if (hinhanhDT != null && hinhanhDT.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanhDT, 0, hinhanhDT.length);
            ImageView anh = findViewById(R.id.imageView);
            anh.setImageBitmap(bitmap);
        } else {
            ImageView anh = findViewById(R.id.imageView);
            anh.setImageResource(R.drawable.baseline_phone_iphone_24);
        }
        int maDt = intent.getIntExtra("maDT", 0);
        String ten = intent.getStringExtra("tenDT");
        String maLoaiSeries = intent.getStringExtra("maLoaiSeries");
        Double giaTien = (double) intent.getDoubleExtra("giaTien", 0);
        String moTa = intent.getStringExtra("moTa");

        TextView tvName = findViewById(R.id.tvTendt);
        TextView tvloaidt = findViewById(R.id.tvLoaisr);
        TextView tvgia = findViewById(R.id.tvGiadt);
        TextView tvmota = findViewById(R.id.tvChitiet);
        TextView tvsoluong = findViewById(R.id.tvsoluong);
        TextView tvdanhGiaTB = findViewById(R.id.textView11);
        dienThoai = dao.getID(String.valueOf(maDt));

        double tb = 0;

        int tong = 0, tg = 0;
        for (DanhGia x : list) {
            if (x.getMaDt() == dienThoai.getMaDT()) {
                tong = tong + x.getDiem();
                tg = tg + 1;
            }
        }
        if (tong != 0 && tg != 0) {
            tb = tong / tg;
        }

        tvName.setText("Title: " + ten);
        tvloaidt.setText("Series: " + maLoaiSeries);
        tvgia.setText(String.format("Giá điện thoại: %,.0f VNĐ", giaTien));
        tvmota.setText(moTa);
        tvsoluong.setText("" + dienThoai.getSoLuong());
        tvdanhGiaTB.setText("Đánh giá và nhận xét: " + tb);

        Toolbar toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity < dienThoai.getSoLuong()) {
                    quantity++; // Tăng số lượng lên 1 đơn vị
                    totalPrice += giaTien; // Tăng tổng giá tiền theo giá của mỗi sản phẩm
                    tvQuantity.setText(String.valueOf(quantity));
                } else {
                    Toast.makeText(getApplicationContext(), "Số lượng sản phẩm vượt quá số lượng có sẵn trong kho", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    quantity--; // Giảm số lượng đi 1 đơn vị
                    totalPrice -= giaTien; // Giảm tổng giá tiền theo giá của mỗi sản phẩm
                    tvQuantity.setText(String.valueOf(quantity)); // Hiển thị số lượng mới
                }
            }
        });
        btngio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);
                String username = preferences.getString("username", "");
                TaiKhoan taiKhoan = taikhoanDAO.getID(username);
                GioHang gioHang = new GioHang(dienThoai.getMaDT(), giaTien, quantity, taiKhoan.getMaTk());
                GioHangDAO dao = new GioHangDAO(getApplicationContext());
                Intent intent = new Intent(ChiTietDT.this, GioHangCustomer.class);
                if (quantity > dienThoai.getSoLuong()) {
                    Toast.makeText(ChiTietDT.this, "Không the đặt hàng số lượng trong kho không đủ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dao.checkExistence(taiKhoan.getMaTk(),maDt)) {
                    Toast.makeText(ChiTietDT.this, "Tên đã tồn tại trong giỏ hàng", Toast.LENGTH_SHORT).show();
                } else {
                    if (dao.insert(gioHang) > 0) {
                        startActivity(intent);
                        Toast.makeText(ChiTietDT.this, "Thêm thành công vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChiTietDT.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
    public void loaddata() {
        Intent intent = getIntent();
        int maDt = intent.getIntExtra("maDT", 0);
        danhgiaDAO = new DanhGiaDAO(getApplicationContext());
        list = danhgiaDAO.getBymatk(maDt);
        adapter_dg = new DanhGiaAdapter(getApplicationContext(), list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rcldg.setLayoutManager(layoutManager);
        rcldg.setAdapter(adapter_dg);
    }
}